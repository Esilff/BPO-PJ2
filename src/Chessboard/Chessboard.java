package Chessboard;

// On a besoin de toutes les pièces
import Piece.*;


/**
 * Chessboard : Classe s'occupant du plateau de jeu. Dispose de fonctions d'entrées
 * sortie et d'un système d'affichage console.
 */
public class Chessboard {
	/**
	 * La taille de l'échéquier. Constante valant 8, __ne dois pas changer__ !!
	 */
	public static final int BOARD_SIZE = 8;

	/** raw chessboard, tableau 2D 8x8 qui contient des pions */
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	//  ---------------------------------------------------------------------
	
	/** Constructeur du plateau */
	public Chessboard() {
		this.resetBoard();
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
			  .append( (char)('a' + i) );
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
					.append(getPiece(lineIndex - 1, 0).getSign());
		}
		sb.append(" | ").append(lineIndex).append("\n");
	}

	// METHODES DE MANIPULATION  ---------------------------------------------------------------------
	/**
	 * Classe utilitaire pour (re-)définir une case de this.board. La config étant peu conventionnelle (line-colonne),
	 * cela permettra de ne pas se tromper grâce aux indications de l'IDE (ou juste rendre le code + maintenable).
	 * @param line la ligne à intervenir
	 * @param column la colonne à intervenir
	 * @param piece la pièce à mettre dans la case en question
	 */
	private void setPiece(int line, int column, Piece piece) {
		this.board[column][line] = piece;
		// TODO possible : surcharge de constructeur permettant de faire setPiece("b7", new Tower...) si nécessaire
	}

	/**
	 * Classe utilitaire pour récupérer une case dans this.board. La config étant peu conventionnelle (line-colonne),
	 * cela permettra de ne pas se tromper grâce aux indications de l'IDE (ou juste rendre le code + maintenable).
	 * @param line la ligne où se trouve la pièce
	 * @param column la colonne où se trouve la pièce
	 * @return La pièce correspondante. Vu qu'on accède scénaristiquement à la pièce physique, on retourne donc la pièce
	 * telle-quelle (par référence au lieu d'un clone)
	 * @see this.setPiece pour définir au lieu de récupérer
	 */
	private Piece getPiece(int line, int column) {
		return this.board[column][line];
	}

	// METHODES D'INITIALISATION ---------------------------------------------------------------------
	/**
	 * ResetBoard : Réinitialise le plateau __avec__ les placement initiaux de pions :
	 * [TCFRQFCT] - La première ligne est la rangée initiale de pièces blanc
	 * [PPPPPPPP] - La seconde ligne est une rangée de pions blancs
	 * [        ] - Toutes les autres lignes sont des cases vides
	 * [pppppppp] - L'avant dernière ligne est une rangée de pions noirs
	 * [tcfrqfct] - La dernière ligne est la rangée intiale de pièces noires
	 */
	private void resetBoard() {
		for (int line = 0; line < BOARD_SIZE; line++) {
			switch (line) {
				case 0:
					reset_firstLine(line, true);
					break;
				case 1:
					fillLineWith(line, new Pawn(true));
					break;
				case BOARD_SIZE - 2:
					fillLineWith(line, new Pawn(false));
					break;
				case BOARD_SIZE - 1:
					reset_firstLine(line, false);
					break;
				default:
					fillLineWith(line, new EmptyPiece());
					break;
			}
		}
	}

	/**
	 * (re-) Définis la configuration par défaut d'une première ou dernière ligne
	 * @param line La ligne à remplir
	 * @param isWhite Les pièces à créer sont-elles du camp blanc, ou noir ?
	 */
	private void reset_firstLine(int line, boolean isWhite) {
		setPiece(line, 0, new Tower(isWhite) );  // T
		setPiece(line, 1, new Knight(isWhite) ); // C
		setPiece(line, 2, new Bishop(isWhite) ); // F
		setPiece(line, 3, new King(isWhite) );   // R
		setPiece(line, 4, new Queen(isWhite) );  // Q
		setPiece(line, 5, new Bishop(isWhite) ); // F
		setPiece(line, 6, new Knight(isWhite) ); // C
		setPiece(line, 7, new Tower(isWhite) );  // T
	}

	/**
	 * Remplis une ligne par une pièce donnée
	 * @param line La ligne à remplir
	 * @param toFill La pièce à ajouter sur l'ensemble de la ligne
	 */
	private void fillLineWith(int line, Piece toFill) {
		for (int column = 0; column < BOARD_SIZE; column++) {
			// on ne veut pas avoir la même pièce physique sur toute la ligne; il faut avoir des copies.
			setPiece(line, column, toFill.clone());
		}
	}
}
