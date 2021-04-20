package Plateau;
//Classe qui va se charger de gérer l'affichage du plateau de jeu
import java.util.ArrayList;

import Pions.Pawn;
import Pions.Tower;
import Pions.Cavalier;
import Pions.Fou;
import Pions.Reine;
import Pions.Roi;

public class Plateau {
	//Proprietes -------------------------------------------------------------------
	
	/*Plateau de jeu, soit un simple tableau de case à deux dimensions*/
	
	private Case[][] plateau = new Case[8][8];
	
	//Methodes ---------------------------------------------------------------------
	
	/* Constructeur du plateau
	 * Se charge d'initialiser chaque case du plateau afin que celui-ci puisse être affiché sans piece
	 * */
	
	public Plateau () {
		this.emptyBoard();
		this.fillBoard();
	}
	
	/* Affichage de la ligne des lettres
	 * @return une ligne de 8 lettres espacées
	 * */
	
	
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
	
	/* Affichage de la ligne horizontale
	 * @return une ligne composée d'un enchainement de trois tirets espacés 
	 * */
	
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
	
	/* Affichage de la ligne de la case
	 * @return l'index horizontal de la case et le contenu des cases pour cet index
	 * */
	
	public String numberLine (int number) {
		Boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		s.append(number + " | ");
		for (int i = 0; i < 8; i++) {
			if (isFirst) {
				s.append(plateau[i][number - 1].getSign());
				isFirst = false;
			}
			else {
				s.append(" | " + plateau[i][number - 1].getSign());
			}
		}
		s.append(" | " + number);
		s.append("\n");
		return s.toString();
	}
	
	/* Met en place les méthodes précédentes afin d'afficher le plateau de jeu
	 * @return le plateau de jeu
	 * */
	
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
	
	
	//Sous-traitance du constructeur
	
	public void emptyBoard () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				plateau[i][j] = new Case();
			}
		}
	}
	
	public void fillBoard () {
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				switch (j) {
				case 6 :
					plateau[i][j].setPiece(new Pawn(false));
					break;
				case 1 :
					plateau[i][j].setPiece(new Pawn(true));
					break;
				case 7 :
					switch (i) {
					case 0 :
						plateau[i][j].setPiece(new Tower(false));
						break;
					case 1 :
						plateau[i][j].setPiece(new Cavalier(false));
						break;
					case 2 :
						plateau[i][j].setPiece(new Fou(false));
						break;
					case 3 :
						plateau[i][j].setPiece(new Roi(false));
						break;
					case 4 :
						plateau[i][j].setPiece(new Reine(false));
						break;
					case 5 :
						plateau[i][j].setPiece(new Fou(false));
						break;
					case 6 :
						plateau[i][j].setPiece(new Cavalier(false));
						break;
					case 7 :
						plateau[i][j].setPiece(new Tower(false));
						break;
					}
					break;
				case 0 :
					switch (i) {
					case 0 :
						plateau[i][j].setPiece(new Tower(true));
						break;
					case 1 :
						plateau[i][j].setPiece(new Cavalier(true));
						break;
					case 2 :
						plateau[i][j].setPiece(new Fou(true));
						break;
					case 3 :
						plateau[i][j].setPiece(new Roi(true));
						break;
					case 4 :
						plateau[i][j].setPiece(new Reine(true));
						break;
					case 5 :
						plateau[i][j].setPiece(new Fou(true));
						break;
					case 6 :
						plateau[i][j].setPiece(new Cavalier(true));
						break;
					case 7 :
						plateau[i][j].setPiece(new Tower(true));
						break;
					}
					break;
				
				
				}
			}
		}
	}
}
