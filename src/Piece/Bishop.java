package Piece;

import Chessboard.Chessboard;
import Chessboard.vect2D;
import Game.BadMoveException;
import Game.Ipiece;

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

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord, Boolean isWhite) throws BadMoveException {
		Ipiece target = chessboard.getPiece(newCoord.y, newCoord.x);
		if (target.isWhite() == this.isWhite() && !(target instanceof EmptyPiece))
			throw new BadMoveException("Le fou ne peut pas être cannibale...");
		if (!isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		vect2D relativeMove = newCoord.minus(originCoord);
		vect2D step = relativeMove.generate_signum();
		vect2D i = originCoord.clone();
		while (true) {
			i.addAndApply(step);
			if (newCoord.equals(i) ) {
				break;
			}
			if(!(chessboard.getPiece(i.y,i.x) instanceof EmptyPiece)) {
				System.out.println("Le chemin est bloqu� " + i.toString());
				throw new BadMoveException("Le chemin est bloque");
			}
			if (isWhite != this.isWhite()) {
				throw new BadMoveException("Pion adverse");
			}

		}
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le fou se déplace en diagonale
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target est un déplacement en diagonale
	 */
    @Override
    public boolean isValidMove(vect2D currentPos, vect2D target) {
    	return isDiagMove(currentPos, target);
    }

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement en diagonale
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement en diagonale
	 */
    public static boolean isDiagMove(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (vect2D.isEqual(relative_move, vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.x = Math.abs(relative_move.x);
		relative_move.y = Math.abs(relative_move.y);

		return relative_move.x == relative_move.y;
	}
}
