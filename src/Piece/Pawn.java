package Piece;
public class Pawn extends Piece {
	public Pawn (Boolean isWhite) {
		super("P", isWhite);
	}
	
	public void play() {
		
	}


	/**
	 * @See Piece.Piece.clone()
	 */
	public Pawn clone() {
		return new Pawn(this.isWhite);
	}

}
