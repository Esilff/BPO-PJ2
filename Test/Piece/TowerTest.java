package Piece;

import Chessboard.Chessboard;
import Chessboard.vect2D;
import Game.Ipiece;

import org.junit.Test;

import static Piece.Piece.IS_WHITE;
import static Piece.PieceTest.graphical_moveDefsTester;
import static Piece.PieceTest.testBadMoves;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TowerTest {
    /**
     * Cette classe teste visuellement les mouvements horizontaux ou verticaux, ie. {@link Tower#isLinearMove}
     * @see Tower#isValidMove(vect2D, vect2D)
     */
    @Test
    public void graphical_towerMove() {
        String expected =
                "□ ▤ □ ▣ □ ▤ □ ▤ \n" +
                "▤ □ ▤ ▣ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▣ □ ▤ □ ▤ \n" +
                "▤ □ ▤ ▣ ▤ □ ▤ □ \n" +
                "▣ ▣ ▣ ☺ ▣ ▣ ▣ ▣ \n" +
                "▤ □ ▤ ▣ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▣ □ ▤ □ ▤ \n" +
                "▤ □ ▤ ▣ ▤ □ ▤ □ \n";
        String out = graphical_moveDefsTester(new Tower(IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }

    // les tests préfixés par testPlay_ assument que graphical_towerMove() est fonctionnel (car écrits après)

    @Test
    public void testPlay_SortieBasique() {
        Chessboard chessboard = new Chessboard();
        String[] moves = {
                "h1h2", "h2h4", "a8a7", "a7a5", // tentative de cannibalisme et sortie de pion
                "h1h3", "a8a6", "h3b3", "a6g6", // haut gauche
                "b3h3", "g6a6", "h3h1", "a6a8" // droite bas
        };
        // TODO : (logan) déplacer une pièce vide fonctionnerait ?
        boolean[] validity = {false, true, false, true,   true, true, true, true,   true, true, true, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | p | p | p | p | p | p | p | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   |   | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 | p |   |   |   |   |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 |   |   |   |   |   |   |   | P | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   |   |   |   |   |   |   |   | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 | P | P | P | P | P | P | P |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }
    @Test
    public void testPlay_obstacles() {
        Chessboard chessboard = new Chessboard();
        String[] moves = {
                "h1h3", "h2h4", "a8a6", "a7a5", // 1: tentative de saute mouton vertical et sortie de pion
                "h1h3", "a8a6", "c2c3", "f7f6", // 2: haut et mise en place d'un obstacle
                "h3b3", "h3d3", "a6g6", "a6e6" // 3: saute mouton horizontal impossible donc on avance un peu moins
        };
        boolean[] validity = {false, true,   false, true,   true, true,   true,true,   false, true,   false, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | p | p | p | p |   | p | p | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   | t | p |   |   | 6\n" + // step 2/3
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 | p |   |   |   |   |   |   |   | 5\n" + // step 1
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 |   |   |   |   |   |   |   | P | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" + // idem
                "3 |   |   | P | T |   |   |   |   | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 | P | P |   | P | P | P | P |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C |   | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }
    @Test
    public void testPlay_Eat() {
        // A3H4 valide ?
        Chessboard chessboard = new Chessboard();
        String[] moves = {
                "h2h4", "a7a5", "h1h3", "a8a6", // sortie de tour
                "h3a3", "a6h6", // Gauche
                "a3a2", "a3a5", "h6h7", "h6h4", // cannibalisme (x) et plus cannibalisme (v)
        };
        boolean[] validity = {true, true, true, true,   true, true,    false, true, false, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | p | p | p | p | p | p | p | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   |   | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 | T |   |   |   |   |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 |   |   |   |   |   |   |   | t | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   |   |   |   |   |   |   |   | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 | P | P | P | P | P | P | P |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C |   | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testTestClone() {
        Ipiece original = new Tower(true);
        Ipiece hisClone = original.clone();
        assertNotSame(original,hisClone);
        assert hisClone instanceof Tower;
        assert ! (hisClone instanceof Pawn);
    }
}
