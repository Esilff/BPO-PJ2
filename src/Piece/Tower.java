package Piece;

import Chessboard.vect2D;

public class Tower extends Piece{
	public Tower (Boolean isWhite) {
		super("T", isWhite);
	}
	
	public void play() {
		
	}

	public Tower clone() {
		return new Tower(this.isWhite);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * La tour se déplace le long des colones ou des rangées
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal ou vertical
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return isLinearMove(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement le long des colones ou des rangées
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal ou vertical
	 */
	public static boolean isLinearMove(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (vect2D.isEqual(relative_move, vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		// Déplacement sur un axe uniquement
		return relative_move.x == 0 || relative_move.y == 0;
	}
}
