package Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import static Appli.Application.ENDL;
import Chessboard.Chessboard;
import Piece.EmptyPiece;
import vec2.vec2;

public class Game {
	/** Le plateau de jeu */
	private final Chessboard board;
	
	/** l'entrée standard (console) */
	private final Scanner input;
	
	/** indique de quel camp est le joueur du tour actuel */
	private boolean isWhitePlaying = true;
	
	/** Message supplémentaire affiché avant le prompt*/
	private String error = "";

	/** indique les différents états de partie */
	private enum CHECK_STATE {
		IN_CHECK,
		THERE_IS_WINNER,
		DRAW,
		NONE
	}
	private CHECK_STATE checkState = CHECK_STATE.NONE;

	/** Stocke la dernière liste des emplacements où le roi peut se déplacer sans se mettre en échec */
	private LinkedList<vec2> KingSafeTargetsList = new LinkedList<>();
	// facultatif mais sert un peu de cache. Cela évite de faire la même opération plusieurs fois

	private static final int ZERO = 0, ONE = 1, TWO = 2, FOUR = 4; // Mon dieux, quatre = 4 :o
	private static final String LOSER_MSG = "FIN DE PARTIE : Le joueur %s a perdu !";
	private static final String DRAW_MSG = "FIN DE PARTIE : Match nul !";
	private static final String EMPTY_STRING = ""; // Comme si c'était une valeur qui pouvait changer ... :-(
	private static final String REGULAR_PROMPT = "> ";
	private static final String ERROR_PROMPT = "#> ";
	private static final String MSG_CURRPLAYER = "Joueur %s, c'est à vous !" + ENDL;
	private static final String WHITE_NAME = "blanc";
	private static final String BLACK_NAME = "noir";
	private static final String CHECK_MSG = "Le joueur %s est en echec" + ENDL;
	private static final String ERRMSG_INVALID_COORD = "Merci d'entrée une coordonnée valide d'échecs";
	private static final String ERRMSG_MOVING_VOID = "Vous êtes en train de déplacer du vide";
	private static final String ERRMSG_MOVING_OPPONENT_PIECE = "Vous êtes en train de déplacer la pièce de l'adversaire.";
	private static final String ERRMSG_CANNIBALISM_FORBIDDEN = "Cannibalisme interdit";
	private static final String ERRMSG_KING_CANNOT_MOVE_THERE = "Coup interdit, votre roi sera en situation d'échec sur la case cible.";
	private static final String ERRMSG_KING_STILL_IN_CHECK = "Le roi est en danger, vous ne pouvez déplacer d'autres pièces";


	public Game(Chessboard chessboard) {
		this.board = chessboard;
		this.input = new Scanner(System.in);
	}
	
	public void start() {
		updateKingSafeTargetsList(isWhitePlaying); // initialise le "cache" des cases safes pour le roi
		while (checkState == CHECK_STATE.NONE || checkState == CHECK_STATE.IN_CHECK) {
			turnManager();
		}
		System.out.println(looserTxt());
		
		input.close();
	}
	
	// utile pour les tests unitaires
	public String board_toString() {
		return board.toString();
	}
	
	public void turnManager() {
		System.out.println(EMPTY_STRING); // saut de ligne
		System.out.println(board_toString());
		
		System.out.format(MSG_CURRPLAYER, (isWhitePlaying) ? WHITE_NAME : BLACK_NAME);
		if (this.checkState != CHECK_STATE.NONE) {
			System.out.format(CHECK_MSG, (isWhitePlaying) ? WHITE_NAME : BLACK_NAME);
		}
		if (this.error.equals(EMPTY_STRING)) {
			System.out.print(REGULAR_PROMPT);
		} else {
			System.out.println(this.error);
			System.out.print(ERROR_PROMPT);
		}
		
		String s = REGULAR_PROMPT;
		try {
			s = this.input.next();
			
			play(s.substring(ZERO, TWO), s.substring(TWO, FOUR));
			
			this.error = EMPTY_STRING;
		} catch (IndexOutOfBoundsException e) {
			this.error = ERRMSG_INVALID_COORD;
		} catch (BadMoveException e) {
			this.error = s.substring(ZERO, FOUR) + " : " + e.getMessage();
		} 
	}
	

