package Piece;

public abstract class Piece {
	private String name;
	private String sign;
	private Boolean isWhite;
	
	public Piece (String name,Boolean state) {
		this.name = name;
		sign = name.substring(0,1);
		isWhite = state;
	}
	
	protected abstract void play ();
	
	public String getSign () {
		if (isWhite) {
			return sign.toUpperCase();
		}
		return sign.toLowerCase();
	}
}
