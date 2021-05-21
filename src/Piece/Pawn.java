package Piece;

import Chessboard.vect2D;
import Game.BadMoveException;
import Game.Ipiece;
import Chessboard.*;

import static java.lang.Integer.signum;

// TODO : plus qu'à supprimer les deux warnings du deprecated, mise en place d'interface + fix getters et on est bon

public class Pawn extends Piece {

	// PARAMÈTRES PARTAGES

	private static final vect2D USUAL_STEP = new vect2D(0, 1),
								DOUBLE_STEP = new vect2D(0, 2),
								CAPTURING_STEP = new vect2D(1, 1);
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

	// PARAMÈTRES D'OBJET

	/**
	 * Indique si la pièce a été jouée ou non (afin de déterminer si le double step est autorisé ou pas)
	 * @see Pawn#Pawn(Boolean, Boolean)
	 */
	private Boolean isPlayed;

	// MÉTHODES D'INTERFACE

	public Pawn (Boolean isWhite) {
		this(isWhite, false);
	}

	/**
	 * (unused mais c'est pour la stabilité)
	 * Constructeur permettant de modifier isPlayed lors de la création, nécessaire si ce pion est impliqué au sein
	 * d'une config custom. cf. documentation du paramètre isPlayed ci-dessous.
	 * @param isWhite true si le pion est de couleur blanche, false sinon.
	 * @param isPlayed À mettre à true si le pion n'est pas dans la 2e ligne de l'échiquier en face de l'adversaire
	 *                 (rappelons que cette classe ne stocke pas la position du pion, mais plutôt les définitions de
	 *                 comportement)
	 */
	public Pawn (Boolean isWhite, Boolean isPlayed) {
		super("P", isWhite);
		this.isPlayed = isPlayed;
	}

	/**
	 * @see
	 */
	public void play(Chessboard chessboard, vect2D originCoord, vect2D targetCoord, Boolean isPlayerWhite) throws BadMoveException {
		if (isPlayerWhite != this.isWhite()) {
			throw new BadMoveException("Il est interdit au joueur actuel de toucher aux pièces de l'adversaire");
		}

		// Reconnaissance du déplacement
		vect2D relative_move = isValidMove_computeTranslation(originCoord, targetCoord);
		MOVE_TYPE moveType = recogniseMove(relative_move);

		// J'aurais bien voulu séparer ce gros switch dans une méthode à part, mais trop de variables utilisées...
		Ipiece target = chessboard.getPiece(targetCoord.y, targetCoord.x);
		switch (moveType) {
			case CAPTURING_STEP: // manger
				if (target instanceof EmptyPiece || target.isWhite() == this.isWhite())
					throw new BadMoveException("Le pion ne peut manger du vide, ou être cannibale...");
				else break;

			case DOUBLE_STEP: // avancer
				Ipiece targetBis = chessboard.getPiece(originCoord.getY() + signum(relative_move.getY()), originCoord.getX());
				if (isPlayed)
					throw new BadMoveException("Le pion a deja ete joue et ne peut plus avancer de deux cases");
				if (!(targetBis instanceof EmptyPiece))
					throw new BadMoveException("Le pion ne peut avancer avec un obstacle sur son chemin");
				// + préconditions du usual step (d'où l'absence du break)

			case USUAL_STEP:
				if (target instanceof EmptyPiece) break;
				else throw new BadMoveException("Le pion ne peut se déplacer de l'avant sur une case occupée.");

			case INVALID_MOVE:
			default:
				throw new BadMoveException("Coup interdit.");
		}

		// déplacement effectif
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(targetCoord.y, targetCoord.x, this);
		this.isPlayed = true;
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
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
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
			&& (this.isWhite() ? USUAL_STEP.y : -USUAL_STEP.y) == relative_move.y;
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement d'une case sur la diagonale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si le déplacement est celui correspondant à la prise d'une pièce par un pion
	 */
	private boolean isPawnCaptureStep(vect2D relative_move) {
		return CAPTURING_STEP.x == Math.abs(relative_move.x)
				&& (this.isWhite() ? USUAL_STEP.y : -USUAL_STEP.y) == relative_move.y;
	}
	/**
	 * Vérifie si le déplacement entre deux points est un déplacement de deux cases sur l'horizontale
	 * @param relative_move le vecteur de déplacement relatif au pion
	 * @return true si il s'agit d'un déplacement de deux cases sur l'horizontale
	 */
	private boolean isMovingTo2ndFrontSquare(vect2D relative_move) {
		return DOUBLE_STEP.x == relative_move.x
				&& (this.isWhite() ? DOUBLE_STEP.y : -DOUBLE_STEP.y) == relative_move.y;
	}
}