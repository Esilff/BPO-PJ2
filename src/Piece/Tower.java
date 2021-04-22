package Piece;

public class Tower extends Piece{
	public Tower (Boolean state) {
		super("T", state);
	}
	
	public void play() {
		
	}

	public Tower clone() {
		return new Tower(this.isWhite);
	}
}
