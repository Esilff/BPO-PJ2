package Chessboard;

// TODO : import Piece.* si nécessaire
import Piece.Piece; // Une pièce quelconque

import Piece.Pawn; // Pion
import Piece.Tower; // Tour
import Piece.Knight; // Cavalier
import Piece.Bishop; // Fou
import Piece.Queen; // Reine
import Piece.King; // Roi
import Piece.EmptyPiece; // Case vide

/**
 * Chessboard : Classe s'occupant du plateau de jeu. Dispose de fonctions d'entrées
 * sortie et d'un système d'affichage console.
 */
public class Chessboard {
	//Proprietes -------------------------------------------------------------------

	/**
	 * La taille de l'échéquier. Constante valant 8, __ne dois pas changer__ !!
	 */
	private final int BOARD_SIZE = 8;

	/** raw chessboard, tableau 2D 8x8 qui contient des pions */
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	//Methodes ---------------------------------------------------------------------
	
	/* Constructeur du plateau
	 * Se charge d'initialiser chaque case du plateau afin que celui-ci puisse �tre affich� sans piece
	 * */
	
	public Chessboard() {
		this.resetBoard();
	}
	
	/** TODO */
	//Plus joli qu'un long syso 'v'
	public String lettersLine () {
		Boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		s.append("    ");
			for (int i = 0; i < 8; i++) {
				if (isFirst) {
					s.append((char)(i + 97));
					isFirst = false;
				}
				else {
					s.append("   " + (char)(i + 97) );
				}
			}
		s.append("    ");
		s.append("\n");
		return s.toString();
	}
	
	/** TODO*/
	
	public String horizontalLine () {
		Boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		s.append("   ");
		for (int i= 0; i < 8; i++) {
			if (isFirst) {
				s.append("---");
				isFirst = false;
			}
			else {
				s.append(" ---");
			}
		}
		s.append("   ");
		s.append("\n");
		return s.toString();
	}
	
	/** TODO */

	public String numberLine (int number) {
		Boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		s.append(number + " | ");
		for (int i = 0; i < 8; i++) {
			if (isFirst) {
				s.append(board[i][number - 1].getSign());
				isFirst = false;
			}
			else {
				s.append(" | " + board[i][number - 1].getSign());
			}
		}
		s.append(" | " + number);
		s.append("\n");
		return s.toString();
	}
	
	/** TODO */
	
	public String toString () {
		String s = lettersLine();
		for (int i = 8; i > 0; i--) {
			s += horizontalLine();
			s += numberLine(i);
		}
		s += horizontalLine();
		s += lettersLine();
		return s;
	}

	/**
	 * Classe utilitaire pour (re-)définir une case de this.board. La config étant peu conventionnelle (line-colonne),
	 * cela permettra de ne pas se tromper grâce aux indications de l'IDE (ou juste rendre le code + maintenable).
	 * @param line la ligne à intervenir
	 * @param column la colonne à intervenir
	 * @param piece la pièce à mettre dans la case en question
	 */
	private void setPiece(int line, int column, Piece piece) {
		this.board[column][line] = piece;
		// TODO : surcharge de constructeur permettant de faire setPiece("b7", new Tower...) si nécessaire
	}

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
