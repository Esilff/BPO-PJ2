package vec2; // est stable

import static java.lang.Integer.signum;

/**
 * Un vecteur, une dimention, une coordonnée, etc. Sous ces mots barbares, ce n'est qu'un couple d'entiers
 * possédant des méthodes de manipulation usuelles de vecteurs et des méthodes utilitaires
 * Son utilisation est similaire au vec2 du GLSL, sauf que ça respecte l'encapsulation
 */
public class vec2 implements Cloneable {
    private int x, y;

    public vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public vec2() {
		this(0, 0);
	}

	public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public vec2 clone() {
    	return new vec2(this.x, this.y);
    }
    
    /**
     * Applique
     * @return new vec2( this.signum(this.x), this.signum(signe de this.y) )
     */
    public vec2 generate_signum() {
        return new vec2(signum(this.x), signum(this.y));
    }


    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void set(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    /* CONSTANTES -------------------------------------------------------------- */
    public static final vec2 INVALID_VECT = new vec2(-255, -255);

    /* METHODES UTILITAIRES -------------------------------------------------------------- */

    /**
     * Soit A = this et B = vecteur donné en paramètre. Effectue l'opération A - B et retourne le résultat en tant que
     * <b>nouveau vecteur</b> (ne modifie donc pas this), un peu comme en GLSL.
     * Ainsi, l'ordre est important ! Si on a deux points A et B, et qu'on veut le vecteur AB $$ \vec{AB} $$, la
     * formule est B - A. Exemple : <pre>vec2 relativeMove = tragetPos.minus(currentPos);</pre> et non l'inverse
     * @param B l'opérande droite de la soustraction
     * @return new vec2(this.x - B.x, this.y - B.y);
     */
    public vec2 minus(vec2 B) {
        return new vec2(this.x - B.x, this.y - B.y);
    }
    
    /**
     * Effectue la somme entre le vecteur courant et celui passé en paramètre.
     * Le résultat se trouve dans le vecteur courant. 
     * @param vect
     */
    public void addAndApply(vec2 vect) {
    	addAndApply(vect.x, vect.y);
    }

    public void addAndApply(int x, int y) {
    	this.x += x;
    	this.y += y;
    }
    
    public void makeAbs() {
    	this.x = Math.abs(this.x);
    	this.y = Math.abs(this.y);
    }

    /**
     * Détermine si un point⁽¹⁾ est à l'extérieur des limites de la boite⁽²⁾ actuelle
     * Exemple : par rapport à un échiquier 8x8, le point de position (1,8) est hors du plateau de jeu, mais pas (0, 7)
     * ⁽¹⁾ cf. commentaire du param 1
     * ⁽²⁾ représenté par un vec2 dont la coordonnée x représente sa largeur et la coordonée y sa hauteur
     * @param dotPosition Le vecteur représentant la position du point à vérifier depuis l'origine.
     * @return vrai si le point est hors des bordures.
     */
    public boolean isOutOfBounds(vec2 dotPosition) {
        return  dotPosition.x < 0 || dotPosition.x >= this.x ||
                dotPosition.y < 0 || dotPosition.y >= this.y;
    }

    /**
     * Teste l'égalité entre le vecteur actuel et celui passé en paramètre.
     * @param v Vecteur à comparer avec le vecteur actuel
     * @return Vrai si les coordonées de this sont identiques aux coordonées de v, faux sinon
     */
    public boolean equals(vec2 v) {
        return this.x == v.x && this.y == v.y;
    }
}