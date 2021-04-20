package Plateau;
import Pions.Piece;
import Pions.VoidPiece;

public class Case {
	private Piece piece;
	
	public Case() {
		piece = new VoidPiece();
	}
	
	public void setPiece (Piece piece) {
		this.piece = piece;
	}
	
	public String getSign () {
		return piece.getSign();
	}
}
