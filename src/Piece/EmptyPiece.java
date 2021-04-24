package Piece;

import Chessboard.vect2D;

public class EmptyPiece extends Piece{
	public EmptyPiece() {
		super(" ", IS_WHITE, IS_GRAPHICAL);
	}
	
	public void play () {
		
	}

	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return false; // indéplacable
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public EmptyPiece clone() {
		return new EmptyPiece();
	}
}
