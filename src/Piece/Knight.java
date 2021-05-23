package Piece;

import Chessboard.vect2D;
import Game.BadMoveException;
import Game.Game;
import Game.Ipiece;
import Chessboard.Chessboard;

public class Knight extends Piece{
	public final static vect2D DEFAULT_L = new vect2D(2, 1), REVERSED_L = new vect2D(1, 2);
	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}

	public void play(Game game, vect2D originCoord, vect2D newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Knight clone() {
		return new Knight(this.isWhite());
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
		return isMoving_L_pattern(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de trois cases faisant un L
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un cavalier
	 */
	public static boolean isMoving_L_pattern(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (relative_move.equals(vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		// TODO : make obj
		relative_move.x = Math.abs(relative_move.x);
		relative_move.y = Math.abs(relative_move.y);
		return relative_move.equals(DEFAULT_L) || relative_move.equals(REVERSED_L);
	}
}