	public void play(String originCoord, String newCoord) throws BadMoveException {
		// décodage de l'entrée
		vec2 originCoordConv = Chessboard.createVectFromChessCoord(originCoord);
		vec2 newCoordConv = Chessboard.createVectFromChessCoord(newCoord);
		if (   originCoordConv.equals(vec2.INVALID_VECT)
			|| newCoordConv.equals(vec2.INVALID_VECT)) {
			throw new BadMoveException(ERRMSG_INVALID_COORD);
		}
		
		// Vérification si la pièce est manipulable
		Ipiece toPlay = this.board.getPiece(originCoordConv);
		Ipiece target = this.board.getPiece(newCoordConv);
		if (toPlay.isEmpty()) {
			throw new BadMoveException(ERRMSG_MOVING_VOID);
		} else if (toPlay.isWhite() != isWhitePlaying) {
			throw new BadMoveException(ERRMSG_MOVING_OPPONENT_PIECE);
		} 
		if (! target.isEmpty() && target.isWhite() == isWhitePlaying) {
			BadMoveException up = new BadMoveException(ERRMSG_CANNIBALISM_FORBIDDEN);
			throw up; // ha ha ha...
		}

		// Règles supplémentaires très basiques pour gérer le cas de l'échec.
		if (toPlay.isKing()) {
			if (this.KingSafeTargetsList.contains(newCoordConv)) {
				this.checkState = CHECK_STATE.NONE; // des fois c'est en none, des fois non.
			} else {
				throw new BadMoveException(ERRMSG_KING_CANNOT_MOVE_THERE);
			}
		} else if (this.checkState == CHECK_STATE.IN_CHECK) {
			throw new BadMoveException(ERRMSG_KING_STILL_IN_CHECK);
		}
		
		// vérification si coup jouable
		toPlay.canMoveTo(this, originCoordConv, newCoordConv, false);
		
		// application du coup et changement du joueur actuel
		this.board.setPiece(originCoordConv, new EmptyPiece());
		this.board.setPiece(newCoordConv, toPlay);
		this.isWhitePlaying = ! this.isWhitePlaying;

		prepareNextTurn(newCoordConv);
	}

	private void prepareNextTurn(vec2 newCoordConv) {
		// marquage échec au besoin
		markOppositeKingInCheckIfNeeded(newCoordConv);
		updateKingSafeTargetsList(this.isWhitePlaying);
		// marquage pat/mat très basique
		if (this.KingSafeTargetsList.isEmpty()) {
			if (this.checkState == CHECK_STATE.IN_CHECK) {
				this.checkState = CHECK_STATE.THERE_IS_WINNER;
			} else {
				this.checkState = CHECK_STATE.DRAW;
			}
		}
		// dernier cas de pat où il reste que deux rois (la tour a été mangée)
		if (   this.board.getCache().getPieceCounterOf(false) == ONE
			&& this.board.getCache().getPieceCounterOf(true) == ONE) {
			this.checkState = CHECK_STATE.DRAW;
		}
	}

	/**
	 * Un algorithme qui permet de vérifier s'il n'y a aucun obstacle lors d'un déplacement horizontal, vertical ou
	 * diagonal. Assume qu'une vérification de corresponsance de déplacement a été effectuée au préalable !!!
	 * @param originCoord la coordonnée actuelle de la pièce (tour, fou ou dame)
	 * @param newCoord la coordonnée cible
	 * @throws BadMoveException si le chemin est bloqué
	 */
	public void checkNoObstaclesInTheWay(vec2 originCoord, vec2 newCoord) throws BadMoveException {
		vec2 relativeMove = newCoord.minus(originCoord);
		vec2 step = relativeMove.generate_signum();
		vec2 i = originCoord.clone();
		while (true) {
			i.addAndApply(step);
			if (newCoord.equals(i) ) {
				break;
			}
			if(!(this.board.getPiece(i).isEmpty())) {
				throw new BadMoveException("Quelque chose bloque le chemin à "+ i.toString());
			}
		}
	}
	
	public void checkNoObstaclesInTheWay(vec2 originCoord, vec2 newCoord, boolean forCheckMate) throws BadMoveException {
		vec2 relativeMove = newCoord.minus(originCoord);
		vec2 step = relativeMove.generate_signum();
		vec2 i = originCoord.clone();
		while (true) {
			i.addAndApply(step);
			if (newCoord.equals(i) ) {
				break;
			}
			if(!(this.board.getPiece(i).isEmpty()) && !forCheckMate) {
				throw new BadMoveException("Quelque chose bloque le chemin à "+ i.toString());
			}
		}
	}
	



