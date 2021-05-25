package Piece;

/**
 * BadMoveException est juste un alias de Exception mais je veux spécifier explicitement que l'on rejette bien la faute
 * au <b>coup</b> du joueur, et non la faute du programmeur
 */
public class BadMoveException extends Exception {
    public BadMoveException() { super(); }
    public BadMoveException(String message) { super(message); }
    public BadMoveException(String message, Throwable cause) { super(message, cause); }
    public BadMoveException(Throwable cause) { super(cause); }
}