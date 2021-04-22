package Piece;

public class Knight extends Piece{
	public Knight(Boolean state) {
		super("C", state);
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
