package Piece;

import Chessboard.Chessboard;

public class Queen extends Piece{
	public Queen(Boolean isWhite) {
		super("D",isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Queen clone() {
		return new Queen(this.isWhite);
	}
}
