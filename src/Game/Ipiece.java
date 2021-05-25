package Game;

import vec2.vec2;

public interface Ipiece extends Cloneable {

	/**
	 * Cette méthode définit la règle de déplacement du pion. Elle a été pensée statique, en prenant en compte que un
	 * pion n'a pas besoin de connaitre sa position (c'est plutôt au plateau de le faire). Donc, cette méthode à elle-
	 * seule ne peut déterminer si le coup est jouable, car il est nécessaire de vérifier d'autres choses (par exemple,
	 * y a-t-il une pièce sur son chemin ?)
	 * @param currentPos la coordonnée actuelle de la pièce
	 * @param target la coordonnée cible de la pièce
	 * @return true si le coup est valide
	 */
	boolean isValidMove(vec2 currentPos, vec2 target);

	/**
	 * Cette méthode vérifie si la pièce actuelle est capable de manger à une position donnée
	 * @param game le gestionnaire du jeu qui sera nécessaire pour lire le plateau
	 * @param originCoord la coordonnée actuelle de la pièce
	 * @param newCoord la coordonnée cible de la pièce
	 * @throws BadMoveException si le coup est invalide.
	 */
	void canMoveTo (Game game, vec2 originCoord, vec2 newCoord) throws BadMoveException;
	
	boolean isEmpty();
	boolean isKing();
	
	/**
	 * @return true si la pièce est de couleur blanche, false sinon
	 */
	boolean isWhite();
	
	/**
	 * @return le caractère graphique de la pièce (s'affiche en majuscule selon la couleur de la pièce)
	 */
	String getSign();
	
	Ipiece clone(); // javadoc implicite de Cloneable
}
