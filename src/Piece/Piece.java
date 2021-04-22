package Piece;

public abstract class Piece {
	private String name; // suppr
	private String sign;

	// une pièce ne peut changer subitement de couleur en cours de partie...
	protected final Boolean isWhite;

	// suppr
	public Piece (String sign,Boolean isWhite) {
		this.sign = sign;
		this.isWhite = isWhite;
	}
	
	protected abstract void play ();

	/**
	 * Permet de créer une nouvelle instance d'un sous-type de pièce.
	 * @return La pièce clonée
	 */
	public abstract Piece clone ();
	
	public String getSign () {
		if (isWhite) {
			return sign.toUpperCase();
		}
		return sign.toLowerCase();
	}
}
