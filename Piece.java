package BaseClass;

public abstract class Piece {
	private char sign;
	private Boolean isWhite;
	
	public Piece (char sign, Boolean isWhite) {
		this.sign = sign;
		this.isWhite = isWhite;
	}
	
	public char getSign() {
		return sign;
	}
	
	public Boolean getIsWhite () {
		return isWhite;
	}
	
	public abstract void play ();
	
	@Override
	public abstract String toString();
}
