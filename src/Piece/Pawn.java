package Piece;

import Chessboard.*;

public class Pawn extends Piece {
	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		vect2D originCoordConv = vect2D.createFromChessCoord(originCoord);
		vect2D newCoordConv = vect2D.createFromChessCoord(newCoord);
		if (this.isWhite && originCoordConv.x == 6) {
			if (newCoordConv.x == originCoordConv.x - 2)  {
				chessboard.setPiece(newCoord, this);
			}
		}
		else {
			if (newCoordConv.x == originCoordConv.x - 1)  {
				chessboard.setPiece(newCoord, this);
			}
		}
	}


	/**
	 * @See Piece.Piece.clone()
	 */
	public Pawn clone() {
		return new Pawn(this.isWhite);
	}

}
