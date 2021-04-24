package Piece;

import Chessboard.vect2D;

public class Pawn extends Piece {
	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	public void play() {
		
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le pion avance d'une case à l'avant, mais peut avancer de deux en début de partie
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un pion
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return false; // TODO !!
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Pawn clone() {
		return new Pawn(this.isWhite);
	}

}
