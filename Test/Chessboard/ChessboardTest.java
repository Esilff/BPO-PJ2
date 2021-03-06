package Chessboard;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChessboardTest {

    @Test
    public void testLayouts() {
        Chessboard defaultChess = new Chessboard(Chessboard.INIT_LAYOUT.DEFAULT_CHESS);
        Chessboard finale_2t1r = new Chessboard(Chessboard.INIT_LAYOUT.FINALE_2R1T);
        Chessboard empty = new Chessboard(Chessboard.INIT_LAYOUT.EMPTY);

        assertEquals(
            "    a   b   c   d   e   f   g   h    \n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "8 | t | c | f | d | r | f | c | t | 8\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "7 | p | p | p | p | p | p | p | p | 7\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "6 |   |   |   |   |   |   |   |   | 6\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "5 |   |   |   |   |   |   |   |   | 5\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "4 |   |   |   |   |   |   |   |   | 4\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "3 |   |   |   |   |   |   |   |   | 3\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "2 | P | P | P | P | P | P | P | P | 2\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "1 | T | C | F | D | R | F | C | T | 1\n" +
                    "   --- --- --- --- --- --- --- ---   \n" +
                    "    a   b   c   d   e   f   g   h    \n",
                defaultChess.toString()
        );
        assertEquals(
                "    a   b   c   d   e   f   g   h    \n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "8 |   |   |   |   | r |   |   |   | 8\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "7 |   | T |   |   |   |   |   |   | 7\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "6 |   |   |   |   | R |   |   |   | 6\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "5 |   |   |   |   |   |   |   |   | 5\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "4 |   |   |   |   |   |   |   |   | 4\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "3 |   |   |   |   |   |   |   |   | 3\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "2 |   |   |   |   |   |   |   |   | 2\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "1 |   |   |   |   |   |   |   |   | 1\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "    a   b   c   d   e   f   g   h    \n",
                finale_2t1r.toString()
        );
        assertEquals(
                "    a   b   c   d   e   f   g   h    \n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "8 |   |   |   |   |   |   |   |   | 8\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "7 |   |   |   |   |   |   |   |   | 7\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "6 |   |   |   |   |   |   |   |   | 6\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "5 |   |   |   |   |   |   |   |   | 5\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "4 |   |   |   |   |   |   |   |   | 4\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "3 |   |   |   |   |   |   |   |   | 3\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "2 |   |   |   |   |   |   |   |   | 2\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "1 |   |   |   |   |   |   |   |   | 1\n" +
                        "   --- --- --- --- --- --- --- ---   \n" +
                        "    a   b   c   d   e   f   g   h    \n",
                empty.toString()
        );
    }
}
