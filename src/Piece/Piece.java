package Piece;

import static Chessboard.Chessboard.BOARD_RECT;
import Chessboard.Chessboard;
import Chessboard.vect2D;

/**
 * Piece : Il s'agit d'une classe abstraite représentant une pièce d'échec (ou un élément graphique vide).
 */
public abstract class Piece {
	private final String sign;

	// une pièce ne peut changer subitement de forme en cours de partie...
	protected final Boolean isWhite, isGraphical;
	// valeurs actives plus haut
	public final static Boolean IS_WHITE = true, IS_GRAPHICAL = true;

	public Piece (String sign,Boolean isWhite, Boolean isGraphical) {
		this.sign = sign;
		this.isWhite = isWhite;
		this.isGraphical = isGraphical;
	}
	public Piece (String sign,Boolean isWhite) {
		this(sign, isWhite, !IS_GRAPHICAL);
	}

	public abstract void play (Chessboard chessboard, vect2D originCoord, vect2D newCoord) throws BadMoveException;

	/**
	 * Cette méthode définit la règle de déplacement du pion. Elle a été pensée statique, en prenant en compte que un
	 * pion n'a pas besoin de connaitre sa position (c'est plutôt au plateau de le faire). Donc, cette méthode à elle-
	 * seule ne peut déterminer si le coup est jouable, car il est nécessaire de vérifier d'autres choses (par exemple,
	 * y a-t-il une pièce sur son chemin ?)
	 * @param currentPos
	 * @param target
	 * @return
	 */
	public abstract boolean isValidMove(vect2D currentPos, vect2D target);

	/**
	 * Méthode statique qui retourne `vect2D.translationFrom_argA_to_argB(currentPos, target)` mais qui applique les
	 * préconditions suivantes :
	 * - les coordonées absolues de currentPos et target sont différentes entre elles
	 * - les coordonées absolues de currentPos et target sont bien contenues dans le plateau de jeu
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return vect2D.INVALID_VECT si les préconditions NE SONT PAS respectées. Si elles sont respectées, cette méthode
	 *  retourne le vecteur de déplacement entre les deux points passés en paramètres
	 * @see vect2D#translationFrom_argA_to_argB(vect2D A, vect2D B)
	 */
	public static vect2D isValidMove_computeTranslation(vect2D currentPos, vect2D target) {
		if (vect2D.isOutOfBounds(BOARD_RECT, currentPos) ||
			vect2D.isOutOfBounds(BOARD_RECT, target) ||
			vect2D.isEqual(currentPos, target) ) {
			return vect2D.INVALID_VECT;
		}
		return vect2D.translationFrom_argA_to_argB(currentPos, target);
	}

	/**
	 * Permet de créer une nouvelle instance d'un sous-type de pièce.
	 * @return La pièce clonée
	 */
	public abstract Piece clone ();
	
	public String getSign () {
		if (isWhite) {
			return sign.toUpperCase();
		}
		return sign.toLowerCase();
	}
}
