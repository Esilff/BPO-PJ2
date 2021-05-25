package Piece;

import Chessboard.Chessboard;
import Game.Game;
import Piece.BadMoveException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CheckMatDrawTest {
    // pat : e6f6 e8f8 f6g6 f8g8 g6h6 g8h8 b7g7

    private void play(Game game, String s, boolean valid) {
        String throwMsg = "";
        boolean didThrow = false;
        try {
            game.play(s.substring(0, 2), s.substring(2, 4));
        } catch (Exception e) {
            didThrow = true;
            throwMsg = e.toString();
        }

        if (valid== didThrow){ // (V et V) ou (F et F)
            fail( " Move " + s
                    + " is " + (valid ? "valid": "not valid")
                    + " but play() " +  (didThrow ? "thrown "+throwMsg+"": "didn't throw anything")
                    + "\nChessboard state : \n" + game.board_toString()
            );
        }
    }

    @Test
    public void test_DRAW() {
        Game game = new Game(new Chessboard(Chessboard.INIT_LAYOUT.FINALE_2R1T));
        for (String move: new String[]{"e6f6", "e8f8", "f6g6", "f8g8", "g6h6", "g8h8", "b7g7"}) {
            play(game, move, true);
        }
        assertEquals(Game.CHECK_STATE.DRAW, game.getCheckState());
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   |   |   |   |   |   |   | r | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   |   |   |   |   |   | T |   | 7\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "6 |   |   |   |   |   |   |   | R | 6\n" +
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
                "    a   b   c   d   e   f   g   h    \n";

        assertEquals(expectedOut, game.board_toString());
    }

    // echec mat avec tour : b7b8
    @Test
    public void test_WinnerWithTower() {
        Game game = new Game(new Chessboard(Chessboard.INIT_LAYOUT.FINALE_2R1T));
        play(game, "b7b8", true);
        assertEquals(Game.CHECK_STATE.THERE_IS_WINNER, game.getCheckState());
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   | T |   |   | r |   |   |   | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   |   |   |   |   |   |   |   | 7\n" +
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
                "    a   b   c   d   e   f   g   h    \n";

        assertEquals(expectedOut, game.board_toString());
    }
    /*

    Le code suivant a été mis en commentaire. Pourquoi ?
    17:43 : je demande à la promo si quelqu'un a réussi à implémenter un coup de type coup e6f6 (roi en danger mais protégé)
    18:00 : pas de réponses
    20:47 : j'ai fini de coder cette fichue fonction
    20:48 : je lis sur le tchat que le sacrifice du roi n'est pas un coup légal ...
            (content que ça fonctionne mais pleure intérieurement)


    // attaquer de manière safe un roi avec un roi : e6f6 e8f8 f6g7 (noir échec) f8e7 (fail) f8g7 (fail) f8e8 (ok)
    @Test
    public void test_AttackKingWithKing() {
        Game game = new Game(new Chessboard(Chessboard.INIT_LAYOUT.FINALE_2R1T));
        play(game, "e6f6", true);
        play(game, "e8f8", true);
        play(game, "f6g7", true);
        assertEquals(Game.CHECK_STATE.IN_CHECK, game.getCheckState());
        play(game, "f8e7", false);
        play(game, "f8g7", false);
        play(game, "f8e8", true);
        assertEquals(Game.CHECK_STATE.NONE, game.getCheckState());
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   |   |   |   | r |   |   |   | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | T |   |   |   |   | R |   | 7\n" +
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
                "    a   b   c   d   e   f   g   h    \n";

        assertEquals(expectedOut, game.board_toString());
    }
    // echec mat avec roi : e6e7
    @Test
    public void test_WinnerWithKing() {
        Game game = new Game(new Chessboard(Chessboard.INIT_LAYOUT.FINALE_2R1T));
        play(game, "e6e7", true);
        assertEquals(Game.CHECK_STATE.THERE_IS_WINNER, game.getCheckState());
        String expectedOut =
                "    a   b   c   d   e   f   g   h    \n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "8 |   |   |   |   | r |   |   |   | 8\n" +
                "   --- --- --- --- --- --- --- ---   \n" +
                "7 |   | T |   |   | R |   |   |   | 7\n" +
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
                "    a   b   c   d   e   f   g   h    \n";

        assertEquals(expectedOut, game.board_toString());
    }*/

}
