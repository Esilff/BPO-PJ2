package Piece;

public class Queen extends Piece{
	public Queen(Boolean isWhite) {
		super("Q",isWhite);
	}
	
	public void play() {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Queen clone() {
		return new Queen(this.isWhite);
	}
}
