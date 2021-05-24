package Piece;

import Game.BadMoveException;
import Game.Game;
import Game.Ipiece;
import vec2.vec2;
import Chessboard.*;

import static java.lang.Integer.signum;

public class Pawn extends Piece {

	// PARAMÈTRES PARTAGES

	private static final vec2 USUAL_STEP = new vec2(0, 1),
								DOUBLE_STEP = new vec2(0, 2),
								CAPTURING_STEP = new vec2(1, 1);
	/**
	 * Une enum représentant le type de mouvement d'un pion
	 * Un pion possède trois patterns de déplacements distincts :
	 * - USUAL_STEP : Un pas vers l'avant (par défaut)
	 * - DOUBLE_STEP : Deux pas vers l'avant (si jamais joué)
	 * - CAPTURING_STEP : Un pas en diagonale (si mange une pièce adverse)
	 * - INVALID_MOVE : Un mouvement qui ne correspond à aucun des cas précédents (action à rejeter)
	 */
	private enum MOVE_TYPE {
		USUAL_STEP, DOUBLE_STEP, CAPTURING_STEP, INVALID_MOVE
	}

	// MÉTHODES D'INTERFACE

	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	/**
	 * @see
	 */

	public void play(Game game, vec2 originCoord, vec2 targetCoord) throws BadMoveException {
		// Reconnaissance du déplacement
		vec2 relative_move = isValidMove_computeTranslation(originCoord, targetCoord);
		MOVE_TYPE moveType = recogniseMove(relative_move);

		// J'aurais bien voulu séparer ce gros switch dans une méthode à part, mais trop de variables utilisées...
		Ipiece target = game.getCloneOfPiece(targetCoord.getY(), targetCoord.getX());
		switch (moveType) {
			case CAPTURING_STEP: // manger
				if (target instanceof EmptyPiece)
					throw new BadMoveException("Le pion ne peut manger du vide");
				else break;

			case DOUBLE_STEP: // avancer
				Ipiece targetBis = game.getCloneOfPiece(originCoord.getY() + signum(relative_move.getY()), originCoord.getX());
				if (isPlayed(originCoord))
					throw new BadMoveException("Le pion a deja ete joue et ne peut plus avancer de deux cases");
				if (!(targetBis instanceof EmptyPiece))
					throw new BadMoveException("La case après la case suivante est occupée");
				// + préconditions du usual step (d'où l'absence du break)

			case USUAL_STEP:
				if (target instanceof EmptyPiece) break;
				else throw new BadMoveException("Le pion ne peut se déplacer de l'avant sur une case occupée.");

			case INVALID_MOVE:
			default:
				throw new BadMoveException("Coup interdit.");
		}
	}
	
	public boolean isPlayed(vec2 originCoord) {
		if (this.isWhite()) {
			return originCoord.getY() != 1;
		} else {
			return originCoord.getY() != Chessboard.BOARD_RECT.getY() - 2;
		}
	}

	/**
	 * @see Piece#clone()
	 */
	public Pawn clone() {
		return new Pawn(this.isWhite());
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le pion peut se déplacer horizontalement d'une case, peut prendre un pion situé dans sa diagonale adjacente, ou
	 * de deux case si n'a jamais joué. Cela définis la validité d'un mouvement, mais pas si le coup est effectivement
	 * jouable (par ex : Ne peut pas se déplacer en diagonale si il n'y a pas de pion à prendre)
	 * Du coup, pas sûr que ce soit effectivement utilisé
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target correspond au déplacement d'un pion
	 */
	@Override
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		vec2 relative_move = isValidMove_computeTranslation(currentPos, target);
		return this.recogniseMove(relative_move) != MOVE_TYPE.INVALID_MOVE;
	}

	// MÉTHODES INTERNES

	/**
	 * Donné un déplacement relatif, vérifie de quel type de déplacement il s'agit.
	 * @param relative_move Le déplacement relatif du présumé pion
	 * @return une valeur de l'énumération {@link MOVE_TYPE}. En particulier, INVALID_MOVE si le coup n'est pas reconnu
	 * 		   ou le vecteur d'entrée n'est pas valide
	 * @see MOVE_TYPE
	 */
	private MOVE_TYPE recogniseMove(vec2 relative_move) {
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
	private boolean isMovingToFrontSquare(vec2 relative_move) {
		return relative_move.equals(new vec2(
				USUAL_STEP.getX(),
				this.isWhite() ? USUAL_STEP.getY() : -USUAL_STEP.getY()
		));
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur la diagonale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est celui correspondant à la prise d'une pièce par un pion
	 */
	private boolean isPawnCaptureStep(vec2 relative_move) {
		return CAPTURING_STEP.getX() == Math.abs(relative_move.getX())
				&& (this.isWhite() ? USUAL_STEP.getY() : -USUAL_STEP.getY()) == relative_move.getY();
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de deux cases sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si il s'agit d'un déplacement de deux cases sur l'horizontale
	 */
	private boolean isMovingTo2ndFrontSquare(vec2 relative_move) {
		return relative_move.equals(new vec2(
				DOUBLE_STEP.getX(),
				this.isWhite() ? DOUBLE_STEP.getY() : -DOUBLE_STEP.getY()
		));
	}
}

// (I really have to find a better job ...)
