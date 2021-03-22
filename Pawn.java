package BaseClass;

public class Pawn extends Piece {
	public Pawn (Boolean isWhite) {
		super('p', isWhite);
	}
	
	public void play() {
		
	}
	
	public String toString() {
		String s = null;
		s += super.getSign();
		if (super.getIsWhite() == true) {	
			s.toUpperCase();
		}
		return s;
	}
}
