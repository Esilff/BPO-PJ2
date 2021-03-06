package Piece;

import java.util.ArrayList;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

public class King extends Piece{
	private static final int ALLOWED_NBR_OF_STEPS = 1;
	private ArrayList<vec2> notAllowed = new ArrayList<>();

	public King(Boolean isWhite) {
		super("R", isWhite);
	}

	@Override
	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
	}

	@Override
	public boolean isKing() {
		return true;
	}

	@Override
	public King clone() {
		return new King(this.isWhite());
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le roi se déplace d'une case autour de lui
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target est un déplacement d'exactement 1 case
	 */
	@Override
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		return isMovingExactlyWithOneStep(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacementt d'un pas
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement d'exactement 1 case
	 */
	public static boolean isMovingExactlyWithOneStep(vec2 currentPos, vec2 target) {
		vec2 relative_move = isValidMove_computeTranslation(currentPos, target);
		if (relative_move.equals(vec2.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.makeAbs();
		return relative_move.getX() <= ALLOWED_NBR_OF_STEPS
			&& relative_move.getY() <= ALLOWED_NBR_OF_STEPS;
	}
}
