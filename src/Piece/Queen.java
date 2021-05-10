package Piece;

import Chessboard.vect2D;
import Chessboard.Chessboard;

public class Queen extends Piece{
	public Queen(Boolean isWhite) {
		super("D",isWhite);
	}

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord, Boolean isWhite) throws BadMoveException {
		
		Piece target = chessboard.getPiece(newCoord.y, newCoord.x);
		if (target.isWhite == this.isWhite && !(target instanceof EmptyPiece))
			throw new BadMoveException("Le fou ne peut pas être cannibale...");
		if (!isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		if (isWhite != this.isWhite) {
			throw new BadMoveException("Pion adverse");
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
				System.out.println("Le chemin est bloqué " + i.toString());
				throw new BadMoveException("Le chemin est bloque");
			}
		}
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Queen clone() {
		return new Queen(this.isWhite);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * La reine cumule les propriétés de la tour (horizontalement/verticalement) et du fou (diagonales)
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal, vertical ou diagonal
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return Bishop.isDiagMove(currentPos, target) || Tower.isLinearMove(currentPos, target);
	}

}
