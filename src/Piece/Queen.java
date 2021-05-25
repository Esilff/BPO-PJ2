package Piece;

import Game.BadMoveException;
import Game.Game;
import vec2.vec2;

public class Queen extends Piece{
	public Queen(Boolean isWhite) {
		super("D",isWhite);
	}

	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		game.checkNoObstaclesInTheWay(originCoord, newCoord);
	}

	public void canMoveTo(Game game, vec2 originCoord, vec2 newCoord, boolean forCheckMate) throws BadMoveException {
		if (!this.isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		if (forCheckMate) {game.checkNoObstaclesInTheWay(originCoord, newCoord, forCheckMate); return;} 
		game.checkNoObstaclesInTheWay(originCoord, newCoord);
	}
	/**
	 * @See Piece.Piece.clone()
	 */
	public Queen clone() {
		return new Queen(this.isWhite());
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * La reine cumule les propriétés de la tour (horizontalement/verticalement) et du fou (diagonales)
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement horizontal, vertical ou diagonal
	 */
	@Override
	public boolean isValidMove(vec2 currentPos, vec2 target) {
		return Bishop.isDiagMove(currentPos, target) || Tower.isLinearMove(currentPos, target);
	}

}
