package Piece;

import Chessboard.Chessboard;

public class King extends Piece{
	public King(Boolean isWhite) {
		super("R", isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public King clone() {
		return new King(this.isWhite);
	}
}
