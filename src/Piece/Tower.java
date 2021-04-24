package Piece;

public class Tower extends Piece{
	public Tower (Boolean isWhite) {
		super("T", isWhite);
	}
	
	public void play() {
		
	}

	public Tower clone() {
		return new Tower(this.isWhite);
	}
}
