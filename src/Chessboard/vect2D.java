package Chessboard;

import java.util.Locale;

/**
 * Une struct représentant un couple de deux integers, à utiliser pour définir un déplacement ou la position d'un point.
 * Java dispose déjà du type Vector2D (ie. Vect2d de java != vect2D de chez nous) mais pour des Double/Float...
 * Contient des méthodes statiques de manipulation usuelles de vecteurs mais aussi de coordonées d'échecs (ex : f8 ou e2)
 */
public class vect2D {

    /* STRUCTURE -------------------------------------------------------------- */
    public int x, y; // vu qu'on l'utilise comme struct, on prends des attributs publics

    public vect2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /* CONSTANTES -------------------------------------------------------------- */
    public static final vect2D INVALID_VECT = new vect2D(-255, -255);

    /* METHODES UTILITAIRES STATIQUES -------------------------------------------------------------- */

    /**
     * Donné deux points (A, B), génère le vecteur de translation qui transforme A en B (ie. $$ \vec{AB} $$)
     * @param A Le vecteur représentant la position du point initial depuis l'origine.
     * @param B Le vecteur représentant la position du point de destination depuis l'origine.
     * @return le vecteur $$ \vec{AB} $$ soit le vecteur (B.x - A.x, B.y - A.y)
     */
    public static vect2D distanceBetween(vect2D A, vect2D B) {
        return new vect2D(B.x - A.x, B.y - A.y);
    }

    /**
     * Détermine si un point est à l'extérieur des limites d'une boite
     * Exemple : par rapport à un échiquier (8, 8), le point de position (1,9) est hors du plateau de jeu
     * @param boxSize la taille de la boite, représentée par un Vect2D dont la coordonée x représente sa largeur
     *                et la coordonée y sa hauteur
     * @param dotPosition Le vecteur représentant la position du point à vérifier depuis l'origine.
     * @return vrai si le point est hors des bordures.
     */
    public static boolean isOutOfBounds(vect2D boxSize, vect2D dotPosition) {
        return dotPosition.x < 0 || dotPosition.x > boxSize.x
            || dotPosition.y < 0 || dotPosition.y > boxSize.y;
    }

    /**
     * Détermine si les deux vecteurs passés en paramètres sont identiques
     * @param A Vecteur à comparer avec...
     * @param B ... ce vecteur-ci
     * @return Vrai si les coordonées de A sont identiques aux coordonées de B, faux sinon
     */
    public static boolean isEqual(vect2D A, vect2D B) {
        return A.x == B.x && A.y == B.y;
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
