package Piece;

public class King extends Piece{
	public King(Boolean state) {
		super("R", state);
	}
	
	public void play() {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public King clone() {
		return new King(this.isWhite);
	}
}
