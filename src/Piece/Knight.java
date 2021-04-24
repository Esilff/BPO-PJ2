package Piece;

import Chessboard.Chessboard;

public class Knight extends Piece{
	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Knight clone() {
		return new Knight(this.isWhite);
	}
}
