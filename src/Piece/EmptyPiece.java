package Piece;
public class EmptyPiece extends Piece{
	public EmptyPiece() {
		super(" ", true);
	}
	
	public void play () {
		
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public EmptyPiece clone() {
		return new EmptyPiece();
	}
}
