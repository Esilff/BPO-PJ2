package Chessboard;

import junit.framework.TestCase;

import static Chessboard.vect2D.INVALID_VECT;

public class vect2DTest extends TestCase {

    public void testTestToString() {
        assertEquals("(0, 0)", new vect2D(0, 0).toString());
        assertEquals("(-1, 1)", new vect2D(-1, 1).toString());
        assertEquals("(9, 999)", new vect2D(9, 999).toString());
        assertEquals("(0, -1)", new vect2D(-0, -1).toString());
        assertEquals("(-255, -255)", INVALID_VECT.toString());
    }

    public void testVectorOfDots() {
        vect2D A = new vect2D(1,2);
        vect2D B = new vect2D(6,7);
        vect2D vec_AB = vect2D.translationFrom_argA_to_argB(A, B);
        assertEquals(5, vec_AB.x);
        assertEquals(5, vec_AB.x);
    }

    public void testIsOutOfBounds() {
        vect2D box = new vect2D(8,8),
            orig = new vect2D(0, 0),
            valid = new vect2D(1, 3),
            X_neg = new vect2D(-1, 3),
            X_big = new vect2D(99, 1),
            Y_neg = new vect2D(1, -3),
            Y_big = new vect2D(1, 99),
            V_all = new vect2D(-10, -10);
        assertTrue( vect2D.isOutOfBounds(box, box)  );
        assertFalse(vect2D.isOutOfBounds(box, orig) );
        assertFalse(vect2D.isOutOfBounds(box, valid));
        assertTrue( vect2D.isOutOfBounds(box, X_neg));
        assertTrue( vect2D.isOutOfBounds(box, X_big));
        assertTrue( vect2D.isOutOfBounds(box, Y_neg));
        assertTrue( vect2D.isOutOfBounds(box, Y_big));
        assertTrue( vect2D.isOutOfBounds(box, V_all));
    }

    public void testIsEqual() {
        assertTrue(vect2D.isEqual(new vect2D(0,0), new vect2D(0,0)));
        assertTrue(vect2D.isEqual(new vect2D(-10,3), new vect2D(-10,3)));
        assertFalse(vect2D.isEqual(new vect2D(1,2), new vect2D(3,4)));
        assertFalse(vect2D.isEqual(new vect2D(9,4), new vect2D(-9,4)));
    }

    public void testCreateFromChessCoord() {
        // Tests conçus pour cette valeur !!
        assertEquals(8, Chessboard.BOARD_SIZE);
        // et fonctionnement dépend de ça, ce sera plus facile de tester les valeurs
        testTestToString();

        vect2D a0 = vect2D.createFromChessCoord("a1");
        vect2D h8 = vect2D.createFromChessCoord("h8");
        vect2D e8 = vect2D.createFromChessCoord("e8");
        vect2D b7 = vect2D.createFromChessCoord("B7");

        assertEquals("(0, 0)", a0.toString());
        assertEquals("(7, 7)", h8.toString());
        assertEquals("(4, 7)", e8.toString());
        assertEquals("(1, 6)", b7.toString());

        vect2D letterOutOfBounds = vect2D.createFromChessCoord("i7");
        vect2D numberOutOfBounds = vect2D.createFromChessCoord("e9");
        vect2D youForgotTheNumber = vect2D.createFromChessCoord("a");
        vect2D youForgotYourBrain = vect2D.createFromChessCoord("");
        vect2D satanicCoord = vect2D.createFromChessCoord("666");
        vect2D justAstring = vect2D.createFromChessCoord("Un test est aussi ennuyant à lire qu'à écrire");

        // TODO : utiliser assert vect2D.isEqual()
        assertEquals(INVALID_VECT.toString(), letterOutOfBounds.toString());
        assertEquals(INVALID_VECT.toString(), numberOutOfBounds.toString());
        assertEquals(INVALID_VECT.toString(), youForgotTheNumber.toString());
        assertEquals(INVALID_VECT.toString(), youForgotYourBrain.toString());
        assertEquals(INVALID_VECT.toString(), satanicCoord.toString());
        assertEquals(INVALID_VECT.toString(), justAstring.toString());
    }
}