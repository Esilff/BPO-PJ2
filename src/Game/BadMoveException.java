package Game;

/**
 * BadMoveException est juste un alias de Exception mais je veux spécifier explicitement que l'on rejette bien la faute
 * au <b>coup</b> du joueur, et non la faute du programmeur
 */
public class BadMoveException extends Exception {
	// Je sais pas ce que c'est, c'est juste Eclipse qui le veut...
	private static final long serialVersionUID = -2468305495887036686L;
	public BadMoveException() { super(); }
    public BadMoveException(String message) { super(message); }
    public BadMoveException(String message, Throwable cause) { super(message, cause); }
    public BadMoveException(Throwable cause) { super(cause); }
}