package Piece;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

public class Knight extends Piece{
	public final static vec2 DEFAULT_L = new vec2(2, 1), REVERSED_L = new vec2(1, 2);
	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}

	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
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
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		return isMoving_L_pattern(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de trois cases faisant un L
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un cavalier
	 */
	public static boolean isMoving_L_pattern(vec2 currentPos, vec2 target) {
		vec2 relative_move = isValidMove_computeTranslation(currentPos, target);
		if (relative_move.equals(vec2.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.makeAbs();
		return relative_move.equals(DEFAULT_L) || relative_move.equals(REVERSED_L);
	}
}
