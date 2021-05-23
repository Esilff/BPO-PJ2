package Piece;

import Chessboard.vec2;
import Game.BadMoveException;
import Game.Game;

public class EmptyPiece extends Piece{
	public EmptyPiece() {
		super(" ", IS_WHITE, IS_GRAPHICAL);
	}
	
	@Override
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		return false; // indéplacable
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public EmptyPiece clone() {
		return new EmptyPiece();
	}

	public void play(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		// assume que ça a été throw dans Game.play()
		
	}
}
