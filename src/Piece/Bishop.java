package Piece;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

/**
 * Bishop : classe représentant la pièce du fou. Se déplace en diagonale.
 */
public class Bishop extends Piece{
	public Bishop(Boolean isWhite) {
		super ("F", isWhite);
	}


	/**
	 * @See Piece.Piece.clone()
	 */
	public Bishop clone() {
		return new Bishop(this.isWhite());
	}

	public void play(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		game.checkNoObstaclesInTheWay(originCoord, newCoord);
	}
	
	/**
	 * voir la doc de Piece.isValidMove.
	 * Le fou se déplace en diagonale
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target est un déplacement en diagonale
	 */
    @Override
    public boolean isValidMove(vec2 currentPos, vec2 target) {
    	return isDiagMove(currentPos, target);
    }

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement en diagonale
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement en diagonale
	 */
    public static boolean isDiagMove(vec2 currentPos, vec2 target) {
		vec2 relative_move = isValidMove_computeTranslation(currentPos, target);
		if (relative_move.equals(vec2.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.makeAbs();

		return relative_move.getX() == relative_move.getY();
	}
}
