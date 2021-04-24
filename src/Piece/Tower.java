package Piece;

import Chessboard.Chessboard;

public class Tower extends Piece{
	public Tower (Boolean isWhite) {
		super("T", isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		
	}

	public Tower clone() {
		return new Tower(this.isWhite);
	}
}
