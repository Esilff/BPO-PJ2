package Chessboard;

import Game.Ipiece;
// On a besoin de toutes les pièces
// TODO : séparation de package nécessaire ? Dépendance réciproque...
import Piece.*;


/**
 * Chessboard : Classe s'occupant du plateau de jeu. Dispose de fonctions de manipulation virtuelles
 * et d'un système d'affichage console.
 */
public class Chessboard {

	/** Définis la disposition initiale à utiliser au démarrage du programme
	 *  cf. this.defaultChess_template() et fonctions associées pour plus de compréhension
	 */
	public enum INIT_LAYOUT {
		DEFAULT_CHESS,
		EMPTY,
		FINALE_2R1T
	}
	private final INIT_LAYOUT TEMPLATE_TO_USE_AT_STARTUP;

	/** La taille de l'échéquier. Constante valant 8, __ne dois pas changer__ sous peine de mauvaises surprises !!*/
	public static final int BOARD_SIZE = 8;
	public static final char LOWERCASE_A = 'a';

	/** raw chessboard, tableau 2D 8x8 qui contient des pions
	 * Final : Non redéfinissable mais ce qui est à l'intérieur reste modifiable
	 */
	private final Ipiece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	/**
	 * Vecteur représentant la taille de l'échiquier. Si il s'agit d'un échiquier 8x8, alors BOARD_RECT = [8, 8]
	 */
	public static final vect2D BOARD_RECT = new vect2D(Chessboard.BOARD_SIZE, Chessboard.BOARD_SIZE);

	//  ---------------------------------------------------------------------
	
	/** Constructeur du plateau */
	public Chessboard(INIT_LAYOUT template_to_use_at_startup) {
		TEMPLATE_TO_USE_AT_STARTUP = template_to_use_at_startup;
		this.resetBoard();
	}
	public Chessboard() {
		this(INIT_LAYOUT.DEFAULT_CHESS);
	}
	
	
	
	// METHODES D'AFFICHAGE ---------------------------------------------------------------------

	/** Génère l'affichage du plateau de jeu
	 * @return la String générée représentant le plateau de jeu
	 */
	public String toString () {
		StringBuilder retval = new StringBuilder();
		append_horizGraduation(retval);
		for (int i = BOARD_SIZE; i > 0; i--) {
			append_horizSeparator(retval);
			append_boardLine(i, retval);
		}
		append_horizSeparator(retval);
		append_horizGraduation(retval);
		return retval.toString();
	}

	/**
	 * Méthode interne qui ajoute une graduation horizontale de plateau au sein d'un buffer de type StringBuilder
	 * passée en paramètre. Typiquement la String "    a   b   c   d   e   f   g   h    " si BOARD_SIZE vaut 8
	 * @param sb la StringBuilder dans laquelle l'entrée sera ajoutée
	 */
	private static void append_horizGraduation(StringBuilder sb) {
		sb.append(" ");
		for (int i = 0; i < BOARD_SIZE; i++) {
			sb.append("   ")
			  .append( (char)(LOWERCASE_A + i) );
		}
		sb.append("    \n");
	}

	/**
	 * Méthode interne qui ajoute une ligne horizontale de plateau au sein d'un bugger de type StringBuilder passé en
	 * paramètre. Typiquement la String "   --- --- --- --- --- --- --- ---   " si BOARD_SIZE vaut 8
	 * @param sb la StringBuilder dans laquelle l'entrée sera ajoutée
	 */
	private static void append_horizSeparator(StringBuilder sb) {
		sb.append("  ");
		for (int i = 0; i < BOARD_SIZE; i++) {
			sb.append(" ---");
		}
		sb.append("   \n");
	}

	/** Méthode interne qui ajoute l'affichage d'une ligne réelle (ie. contenant des pièces) au sein d'un buffer de
	 * type StringBuilder. Exemple d'affichage : "8 | t | c | f | r | q | f | c | t | 8"
	 * @param lineIndex l'identifiant (naturel) de la ligne, valeur entre 1 et 8 en particulier.
	 * @param sb la StringBuilder dans laquelle l'entrée sera ajoutée
	 */
	private void append_boardLine(int lineIndex, StringBuilder sb) {
		sb.append(lineIndex);
		for (int i = 0; i < BOARD_SIZE; i++) {
			sb.append(" | ")
			  .append(getPiece(lineIndex - 1, i).getSign());
		}
		sb.append(" | ").append(lineIndex).append("\n");
	}

