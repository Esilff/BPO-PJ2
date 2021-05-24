 package Piece;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

public class Tower extends Piece{
	public Tower (Boolean isWhite) {
		super("T", isWhite);
	}
	
	
	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		game.checkNoObstaclesInTheWay(originCoord, newCoord);
	}


	public Tower clone() {
		return new Tower(this.isWhite());
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * La tour se déplace le long des colones ou des rangées
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal ou vertical
	 */
	@Override
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		return isLinearMove(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement le long des colones ou des rangées
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal ou vertical
	 */
	public static boolean isLinearMove(vec2 currentPos, vec2 target) {
		vec2 relative_move = isValidMove_computeTranslation(currentPos, target);
		if (relative_move.equals(vec2.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		// Déplacement sur un axe uniquement
		return relative_move.getX() == 0 || relative_move.getY() == 0;
	}
}
