package Chessboard;

import java.util.Locale;

/**
 * Une struct représentant un couple de deux integers, à utiliser pour définir un déplacement ou la position d'un point.
 * Java dispose déjà du type Vector2D (ie. Vect2d de java != vect2D de chez nous) mais pour des Double/Float...
 * Contient des méthodes statiques de manipulation usuelles de vecteurs mais aussi de coordonées d'échecs (ex : f8 ou e2)
 */

public class vect2D {// TODO : renommer en vec2 (comme GLSL)
    public int x, y; // TODO : private

    public vect2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public vect2D clone() {
    	return new vect2D(this.x, this.y);
    }
    /**
     * Applique
     * @return new vec2( this.signum(this.x), this.signum(signe de this.y) )
     */
    public vect2D generate_signum() {
        return new vect2D(signum(this.x), signum(this.y));
    }


    public int getX() { return this.x; }
    public int getY() { return this.y; }

    /* CONSTANTES -------------------------------------------------------------- */
    public static final vect2D INVALID_VECT = new vect2D(-255, -255);

    /* METHODES UTILITAIRES STATIQUES -------------------------------------------------------------- */

    /**
     * Le code de la fonction est assez explicite. Elle existe déjà dans Maths, mais encore une fois, que pour des float
     * ou des double.
     * @param k Le nombre dont le signe doit être extrait
     * @return Retourne -1 si k est négatif, 0 si k est nul ou 1 si k est positif.
     */
    private static int signum(int k) {
        if (k > 0) return 1;
        return (k == 0) ? 0 : -1;
    }

    /**
     * Donné deux points (A, B), génère le vecteur de translation qui transforme A en B (ie. $$ \vec{AB} $$)
     * @param A Le vecteur représentant la position du point initial depuis l'origine.
     * @param B Le vecteur représentant la position du point de destination depuis l'origine.
     * @return le vecteur $$ \vec{AB} $$ soit le vecteur (B.x - A.x, B.y - A.y)
     * @deprecated Utiliser {@link vect2D#minus(vect2D)}
     */
    public static vect2D difference(vect2D A, vect2D B) {
        // TODO : Deprecated. Remplacer ces utilisations
        return new vect2D(B.x - A.x, B.y - A.y);
    }

    /**
     * Soit A = this et B = vecteur donné en paramètre. Effectue l'opération A - B et retourne le résultat en tant que
     * <b>nouveau vecteur</b> (ne modifie donc pas this), un peu comme en GLSL.
     * Ainsi, l'ordre est important ! Si on a deux points A et B, et qu'on veut le vecteur AB $$ \vec{AB} $$, la
     * formule est B - A. Exemple : <pre>vec2 relativeMove = tragetPos.minus(currentPos);</pre> et non l'inverse
     * @param B l'opérande droite de la soustraction
     * @return new vec2(this.x - B.x, this.y - B.y);
     */
    public vect2D minus(vect2D B) {
        return new vect2D(this.x - B.x, this.y - B.y);
    }
    
    public void addAndApply(vect2D vect) {
    	this.x += vect.x;
    	this.y += vect.y;
    }

    /**
     * Détermine si un point est à l'extérieur des limites d'une boite
     * Exemple : par rapport à un échiquier, le point de position (1,8) est hors du plateau de jeu, mais pas (1, 7)
     * @param boxSize la taille de la boite, représentée par un Vect2D dont la coordonée x représente sa largeur
     *                et la coordonée y sa hauteur
     * @param dotPosition Le vecteur représentant la position du point à vérifier depuis l'origine.
     * @return vrai si le point est hors des bordures.
     * @deprecated Utiliser {@link vect2D#isOutOfBounds(vect2D dotPosition)}, qui utilise le vec2 en tant qu'objet
     * plutôt que structure
     */
    public static boolean isOutOfBounds(vect2D boxSize, vect2D dotPosition) {
        return dotPosition.x < 0 || dotPosition.x >= boxSize.x
            || dotPosition.y < 0 || dotPosition.y >= boxSize.y;
    }

    /**
     * Détermine si un point⁽¹⁾ est à l'extérieur des limites de la boite⁽²⁾ actuelle
     * Exemple : par rapport à un échiquier 8x8, le point de position (1,8) est hors du plateau de jeu, mais pas (0, 7)
     * ⁽¹⁾ cf. commentaire du param 1
     * ⁽²⁾ représenté par un vec2 dont la coordonnée x représente sa largeur et la coordonée y sa hauteur
     * @param dotPosition Le vecteur représentant la position du point à vérifier depuis l'origine.
     * @return vrai si le point est hors des bordures.
     */
    public boolean isOutOfBounds(vect2D dotPosition) {
        return  dotPosition.x < 0 || dotPosition.x >= this.x ||
                dotPosition.y < 0 || dotPosition.y >= this.y;
    }

    /**
     * Détermine si les deux vecteurs passés en paramètres sont identiques
     * @param A Vecteur à comparer avec...
     * @param B ... ce vecteur-ci
     * @return Vrai si les coordonées de A sont identiques aux coordonées de B, faux sinon
     *
     * @deprecated Utiliser {@link vect2D#equals(vect2D v)}, qui utilise le vec2 en tant qu'objet
     * plutôt que structure
     */
    // TODO : deprecated
    public static boolean isEqual(vect2D A, vect2D B) {
        return A.x == B.x && A.y == B.y;
    }

    /**
     * Teste l'égalité entre le vecteur actuel et celui passé en paramètre.
     * @param v Vecteur à comparer avec le vecteur actuel
     * @return Vrai si les coordonées de this sont identiques aux coordonées de v, faux sinon
     */
    public boolean equals(vect2D v) {
        return this.x == v.x && this.y == v.y;
    }

    /**
     * Transforme une position d'échiquier en position vectorielle.
     * Par exemple, donné en entrée "B7", cette fonction retourne un nouveau vect2D(1, 6)
     * Cette fonction a été conçue pour un échiquier standard . Une coordonnée ne doit donc pas dépasser 2 caractères !
     * @param coord une chaine de caractère de deux lettres représentant la position d'une pièce dans l'échiquier
     * @return Le vecteur représentant la position du point généré depuis l'origine. Si l'opération échoue ou que
     *  les valeurs dépassent Chessboard.BOARD_SIZE, cette fonction retourne le vecteur vect2D.INVALID_VECT
     */
    public static vect2D createFromChessCoord(String coord) {
        coord = coord.toLowerCase(Locale.ROOT);

        try {
            int x = coord.charAt(0) - Chessboard.LOWERCASE_A; // X = 'a' -> 'z' et 'a' = 0
            int y = coord.charAt(1) - '0'; // Y = '0' -> '9' et '0' = 0
            vect2D retval = new vect2D(x, y - 1);
            if (isOutOfBounds(Chessboard.BOARD_RECT, retval)) {
                return  INVALID_VECT;
            }
            return retval;
        } catch (IndexOutOfBoundsException e) {
            return INVALID_VECT;
        }
    }
}