	/**
	 * Vérifie si la pièce passée en paramètre est capable de manger le roi; le cas échéant, mets en situation d'échecs
	 * @param newCoordConv la position de la pièce s'apprêtant à attaquer le roi
	 */
	private void markOppositeKingInCheckIfNeeded(vec2 newCoordConv) {
		vec2 kingPos = this.board.getCache().getKingPosOfColor(isWhitePlaying);
		if (canBeEatenBy(kingPos, newCoordConv, false)) {
			this.checkState = CHECK_STATE.IN_CHECK;
		}
	}
	private Boolean canBeEatenBy(vec2 coord, vec2 ennemy, boolean forCheckMate) {
		try {
			this.board.getPiece(ennemy).canMoveTo(this, ennemy, coord, forCheckMate);
			return true;
		} catch (BadMoveException e) {
			return false;
		}
	}
	/**
	 * Un algorithme qui retourne une liste des positions des pièces correspondant à la couleur passée en paramètre
	 * @param isWhite true si on souhaite récupérer des pièces blanches, false sinon
	 * @return une ArrayList contenant la liste des positions des pièces de la couleur passée en paramètre
	 */
	private ArrayList<vec2> getPiecesOfColor(boolean isWhite) {
		ArrayList<vec2> ennemy = new ArrayList<>();
		for (int column = ZERO; column < Chessboard.BOARD_SIZE; column++) {
			for (int line = ZERO; line < Chessboard.BOARD_SIZE; line++) {
				Ipiece piece = this.board.getPiece(line, column);
				if (!piece.isEmpty() && piece.isWhite() == isWhite) {
					ennemy.add(new vec2(column, line));
				}
			}
		}

		return ennemy;
	}
	/**
	 * Un algorithme qui retourne les cases se trouvant autour du roi en prenant en compte les limites du plateau
	 * @param isWhite true si on cherche les cases environnantes du roi de couleur blanche, false sinon
	 * @return une LinkedList contenant les positions absolues des cases aux alentours du roi
	 */
	private LinkedList<vec2> getKingNeighbour(boolean isWhite) {
		LinkedList<vec2> retval = new LinkedList<>();
		vec2 kingPos = this.board.getCache().getKingPosOfColor(isWhite);
		for (int i = -ONE; i <= ONE; i++) {
			for (int j = -ONE; j <= ONE; j++) {
				if (i == ZERO && j == ZERO) continue;
				vec2 toAdd = kingPos.plus(i, j);
				if (! Chessboard.BOARD_RECT.isOutOfBounds(toAdd)) {
					retval.add(toAdd);
				}
			}
		}
		return retval;
	}
	/**
	 * Retourne la liste des mouvements possibles du roi en prenant en compte les cas d'échec dans la variable
	 * {@link this#KingSafeTargetsList}
	 * @param isWhite true si on cherche les cases environnantes du roi de couleur blanche, false sinon
	 */
	private void updateKingSafeTargetsList(boolean isWhite) {
		// vérification mat pour le moment
		this.KingSafeTargetsList = getKingNeighbour(isWhite);
		ArrayList<vec2> ennemy = getPiecesOfColor(!isWhite);

		for (vec2 ennemyIndex : ennemy) {
			// l'interateur permet de remove un element de la linkedList alors qu'elle est parcourue dans le for
			// on aurait pu utiliser removeIf comme me propose intelliJ, mais cette méthode est moins magique
			for (Iterator<vec2> it = this.KingSafeTargetsList.iterator(); it.hasNext(); ) {
				vec2 machin = it.next();
				if (canBeEatenBy(machin, ennemyIndex, true)) {
					it.remove();
				}
			}
		}
	}
	
	
	
	/**
	 * N'est utilisé que par pion. Comme quoi on aurait pas dû le coder...
	 * @return le chessboard courant
	 */
	public Ipiece getCloneOfPiece(int line, int column) {
		return this.board.getPiece(line, column); // getPiece retourne déjà un clone...
	}

	private String looserTxt() {
		if (checkState == CHECK_STATE.DRAW) {
			return DRAW_MSG;
		}
		return String.format(LOSER_MSG, isWhitePlaying ? WHITE_NAME : BLACK_NAME);
	}
}