	// METHODES DE MANIPULATION  ---------------------------------------------------------------------
	/**
	 * Classe utilitaire pour (re-)définir une case de this.board. La config étant peu conventionnelle (line-colonne),
	 * cela permettra de ne pas se tromper grâce aux indications de l'IDE (ou juste rendre le code + maintenable).
	 * cette méthode n'a donc __pas__ pour but d'agir en tant que getter/setter d'encapsulation
	 * @param line la ligne à intervenir
	 * @param column la colonne à intervenir
	 * @param Ipiece la pièce à mettre dans la case en question
	 * @return false si l'opération échoue, sinon true. (attention au silence, pas d'erreurs violentes !!)
	 */
	public boolean setPiece(int line, int column, Ipiece piece) {
		try {
			this.board[column][line] = piece;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * surcharge de setPiece(int line, int column, Ipiece piece), mais les coordonées numériques peuvent être remplacées
	 * par un vec2 (coupe d'entiers).
	 * @param coord Un coupe d'entiers représenté par un vec2
	 * @param Ipiece La pièce à placer dans l'échiquier.
	 * @return false si l'opération échoue, sinon true. (attention donc au silence, pas d'erreurs violentes !!)
	 */
	public boolean setPiece(vect2D coord, Ipiece piece) {
		return this.setPiece(coord.getY(), coord.getX(), piece);
	}

	/**
	 * surcharge de setPiece(int line, int column, Ipiece piece), mais les coordonées numériques peuvent être remplacées
	 * par une coordonée d'échecs. Ex : B7 (= ligne 7 colonne 2) place le pion dans this.board[6][1].
	 * @param coord Une coordonée naturelle d'échiquier : 1 lettre pour désigner la colonne, 1 chiffre désigner la ligne
	 * @param Ipiece La pièce à placer dans l'échiquier.
	 * @return false si l'opération échoue, sinon true. (attention donc au silence, pas d'erreurs violentes !!)
	 */
	public boolean setPiece(String coord, Ipiece piece) {
		return this.setPiece(vect2D.createFromChessCoord(coord), piece);
	}

	/**
	 * Classe utilitaire pour récupérer une case dans this.board. La config étant peu conventionnelle (line-colonne),
	 * cela permettra de ne pas se tromper grâce aux indications de l'IDE (ou juste rendre le code + maintenable).
	 * @param line la ligne où se trouve la pièce
	 * @param column la colonne où se trouve la pièce
	 * @return La pièce correspondante. Vu qu'on accède scénaristiquement à la pièce physique, on retourne donc la pièce
	 * telle-quelle (par référence au lieu d'un clone)
	 * @see this.setIpiece pour définir au lieu de récupérer
	 */
	public Ipiece getPiece(int line, int column) {
		return this.board[column][line].clone();
	}
	public Ipiece getPiece(vect2D newCoord) {
		return getPiece(newCoord.getY(), newCoord.getX());
	}

	// METHODES D'INITIALISATION ---------------------------------------------------------------------

	/**
	 * ResetBoard : nom de fonction assez explicite. Choisis le bon template de départ selon la variable
	 * interne TEMPLATE_TO_USE_AT_STARTUP
	 */
	private INIT_LAYOUT resetBoard() {
		switch (TEMPLATE_TO_USE_AT_STARTUP){
			case DEFAULT_CHESS:
				return defaultChess_template();
			case FINALE_2R1T:
				return finale2R1T_template();
			default:
				return empty_template();
		}
	}

	/**
	 * Réinitialise le plateau par les placement initiaux de pions :
	 * [TCFRQFCT] - La première ligne est la rangée initiale de pièces blanc
	 * [PPPPPPPP] - La seconde ligne est une rangée de pions blancs
	 * [        ] - Toutes les autres lignes sont des cases vides
	 * [pppppppp] - L'avant dernière ligne est une rangée de pions noirs
	 * [tcfrqfct] - La dernière ligne est la rangée intiale de pièces noires
	 */
	private INIT_LAYOUT defaultChess_template() {
		for (int line = 0; line < BOARD_SIZE; line++) {
			switch (line) {
				case 0:
					reset_firstLine(line, Piece.IS_WHITE);
					break;
				case 1:
					fillLineWith(line, new Pawn(Piece.IS_WHITE));
					break;
				case BOARD_SIZE - 2:
					fillLineWith(line, new Pawn(!Piece.IS_WHITE));
					break;
				case BOARD_SIZE - 1:
					reset_firstLine(line, !Piece.IS_WHITE);
					break;
				default:
					fillLineWith(line, new EmptyPiece());
					break;
			}
		}
		return INIT_LAYOUT.DEFAULT_CHESS;
	}
	private INIT_LAYOUT finale2R1T_template() {
		this.empty_template();
		this.setPiece("e8", new King(!Piece.IS_WHITE));
		this.setPiece("e6", new King(Piece.IS_WHITE));
		this.setPiece("b7", new Tower(Piece.IS_WHITE));
		return INIT_LAYOUT.FINALE_2R1T;
	}

	/**
	 *
	 * @return le type de layout généré : INIT_LAYOUT.EMPTY
	 */
	private INIT_LAYOUT empty_template() {
		for (int line = 0; line < BOARD_SIZE; line++) {
			fillLineWith(line, new EmptyPiece());
		}
		return INIT_LAYOUT.EMPTY;
	}


	/**
	 * (re-) Définis la configuration par défaut d'une première ou dernière ligne
	 * @param line La ligne à remplir
	 * @param isWhite Les pièces à créer sont-elles du camp blanc, ou noir ?
	 */
	private void reset_firstLine(int line, boolean isWhite) {
		setPiece(line, 0, new Tower(isWhite)  );  // T
		setPiece(line, 1, new Knight(isWhite) ); // C
		setPiece(line, 2, new Bishop(isWhite) ); // F
		setPiece(line, 3, new Queen(isWhite)  );   // D pour Dame
		setPiece(line, 4, new King(isWhite)   );  // R
		setPiece(line, 5, new Bishop(isWhite) ); // F
		setPiece(line, 6, new Knight(isWhite) ); // C
		setPiece(line, 7, new Tower(isWhite) );  // T
	}

	/**
	 * Remplis une ligne par une pièce donnée
	 * @param line La ligne à remplir
	 * @param toFill La pièce à ajouter sur l'ensemble de la ligne
	 */
	private void fillLineWith(int line, Ipiece toFill) {
		for (int column = 0; column < BOARD_SIZE; column++) {
			// on ne veut pas avoir la même pièce physique sur toute la ligne; il faut avoir des copies.
			setPiece(line, column, toFill.clone());
		}
	}
}
