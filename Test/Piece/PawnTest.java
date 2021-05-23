package Piece;

import static org.junit.Assert.*;
import org.junit.Test;

import Chessboard.Chessboard;
import Chessboard.vec2;
import Game.Ipiece;

import static Piece.Piece.IS_WHITE;
import static Piece.PieceTest.testBadMoves; /** @see PawnTest#testPlay_simpleStep */
import static Piece.PieceTest.graphical_moveDefsTester; /** @see PawnTest#graphical_PawnMove() */

/**
 * @author LoganTann
 */
public class PawnTest {

    /**
     * Visualisation de la définition des mouvements possibles du pion. Rappel : ne tient pas compte de l'état du
     * plateau, ne sert qu'à vérifier la validité d'un schéma de mouvement. (ie. test de {@link Piece#isValidMove} et
     * non de {@link Piece#play}
     * @see PieceTest#graphical_moveDefsTester 
     */
    @Test
    public void graphical_PawnMove() {
        String expected_w =
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▣ □ ▤ □ ▤ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "□ ▤ □ ☺ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n";
        String expected_b =
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ □ ☺ □ ▤ □ ▤ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "□ ▤ □ ▣ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n";
        String out_w = graphical_moveDefsTester(new Pawn(IS_WHITE), vec2.createFromChessCoord("d4"));
        String out_b = graphical_moveDefsTester(new Pawn(! IS_WHITE), vec2.createFromChessCoord("d4"));
        assertEquals(expected_w, out_w);
        assertEquals(expected_b, out_b);
    }

    /**
     * Par contre, les tests préfixés par testPlay_ effectuent bien une vérification qui tenant compte de l'état du
     * plateau et des pièces environnantes (donc, test de {@link Piece#play})
     * Ce test vérifie le pas simple d'un pion
     */
    @Test
    public void testPlay_simpleStep() {
        
        Chessboard chessboard = new Chessboard();
        String[] moves = {
                "a2a3", "a7a6", // Allez petit pas devant (tin tin)
                "a3a2", "a3a4", "a6a7", "a6a5", // petit pas derrière (tin tin tin)... Allez petit pas devant (tin tin)
                "a4a5", "h2h3", "a5a4", "h7h6"  // Toléka toléka -- coups pas valides car passage bloqué + idle moves
        };
        boolean[] validity = {true, true,    false, true, false, true,    false, true, false, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | p | p | p | p | p | p |   | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   | p | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 | p |   |   |   |   |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 | P |   |   |   |   |   |   |   | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   |   |   |   |   |   |   | P | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 |   | P | P | P | P | P | P |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }

    /**
     * Ce test vérifie les doubles pas et les coups interdits qui y sont liés
     */
    @Test
    public void testPlay_doubleStep() {
        Chessboard chessboard = new Chessboard();
        String[] moves = {
            // double step
                "a2a4", "b7b5",
            // Prévention "double step seulement si jamais joué"
                "a4a6", "a4a5", "b5b3", "b5b4",
            // prévention "saute mouton"
                "a5a6", "b4b3",
                "b2b4", "h2h4", "a7a5", "g7g5",
            // Prévention target est occupée
                "h4h5", "g5g4",
                "g2g4", "g2g3", "h7h5", "h7h6"
        };
        boolean[] validity = {
                true, true, // Double step habituel (v)
                false, true, false, true, // [re double step (x) + préparation de la suite (v)] * 2
                // barrage de route en finition (+1) * 2 & [Saute mouton (x) + préparation de la suite (v)] * 2
                true,true,   false, true, false, true,
                // barrage de target en finition (+1) * 2 & [target occupé (x) + on avance simple (v)] * 2
                true,true,   false, true, false, true
        };
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 | p |   | p | p | p | p |   |   | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 | P |   |   |   |   |   |   | p | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 |   |   |   |   |   |   |   | P | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 |   |   |   |   |   |   | p |   | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   | p |   |   |   |   | P |   | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 |   | P | P | P | P | P |   |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }

    /**
     * Ce test vérifie les pas en diagonales (manger) et les coups interdits qui y sont liés
     */
    @Test
    public void testPlay_Eat() {
        Chessboard chessboard = new Chessboard();
        String[] moves = {
                "a2b3", "a2a3", "a7b6", "a7a6", // manger du vide
                "b2b4", "b7b5", // placement pour tenter de ...
                "a3b4", "a3a4", "a6b5", "a6a5", // ... les rendre cannibale
                "a4b5", "a5b4" // là ils peuvent manger
        };
        boolean[] validity = {false, true, false, true,   true, true,   false, true, false, true,    true, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | d | r | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   |   | p | p | p | p | p | p | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   |   | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 |   | P |   |   |   |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 |   | p |   |   |   |   |   |   | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   |   |   |   |   |   |   |   | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 |   |   | P | P | P | P | P | P | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | D | R | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }

    /**
     * Ce test vérifie la méthode permettant de cloner les pièces
     */
    @SuppressWarnings("ConstantConditions") // désactive un faux warning de IntelliJ idea 
    @Test
    public void testTestClone() {
        Ipiece original = new Pawn(true);
        Ipiece hisClone = original.clone();
        assertNotSame(original,hisClone);
        assert hisClone instanceof Pawn; // un pawn
        assert ! (hisClone instanceof Tower); // mais pas une tour ou quoi que ce soit d'autre
    }
}
