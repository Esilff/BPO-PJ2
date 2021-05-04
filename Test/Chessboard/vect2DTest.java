package Chessboard;

import org.junit.Test;
import static org.junit.Assert.*;

import static Chessboard.vect2D.INVALID_VECT;

public class vect2DTest {

    @Test
    public void testTestToString() {
        assertEquals("(0, 0)", new vect2D(0, 0).toString());
        assertEquals("(-1, 1)", new vect2D(-1, 1).toString());
        assertEquals("(9, 999)", new vect2D(9, 999).toString());
        assertEquals("(0, -1)", new vect2D(-0, -1).toString());
        assertEquals("(-255, -255)", INVALID_VECT.toString());
    }

    @Test
    public void testVectorOfDots() {
        // teste .minus() et getters/setters à la fois
        vect2D dot_A = new vect2D(1,2);
        vect2D dot_B = new vect2D(6,6);
        vect2D vec_AB = dot_B.minus(dot_A);
        assertEquals(5, vec_AB.getX());
        assertEquals(4, vec_AB.getY());
    }

    @Test
    public void testIsOutOfBounds() {
        vect2D box = new vect2D(8,8),
            orig = new vect2D(0, 0),
            valid = new vect2D(1, 3),
            X_neg = new vect2D(-1, 3),
            X_big = new vect2D(99, 1),
            Y_neg = new vect2D(1, -3),
            Y_big = new vect2D(1, 99),
            V_all = new vect2D(-10, -10);
        assertTrue( box.isOutOfBounds(box)  );
        assertFalse(box.isOutOfBounds(orig) );
        assertFalse(box.isOutOfBounds(valid));
        assertTrue( box.isOutOfBounds(X_neg));
        assertTrue( box.isOutOfBounds(X_big));
        assertTrue( box.isOutOfBounds(Y_neg));
        assertTrue( box.isOutOfBounds(Y_big));
        assertTrue( box.isOutOfBounds(V_all));
    }

    @Test
    public void testIsEqual() {
        assertTrue(new vect2D(0,0).equals(new vect2D(0,0))     );
        assertTrue(new vect2D(-10,3).equals(new vect2D(-10,3)) );
        assertFalse(new vect2D(1,2).equals(new vect2D(3,4)) );
        assertFalse(new vect2D(9,4).equals(new vect2D(-9,4)));
        // Pas la peine d'en faire des tonnes non plus, les autres tests plus bas sont là pour ça
    }

    @Test
    public void testCreateFromChessCoord() {
        // Tests conçus pour cette valeur !!
        assertEquals(8, Chessboard.BOARD_SIZE);
        // et fonctionnement dépend de ces deux fonctionnalités, ce sera plus lisible
        testTestToString();
        testIsEqual();

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

        assert letterOutOfBounds.equals(INVALID_VECT);
        assert numberOutOfBounds.equals(INVALID_VECT);
        assert youForgotTheNumber.equals(INVALID_VECT);
        assert youForgotYourBrain.equals(INVALID_VECT);
        assert satanicCoord.equals(INVALID_VECT);
        assert justAstring.equals(INVALID_VECT);
    }

    @Test
    public void testGenerate_signum() {
        // p = positif, n = negatif, z = nul
        vect2D vpz = new vect2D( 5,  0).generate_signum();
        vect2D vnp = new vect2D(-6,  7).generate_signum();
        vect2D vzn = new vect2D( 0, -8).generate_signum();
        assert vpz.equals(new vect2D( 1,  0));
        assert vnp.equals(new vect2D(-1,  1));
        assert vzn.equals(new vect2D( 0, -1));

        // Si les trois premiers fonctionnent, pas normal que les suivants ne fonctionnent pas
        vect2D vpp = new vect2D( 1,  2).generate_signum();
        vect2D vnn = new vect2D(-3, -4).generate_signum();
        vect2D vzz = new vect2D( 0,  0).generate_signum();
        assert vpp.equals(new vect2D( 1,  1));
        assert vnn.equals(new vect2D(-1, -1));
        assert vzz.equals(new vect2D( 0,  0));

        vect2D vHuge = new vect2D(325, -255).generate_signum();
        assert vHuge.equals(new vect2D(1, -1));
    }
}
