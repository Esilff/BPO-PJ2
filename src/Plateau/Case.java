package Plateau;
import Piece.Piece;
import Piece.EmptyPiece;

public class Case {
	private Piece piece;
	
	public Case() {
		piece = new EmptyPiece();
	}
	
	public void setPiece (Piece piece) {
		this.piece = piece;
	}
	
	public String getSign () {
		return piece.getSign();
	}
}
