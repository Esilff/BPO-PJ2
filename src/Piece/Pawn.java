package Piece;

import Chessboard.vect2D;
import Chessboard.*;

public class Pawn extends Piece {
	private static final vect2D USUAL_STEP = new vect2D(0, 1),
								DOUBLE_STEP = new vect2D(0, 2),
								CAPTURING_STEP = new vect2D(1, 1);
	
	private Boolean isPlayed = false;
	
	private enum MOVE_TYPE {
		USUAL_STEP, DOUBLE_STEP, CAPTURING_STEP, INVALID_MOVE
	};
	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord) throws BadMoveException {
		// Reconnaissance du déplacement
		vect2D relative_move = isValidMove_computeTranslation(originCoord, newCoord);
		MOVE_TYPE moveType = recogniseMove(relative_move);

		Piece target = chessboard.getPiece(newCoord.y, newCoord.x);
		Piece targetBis = chessboard.getPiece(newCoord.y - 1, newCoord.x); //A utiliser pour verifier que rien n'est present sur le chemin, lors d'un doubleStep
		switch (moveType) {
			case CAPTURING_STEP:
				if (target instanceof EmptyPiece || target.isWhite == this.isWhite)
					throw new BadMoveException("Le pion ne peut manger du vide, ou être cannibale...");
				else break;

			case DOUBLE_STEP:
				if (target instanceof EmptyPiece && isPlayed) { //Deja joue
					throw new BadMoveException("Le pion a deja ete joue et ne peut plus avancer de deux cases");
				}
				if (target.isWhite == this.isWhite && !(target instanceof EmptyPiece))
					throw new BadMoveException("Le pion ne peut manger du vide, ou être cannibale...");
				if (targetBis.isWhite == this.isWhite && !(targetBis instanceof EmptyPiece))
					throw new BadMoveException("Le pion ne peut avancer avec un obstacle sur le chemin");
				else break;
				
			case USUAL_STEP:
				if (target instanceof EmptyPiece) break;
				else throw new BadMoveException("Le pion ne peut se déplacer de l'avant sur une case occupée.");

			case INVALID_MOVE:
			default:
				throw new BadMoveException("Coup interdit.");
		}

		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
		this.isPlayed = true;
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
		return this.recogniseMove(relative_move) != MOVE_TYPE.INVALID_MOVE;
	}

	private MOVE_TYPE recogniseMove(vect2D relative_move) {
		if (isMovingToFrontSquare(relative_move)) {
			return MOVE_TYPE.USUAL_STEP;
		} else if (isPawnCaptureStep(relative_move)) {
			return MOVE_TYPE.CAPTURING_STEP;
		} else if (isMovingTo2ndFrontSquare(relative_move)) {
			return MOVE_TYPE.DOUBLE_STEP;
		}
		return MOVE_TYPE.INVALID_MOVE;
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est sur la case en face de lui
	 */
	private boolean isMovingToFrontSquare(vect2D relative_move) {
		return USUAL_STEP.x == relative_move.x
			&& (this.isWhite ? USUAL_STEP.y : -USUAL_STEP.y) == relative_move.y;
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur la diagonale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est celui correspondant à la prise d'une pièce par un pion
	 */
	private boolean isPawnCaptureStep(vect2D relative_move) {
		return CAPTURING_STEP.x == Math.abs(relative_move.x)
				&& (this.isWhite ? USUAL_STEP.y : -USUAL_STEP.y) == relative_move.y;
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de deux cases sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si il s'agit d'un déplacement de deux cases sur l'horizontale
	 */
	private boolean isMovingTo2ndFrontSquare(vect2D relative_move) {
		return DOUBLE_STEP.x == relative_move.x
				&& (this.isWhite ? DOUBLE_STEP.y : -DOUBLE_STEP.y) == relative_move.y;
	}
}
