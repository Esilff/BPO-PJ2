package Piece;

import Chessboard.vect2D;
import Chessboard.*;

public class Pawn extends Piece {
	private static final vect2D USUAL_STEP = new vect2D(0, 1),
								DOUBLE_STEP = new vect2D(0, 2),
								CAPTURING_STEP = new vect2D(1, 1);
	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	public void play(Chessboard chessboard, String originCoord, String newCoord) {
		vect2D originCoordConv = vect2D.createFromChessCoord(originCoord);
		vect2D newCoordConv = vect2D.createFromChessCoord(newCoord);
		if (this.isWhite && originCoordConv.x == 6) {
			if (newCoordConv.x == originCoordConv.x - 2)  {
				chessboard.setPiece(newCoord, this);
			}
		}
		else {
			if (newCoordConv.x == originCoordConv.x - 1)  {
				chessboard.setPiece(newCoord, this);
			}
		}
	}

	/**
	 * @see Piece#clone()
	 */
	public Pawn clone() {
		return new Pawn(this.isWhite);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le pion peut se déplacer horizontalement d'une case, peut prendre un pion situé dans sa diagonale adjacente, ou
	 * de deux case si n'a jamais joué. Cela définis la validité d'un mouvement, mais pas si le coup est effectivement
	 * jouable (par ex : Ne peut pas se déplacer en diagonale si il n'y a pas de pion à prendre)
	 * Ducoup, pas sûr que ce soit effectivement utilisé
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un pion
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (vect2D.isEqual(relative_move, vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		// trié par ordre d'utilisation moyen
		return isMovingToFrontSquare(relative_move)
				|| isPawnCaptureStep(relative_move)
				|| isMovingTo2ndFrontSquare(relative_move);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est sur la case en face de lui
	 */
	public static boolean isMovingToFrontSquare(vect2D relative_move) {
		return vect2D.isEqual(USUAL_STEP, relative_move);
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur la diagonale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est celui correspondant à la prise d'une pièce par un pion
	 */
	public static boolean isPawnCaptureStep(vect2D relative_move) {
		return CAPTURING_STEP.y == relative_move.y && CAPTURING_STEP.x == Math.abs(relative_move.x);
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de deux cases sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si il s'agit d'un déplacement de deux cases sur l'horizontale
	 */
	public static boolean isMovingTo2ndFrontSquare(vect2D relative_move) {
		return vect2D.isEqual(DOUBLE_STEP, relative_move);
	}
}
