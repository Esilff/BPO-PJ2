package Piece;

import Chessboard.vect2D;
import Game.BadMoveException;
import Game.Game;
import Chessboard.Chessboard;

public class EmptyPiece extends Piece{
	public EmptyPiece() {
		super(" ", IS_WHITE, IS_GRAPHICAL);
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

	public void play(Game game, vect2D originCoord, vect2D newCoord) throws BadMoveException {
		// assume que ça a été throw dans Game.play()
		
	}
}
