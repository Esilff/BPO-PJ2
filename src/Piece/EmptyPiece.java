package Piece;

import Chessboard.vect2D;
import Chessboard.Chessboard;

public class EmptyPiece extends Piece{
	public EmptyPiece() {
		super(" ", IS_WHITE, IS_GRAPHICAL);
	}

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord,Boolean isWhite) throws BadMoveException {

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
