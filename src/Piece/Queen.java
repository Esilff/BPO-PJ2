package Piece;

import Chessboard.vect2D;
import Chessboard.Chessboard;

public class Queen extends Piece{
	public Queen(Boolean isWhite) {
		super("D",isWhite);
	}

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord) throws BadMoveException {
		Piece target = chessboard.getPiece(newCoord.y, newCoord.x);
		if (target.isWhite == this.isWhite && !(target instanceof EmptyPiece))
			throw new BadMoveException("Le fou ne peut pas être cannibale...");
		if (!isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
	}

	/**
	 * @see Piece#clone()
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
