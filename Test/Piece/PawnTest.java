package Piece;

import Chessboard.Chessboard;
import org.junit.Test;
import static org.junit.Assert.*;

public class PawnTest {
    public void testBadMoves(Chessboard chessboard, String[] moves, boolean[] isMoveValid) {
        assert moves.length == isMoveValid.length : "Erreur d'écriture de test, String.length != boolean.length";
        for (int i = 0; i < moves.length; i++) {
            String s = moves[i];
            boolean didThrow = false;
            try {
                chessboard.play(s.substring(0, 2), s.substring(2, 4));
            } catch (BadMoveException e) {
                didThrow = true;
            }
            if (isMoveValid[i] == didThrow){ // (V et V) ou (F et F)
                fail( " Move " + s
                    + " is " + (isMoveValid[i] ? "valid": "not valid")
                    + " but play() " +  (didThrow ? "thrown BadMoveException": "didn't throw anything")
                );
            }
        }
    }

    @Test
    public void testPlay_simpleStep() {
        Chessboard chessboard = new Chessboard();
        String moves[] = {
            "a2a3", "a7a6", // Allez petit pas devant (tin tin)
            "a3a2", "a3a4", "a6a7", "a6a5", // petit pas derrière (tin tin tin)... Allez petit pas devant (tin tin)
            "a4a5", "h2h3", "a5a4", "h2h3"  // Toléka toléka -- coups pas valides car passage bloqué + idle moves
        };
        boolean validity[] = {true, true,    false, true, false, true,    false, true, false, true};
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | r | d | f | c | t | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | p | p | p | p | p | p | p | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   |   | 6\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "5 | p |   |   |   |   |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "4 | P |   |   |   |   |   |   |   | 4\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "3 |   |   |   |   |   |   |   | P | 3\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "2 |   | P | P | P | P | P | P |   | 2\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "1 | T | C | F | R | D | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }
    @Test
    public void testPlay_doubleStep() {
        Chessboard chessboard = new Chessboard();
        String moves[] = {
            "a2a4", "b7b5", // normal (+2)
            "a4a6", "a4a5", // recommence + barrage de route (+1)
            "b5b3", "b5b4", // idem
            "a5a6", "b4b3", // barrage de route en finition (+1)
            "b2b4", "h2h3", // route barrée... + (idle move)
            "a7a5", "h7h5"  //idem
        };
        boolean validity[] = {true, true,   false, true,   false, true,   true,true,   false, true,   false, true};
        String expectedOut = "TODO a4a6 not valid"; // TODO : a4a6 not valid

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }
    @Test
    public void testPlay_Eat() {
        Chessboard chessboard = new Chessboard();
        String moves[] = {
            "a2b3", "a2a3", "a7b6", "a7a6", // manger du vide
            "b2b4", "b7b5", // placement pour tenter de ...
            "a3b4", "a3a4", "a6b5", "a6a5", // ... les rendre cannibale
            "a4b5", "a5b4" // là ils peuvent manger
        };
        boolean validity[] = {false, true, false, true,   true, true,   false, true, false, true,    true, true};
        String expectedOut = "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 | t | c | f | r | d | f | c | t | 8\n" +
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
                "1 | T | C | F | R | D | F | C | T | 1\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "    a   b   c   d   e   f   g   h    \n";

        testBadMoves(chessboard, moves, validity);
        assertEquals(expectedOut, chessboard.toString());
    }

    public void testTestClone() {
        Piece original = new Pawn(true);
        Piece hisClone = original.clone();
        assertEquals(original,hisClone); // Contenu identique
        assertNotSame(original,hisClone); // ... sauf la référence
        assertTrue(hisClone instanceof Pawn); // et doit être un Pawn, pas un pion non spécifié
    }

    /**
     * @see PiecesMoveRules#graphical_PawnMove()
     */
    public void testIsValidMove() {
        // test implémenté ici : PiecesMoveRules.graphical_PawnMove()
    }
}