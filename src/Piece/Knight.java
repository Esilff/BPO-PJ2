package Piece;

import Chessboard.vect2D;
import Chessboard.Chessboard;

public class Knight extends Piece{
	public final static vect2D DEFAULT_L = new vect2D(2, 1), REVERSED_L = new vect2D(1, 2);
	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord) throws BadMoveException {
		Boolean moveType = isMovingExactlyWithOneStep(originCoord, newCoord);
		vect2D boxSize = new vect2D(chessboard.BOARD_SIZE, chessboard.BOARD_SIZE);
		Piece target = chessboard.getPiece(newCoord.y, newCoord.x);
		if(!isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Le coup est invalide");
		}
		if (target.isWhite == this.isWhite)
			throw new BadMoveException("Le cavalier ne peut pas être cannibale...");
		if (vect2D.isOutOfBounds(boxSize, newCoord)) 
			throw new BadMoveException("Coup interdit.");
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Knight clone() {
		return new Knight(this.isWhite);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le cavalier fait un bond de trois cases en formant un L
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un cavalier
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return isMovingExactlyWithOneStep(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de trois cases faisant un L
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un cavalier
	 */
	public static boolean isMovingExactlyWithOneStep(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (vect2D.isEqual(relative_move, vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.x = Math.abs(relative_move.x);
		relative_move.y = Math.abs(relative_move.y);
		return vect2D.isEqual(DEFAULT_L, relative_move) || vect2D.isEqual(REVERSED_L, relative_move);
	}
}
