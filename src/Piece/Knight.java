package Piece;

public class Knight extends Piece{
	public Knight(Boolean isWhite) {
		super("C", isWhite);
	}
	
	public void play() {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public Knight clone() {
		return new Knight(this.isWhite);
	}
}
