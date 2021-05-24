package Game;

import vec2.vec2;

public interface Ipiece extends Cloneable {

	public void canMoveTo (Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException;


	/**
	 * Cette méthode définit la règle de déplacement du pion. Elle a été pensée statique, en prenant en compte que un
	 * pion n'a pas besoin de connaitre sa position (c'est plutôt au plateau de le faire). Donc, cette méthode à elle-
	 * seule ne peut déterminer si le coup est jouable, car il est nécessaire de vérifier d'autres choses (par exemple,
	 * y a-t-il une pièce sur son chemin ?)
	 * @param currentPos
	 * @param target
	 * @return
	 */
	public boolean isValidMove(vec2 currentPos, vec2 target);
	
	public boolean isWhite();

	public Ipiece clone();

	public String getSign();
	
	/*implicite : méthode clone()*/
}
