package Application;

//Classe qui va se charger de gérer l'affichage du plateau de jeu
import java.util.ArrayList;

public class Plateau {
	//Proprietes -------------------------------------------------------------------
	
	/*Plateau de jeu, soit un simple tableau de case à deux dimensions*/
	
	private Case[][] plateau = new Case[8][8];
	
	//Methodes ---------------------------------------------------------------------
	
	/* Constructeur du plateau
	 * Se charge d'initialiser chaque case du plateau afin que celui-ci puisse être affiché sans piece
	 * */
	
	public Plateau () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				plateau[i][j] = new Case();
			}
		}
	}
	
	/* Affichage de la ligne des lettres
	 * @return une ligne de 8 lettres espacées
	 * */
	
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
				s.append(plateau[number - 1][i].toString());
				isFirst = false;
			}
			else {
				s.append(" | " + plateau[number - 1][i].toString());
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
}
