package Piece;

import Chessboard.vect2D;
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

	/**
	 * voir la doc de Piece.isValidMove.
	 * La reine cumule les propriétés de la tour (horizontalement/verticalement) et du fou (diagonales)
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal, vertical ou diagonal
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return Bishop.isDiagMove(currentPos, target) || Tower.isLinearMove(currentPos, target);
	}

}
