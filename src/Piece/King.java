package Piece;

import java.util.ArrayList;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

public class King extends Piece{
	private static final int ALLOWED_NBR_OF_STEPS = 1;
	private ArrayList<vec2> notAllowed = new ArrayList<vec2>();

	public King(Boolean isWhite) {
		super("R", isWhite);
	}

	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
	}
	
	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord, boolean forCheckMate) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
	}

	@Override
	public boolean isKing() {
		return true;
	}
	/*
	 pour une configuration de finale 2r1t

	vérif échec : 
	Game.play() : si la pièce précédente est capable de manger le roi adverse
				  (si une tour ou un fou ou une dame du camp de la pièce bougée est capable de manger le roi adverse)
	
	vérif mat :  seulement si en échec :
	pour chaque case cible
		si la case cible peut se faire manger par une pièce adverse
			= bourrin : est-ce que cette case peut se faire manger par les pièces adverses
	si le nombre de case cible = nbre case morte alors en mat
	
	vérif pat : 
	(Si le nombre de pièces adverses = nombre de pièces joueur actuel)
	code de mat mais on inverse la condition "si en échec"
	si nombre de 

	 */
	
	
	/**
	 * @See Piece.Piece.clone()
	 */
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
	 * Vérifie si le déplacement entre deux points est un déplacement en diagonale
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
