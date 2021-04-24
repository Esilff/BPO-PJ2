package Piece;

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

	public abstract void play (Chessboard chessboard, String originCoord, String newCoord);


	
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
