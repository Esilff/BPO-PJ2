package Game;

import java.util.ArrayList;
import java.util.Scanner;

import static Appli.Application.ENDL;
import Chessboard.Chessboard;
import Piece.EmptyPiece; // TODO : suppression dépendance
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
	
	/** @see Game#WhoIsInCheck() */
	private Boolean isInCheck = null; 
	
	public Boolean whiteLoosed = false;
	public Boolean blackLoosed = false;
	
	private static final String EMPTY_STRING = ""; // Comme si c'était une valeur qui pouvait changer ... :-(
	private static final String REGULAR_PROMPT = "> ";
	private static final String ERROR_PROMPT = "#> ";
	private static final String MSG = "Joueur %s, c'est à vous !" + ENDL;
	private static final String WHITE_NAME = "blanc";
	private static final String BLACK_NAME = "noir";
	private static final String CHECK_MSG = "Le joueur %s est en echec" + ENDL;


	public Game(Chessboard chessboard) {
		this.board = chessboard;
		this.input = new Scanner(System.in);
	}
	
	public void start() {
		while (!whiteLoosed && !blackLoosed) {
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
		
		System.out.println(turnTxt(isWhitePlaying));
		if (isInCheck != null) {
			System.out.println(checkTxt(isInCheck));
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
			
			play(s.substring(0, 2), s.substring(2, 4));
			
			this.error = EMPTY_STRING;
		} catch (IndexOutOfBoundsException e) {
			this.error = "Merci d'entrée une coordonnée valide d'échecs";
		} catch (BadMoveException e) {
			this.error = s.substring(0, 2) + " : " + e.getMessage();
		} 
	}
	

	public void play(String originCoord, String newCoord) throws BadMoveException {
		// décodage de l'entrée
		vec2 originCoordConv = Chessboard.createVectFromChessCoord(originCoord);
		vec2 newCoordConv = Chessboard.createVectFromChessCoord(newCoord);
		if (   originCoordConv.equals(vec2.INVALID_VECT)
			|| newCoordConv.equals(vec2.INVALID_VECT)) {
			throw new BadMoveException("Coordonnées invalides");
		}
		
		// Vérification si la pièce est manipulable
		Ipiece toPlay = this.board.getPiece(originCoordConv);
		Ipiece target = this.board.getPiece(newCoordConv);
		if (toPlay.isEmpty()) {
			throw new BadMoveException("Vous êtes en train de déplacer du vide");
		} else if (toPlay.isWhite() != isWhitePlaying) {
			throw new BadMoveException("Vous êtes en train de déplacer la pièce de l'adversaire. How dare you ?");
		} 
		if (! target.isEmpty() && target.isWhite() == isWhitePlaying) {
			BadMoveException up = new BadMoveException("Cannibalisme interdit");
			throw up; // ha ha ha...
		}
		if (isInCheck != null && !(toPlay.isKing())) {
			if (toPlay.isWhite() == isInCheck) {
				throw new BadMoveException("Le roi est en danger");
			}
		}
		
		
		// vérification si coup jouable
		toPlay.canMoveTo(this, originCoordConv, newCoordConv);
		
		// coup effectif
		board.setPiece(originCoordConv, new EmptyPiece());
		board.setPiece(newCoordConv, toPlay);
		this.isWhitePlaying = ! this.isWhitePlaying;
		
		// marquage échec
		lookForKingInCheck(newCoordConv);
		checkMate();
	}
	
	public void lookForKingInCheck(vec2 newCoordConv) throws BadMoveException{
		vec2 kingPos;
		// todo : factoriser ça
		if (!isWhitePlaying) kingPos = this.board.getCache().getKingPos_black(); 
		else kingPos = this.board.getCache().getKingPos_white();
		try {
			this.board.getPiece(newCoordConv).canMoveTo(this, newCoordConv, kingPos);
			if (!isWhitePlaying) isInCheck = false;
			else isInCheck = true;
		}catch (BadMoveException e ) {}
	}

	/**
	 * Un algorithme qui permet de vérifier s'il n'y a aucun obstacle lors d'un déplacement horizontal, vertical ou diagonal.
	 * Assume qu'une vérification de corresponsance de déplacement a été effectuée au préalable !!! 
	 * @param originCoord
	 * @param newCoord
	 * @throws BadMoveException
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
	
	/**
	 * Un algorithme qui retourne une liste des positions des pièces ennemies du joueur courant
	 * @return
	 */
	private ArrayList<vec2> getEnnemy() {
		ArrayList<vec2> ennemy = new ArrayList<vec2>();
		for (int i = 0; i < Chessboard.BOARD_SIZE; i++) {
			for (int j = 0; j < Chessboard.BOARD_SIZE; j++) {
				if (this.board.getPiece(i, j).isWhite() != isWhitePlaying) {
					ennemy.add(new vec2(i,j));
				}
			}
		}
		return ennemy;
	}
	
	/**
	 * Un algorithme qui retournes les cases se trouvant autour du roi actuel
	 * @return
	 */
	private ArrayList<vec2> getKingNeighbour() {
		ArrayList<vec2> neighbour = new ArrayList<vec2>();
		
		// TODO : peut-être passable en paramètre ?
		vec2 kingPos;
		if (!isWhitePlaying) kingPos = this.board.getCache().getKingPos_black(); 
		else kingPos = this.board.getCache().getKingPos_white();
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0) continue;
				vec2 toAdd = kingPos.plus(i, j);
				if (! Chessboard.BOARD_RECT.isOutOfBounds(toAdd)) {
					neighbour.add(toAdd);
				}
			}
		}
		return neighbour;
	}
	
	private Boolean canBeEatenBy(vec2 coord, vec2 ennemy) {
		try {
			this.board.getPiece(ennemy).canMoveTo(this, ennemy, coord);
			return true;
		}catch (BadMoveException e ) {}
		return false;
	}
	
	private void checkMate() {
		ArrayList<vec2> neighbour = getKingNeighbour();
		ArrayList<vec2> ennemy = getEnnemy();
		ArrayList<vec2> toRemove = new ArrayList<vec2>();
		for (vec2 ennemyIndex : ennemy) {
			for (vec2 neighbourIndex : neighbour) {
				if (canBeEatenBy(neighbourIndex, ennemyIndex)) {
					//System.out.println(ennemyIndex.toString() + " fait retirer " + neighbourIndex.toString());
					toRemove.add(neighbourIndex);
					// optimisation avec des LinkedList + Iterateur si on a le temps
				}
			}
		}
		neighbour.removeAll(toRemove);
		if (neighbour.isEmpty()) {
			if (isWhitePlaying) whiteLoosed = true;
			else blackLoosed = true;
		}
	}
	
	
	
	/**
	 * N'est utilisé que par pion. Comme quoi on aurait pas dû le coder...
	 * @return le chessboard courant
	 */
	public Ipiece getCloneOfPiece(int line, int column) {
		return this.board.getPiece(line, column); // getPiece retourne déjà un clone...
	}
	
	private static String turnTxt(Boolean isWhite) {
		return String.format(MSG, (isWhite) ? WHITE_NAME : BLACK_NAME);
	}
	
	private static String checkTxt(Boolean isWhite) {
		return String.format(CHECK_MSG, (isWhite) ? WHITE_NAME : BLACK_NAME);
	}

	private String looserTxt() {
		String s ="";
		if (whiteLoosed) s+= "The White side loosed";
		else s+= "The black side loosed";
		return s;
	}

	/** @return true si le roi blanc est en échec, false si le roi noir est en échec, null sinon */
	public Boolean WhoIsInCheck() {
		return isInCheck;
	}
	
	
}
