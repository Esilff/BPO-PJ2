package Game;

import Chessboard.Chessboard;
import Chessboard.vect2D;

public interface Ipiece extends Cloneable {

	public void play (Chessboard chessboard, vect2D originCoord, vect2D newCoord, Boolean isWhite) throws BadMoveException;


	/**
	 * Cette méthode définit la règle de déplacement du pion. Elle a été pensée statique, en prenant en compte que un
	 * pion n'a pas besoin de connaitre sa position (c'est plutôt au plateau de le faire). Donc, cette méthode à elle-
	 * seule ne peut déterminer si le coup est jouable, car il est nécessaire de vérifier d'autres choses (par exemple,
	 * y a-t-il une pièce sur son chemin ?)
	 * @param currentPos
	 * @param target
	 * @return
	 */
	public boolean isValidMove(vect2D currentPos, vect2D target);
	
	public boolean isWhite();

	public Ipiece clone();

	public String getSign();
	
	/*implicite : méthode clone()*/
}
