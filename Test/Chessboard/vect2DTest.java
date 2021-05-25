package Chessboard;

import org.junit.Test;

import vec2.vec2;

import static org.junit.Assert.*;
import static vec2.vec2.INVALID_VECT;

public class vect2DTest {

    @Test
    public void testTestToString() {
        assertEquals("(0, 0)", new vec2(0, 0).toString());
        assertEquals("(-1, 1)", new vec2(-1, 1).toString());
        assertEquals("(9, 999)", new vec2(9, 999).toString());
        assertEquals("(0, -1)", new vec2(-0, -1).toString());
        assertEquals("(-255, -255)", INVALID_VECT.toString());
    }

    @Test
    public void testVectorOfDots() {
        // teste .minus() et getters/setters à la fois
        vec2 dot_A = new vec2(1,2);
        vec2 dot_B = new vec2(6,6);
        vec2 vec_AB = dot_B.minus(dot_A);
        assertEquals(5, vec_AB.getX());
        assertEquals(4, vec_AB.getY());
    }

    @Test
    public void testIsOutOfBounds() {
        vec2 box = new vec2(8,8),
            orig = new vec2(0, 0),
            valid = new vec2(1, 3),
            X_neg = new vec2(-1, 3),
            X_big = new vec2(99, 1),
            Y_neg = new vec2(1, -3),
            Y_big = new vec2(1, 99),
            V_all = new vec2(-10, -10);
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
        assertTrue(new vec2(0,0).equals(new vec2(0,0))     );
        assertTrue(new vec2(-10,3).equals(new vec2(-10,3)) );
        assertFalse(new vec2(1,2).equals(new vec2(3,4)) );
        assertFalse(new vec2(9,4).equals(new vec2(-9,4)));
        // Pas la peine d'en faire des tonnes non plus, les autres tests plus bas sont là pour ça
    }

    @Test
    public void testCreateFromChessCoord() {
        // Tests conçus pour cette valeur !!
        assertEquals(8, Chessboard.BOARD_SIZE);
        // et fonctionnement dépend de ces deux fonctionnalités, ce sera plus lisible
        testTestToString();
        testIsEqual();

        vec2 a0 = Chessboard.createVectFromChessCoord("a1");
        vec2 h8 = Chessboard.createVectFromChessCoord("h8");
        vec2 e8 = Chessboard.createVectFromChessCoord("e8");
        vec2 b7 = Chessboard.createVectFromChessCoord("B7");

        assertEquals("(0, 0)", a0.toString());
        assertEquals("(7, 7)", h8.toString());
        assertEquals("(4, 7)", e8.toString());
        assertEquals("(1, 6)", b7.toString());

        vec2 letterOutOfBounds = Chessboard.createVectFromChessCoord("i7");
        vec2 numberOutOfBounds = Chessboard.createVectFromChessCoord("e9");
        vec2 youForgotTheNumber = Chessboard.createVectFromChessCoord("a");
        vec2 youForgotYourBrain = Chessboard.createVectFromChessCoord("");
        vec2 satanicCoord = Chessboard.createVectFromChessCoord("666");
        vec2 justAstring = Chessboard.createVectFromChessCoord("Un test est aussi ennuyant à lire qu'à écrire");

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
        vec2 vpz = new vec2( 5,  0).generate_signum();
        vec2 vnp = new vec2(-6,  7).generate_signum();
        vec2 vzn = new vec2( 0, -8).generate_signum();
        assert vpz.equals(new vec2( 1,  0));
        assert vnp.equals(new vec2(-1,  1));
        assert vzn.equals(new vec2( 0, -1));

        // Si les trois premiers fonctionnent, pas normal que les suivants ne fonctionnent pas
        vec2 vpp = new vec2( 1,  2).generate_signum();
        vec2 vnn = new vec2(-3, -4).generate_signum();
        vec2 vzz = new vec2( 0,  0).generate_signum();
        assert vpp.equals(new vec2( 1,  1));
        assert vnn.equals(new vec2(-1, -1));
        assert vzz.equals(new vec2( 0,  0));

        vec2 vHuge = new vec2(325, -255).generate_signum();
        assert vHuge.equals(new vec2(1, -1));
    }
}
