package Piece;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

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


	@Override
	public boolean isEmpty() {
		return true;
	}
	
	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		// assume que ça a été throw dans Game.play()
		
	}
	
	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord, boolean forCheckMate) throws BadMoveException {
		// assume que ça a été throw dans Game.play()
		
	}
}
