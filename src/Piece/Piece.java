package Piece;

import static Chessboard.Chessboard.BOARD_RECT;

import Chessboard.vect2D;
import Game.BadMoveException;
import Game.Ipiece;

/**
 * Piece : Il s'agit d'une classe abstraite représentant une pièce d'échec (ou un élément graphique vide).
 * @see Game.Ipiece
 */
public abstract class Piece implements Ipiece {
	private final String sign;

	private final Boolean isWhite, isGraphical;
	
	// Constantes. permet de faire this.isWhite = ! IS_WHITE; (n'est pas blanche) par ex.
	public final static Boolean IS_WHITE = true, IS_GRAPHICAL = true;

	public Piece (String sign,Boolean isWhite, Boolean isGraphical) {
		this.sign = sign;
		this.isWhite = isWhite;
		this.isGraphical = isGraphical;
	}
	public Piece (String sign,Boolean isWhite) {
		this(sign, isWhite, !IS_GRAPHICAL);
	}


	/**
	 * Méthode statique qui retourne `vect2D.translationFrom_argA_to_argB(currentPos, target)` mais qui applique les
	 * préconditions suivantes :
	 * - les coordonées absolues de currentPos et target sont différentes entre elles
	 * - les coordonées absolues de currentPos et target sont bien contenues dans le plateau de jeu
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return vect2D.INVALID_VECT si les préconditions NE SONT PAS respectées. Si elles sont respectées, cette méthode
	 *  retourne le vecteur de déplacement entre les deux points passés en paramètres
	 * @see vect2D#difference(vect2D B, vect2D A)
	 * @deprecated
	 */
	public static vect2D isValidMove_computeTranslation(vect2D currentPos, vect2D target) {
		if (vect2D.isOutOfBounds(BOARD_RECT, currentPos) ||
			vect2D.isOutOfBounds(BOARD_RECT, target) ||
			vect2D.isEqual(currentPos, target) ) {
			return vect2D.INVALID_VECT;
		}
		return vect2D.difference(currentPos, target);
	}

	/**
	 * Permet de créer une nouvelle instance d'un sous-type de pièce.
	 * @return La pièce clonée
	 */
	public abstract Ipiece clone();
	
	public String getSign () {
		if (isWhite) {
			return sign.toUpperCase();
		}
		return sign.toLowerCase();
	}
	
	public boolean isWhite() {
		return this.isWhite;
	}
	
}
