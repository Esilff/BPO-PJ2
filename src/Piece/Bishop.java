package Piece;

public class Bishop extends Piece{
	public Bishop(Boolean state) {
		super ("F", state);
	}


	/**
	 * @See Piece.Piece.clone()
	 */
	public Bishop clone() {
		return new Bishop(this.isWhite);
	}

	public void play() {
		
	}
}
