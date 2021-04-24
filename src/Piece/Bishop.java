package Piece;

import Chessboard.vect2D;

/**
 * Bishop : classe représentant la pièce du fou. Se déplace en diagonale.
 */
public class Bishop extends Piece{
	public Bishop(Boolean isWhite) {
		super ("F", isWhite);
	}


	/**
	 * @See Piece.Piece.clone()
	 */
	public Bishop clone() {
		return new Bishop(this.isWhite);
	}

	public void play() {
		
	}
}
