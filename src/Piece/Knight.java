package Piece;

import Chessboard.vect2D;

public class Knight extends Piece{
	public final static vect2D[] ALLOWED_MOVES = new vect2D[]{
		new vect2D(-1, 2),  new vect2D(1, 2), // TOP LEFT, 	TOP RIGHT
		new vect2D(-1, -2), new vect2D(1, -2) // BOTTOM LEFT,	BOTTOM RIGHT (L)
	};


	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}
	
	public void play() {
		
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

		for (vect2D allowed: ALLOWED_MOVES) {
			if (vect2D.isEqual(allowed, relative_move)) {
				return true;
			}
		}

		return false;
	}
}
