package Piece;

import org.junit.Test;
import static org.junit.Assert.*;
import Chessboard.vect2D;

import static Chessboard.Chessboard.BOARD_SIZE;
import static Piece.Piece.IS_WHITE;

public class PiecesMoveRules {
    /**
     * Utilitaire graphique permettant de vérifier les règles de déplacement possibles d'une pièce
     * Elle permet de tester {@link Piece#isValidMove(vect2D, vect2D)} par correspondance graphique (plus visuel dans
     * l'écriture des tests, cf exemples plus bas).
     * USAGE : À DES FINS DE TESTS UNIQUEMENT
     * @param toTest la picèce à tester
     * @param piecePosition la position de la pièce au sein du plateau
     * @return Le résultat graphique généré.
     */
    private static String testPiece(Piece toTest, vect2D piecePosition) {
        // Ne pas modifier ces constantes. Soit c'est ça, soit je devrai faire un code certes adaptable MAIS illisible
        final char ME = '☺', EMPTY_WHITE = '□', EMPTY_BLACK='▤', NOT_EMPTY='▣', SPACE = ' ', LINE_SEPARATOR='\n';

        StringBuilder buffer = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (piecePosition.x == x && piecePosition.y == y) {
                    buffer.append(ME);
                } else if ( toTest.isValidMove(piecePosition, new vect2D(x, y)) ) {
                    buffer.append(NOT_EMPTY);
                } else if (y % 2 == x % 2) {
                    buffer.append(EMPTY_BLACK);
                } else {
                    buffer.append(EMPTY_WHITE);
                }
                buffer.append(SPACE);
            }
            buffer.append(LINE_SEPARATOR);
        }
        return buffer.toString();
    }

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
        String out = testPiece(new Tower(IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }

    /**
     * Cette classe teste visuellement les mouvements en diagonale, ie. {@link Bishop#isDiagMove}
     * @see Bishop#isValidMove(vect2D, vect2D)
     */
    @Test
    public void graphical_bishopMove() {
        String expected_w =
            "□ ▤ □ ▤ □ ▤ □ ▣ \n" +
            "▣ □ ▤ □ ▤ □ ▣ □ \n" +
            "□ ▣ □ ▤ □ ▣ □ ▤ \n" +
            "▤ □ ▣ □ ▣ □ ▤ □ \n" +
            "□ ▤ □ ☺ □ ▤ □ ▤ \n" +
            "▤ □ ▣ □ ▣ □ ▤ □ \n" +
            "□ ▣ □ ▤ □ ▣ □ ▤ \n" +
            "▣ □ ▤ □ ▤ □ ▣ □ \n";
        String expected_b =
            "▣ ▤ □ ▤ □ ▤ ▣ ▤ \n" +
            "▤ ▣ ▤ □ ▤ ▣ ▤ □ \n" +
            "□ ▤ ▣ ▤ ▣ ▤ □ ▤ \n" +
            "▤ □ ▤ ☺ ▤ □ ▤ □ \n" +
            "□ ▤ ▣ ▤ ▣ ▤ □ ▤ \n" +
            "▤ ▣ ▤ □ ▤ ▣ ▤ □ \n" +
            "▣ ▤ □ ▤ □ ▤ ▣ ▤ \n" +
            "▤ □ ▤ □ ▤ □ ▤ ▣ \n";
        String out_w = testPiece(new Bishop(IS_WHITE), vect2D.createFromChessCoord("d4"));
        String out_b = testPiece(new Bishop(! IS_WHITE), vect2D.createFromChessCoord("d5"));
        assertEquals(expected_w, out_w);
        assertEquals(expected_b, out_b);
    }
    @Test
    public void graphical_knightMove() {
        String expected =
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ ▣ ▤ ▣ ▤ □ ▤ \n" +
                "▤ ▣ ▤ □ ▤ ▣ ▤ □ \n" +
                "□ ▤ □ ☺ □ ▤ □ ▤ \n" +
                "▤ ▣ ▤ □ ▤ ▣ ▤ □ \n" +
                "□ ▤ ▣ ▤ ▣ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n";
        String out = testPiece(new Knight(IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }
    @Test
    public void graphical_queenMove() {
        String expected =
                "□ ▤ □ ▣ □ ▤ □ ▣ \n" +
                "▣ □ ▤ ▣ ▤ □ ▣ □ \n" +
                "□ ▣ □ ▣ □ ▣ □ ▤ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "▣ ▣ ▣ ☺ ▣ ▣ ▣ ▣ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "□ ▣ □ ▣ □ ▣ □ ▤ \n" +
                "▣ □ ▤ ▣ ▤ □ ▣ □ \n";
        String out = testPiece(new Queen(IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }
    @Test
    public void graphical_KingMove() {
        String expected =
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n" +
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "□ ▤ ▣ ☺ ▣ ▤ □ ▤ \n" +
                "▤ □ ▣ ▣ ▣ □ ▤ □ \n" +
                "□ ▤ □ ▤ □ ▤ □ ▤ \n" +
                "▤ □ ▤ □ ▤ □ ▤ □ \n";
        String out = testPiece(new King(IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }
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
        String out_w = testPiece(new Pawn(IS_WHITE), vect2D.createFromChessCoord("d4"));
        String out_b = testPiece(new Pawn(! IS_WHITE), vect2D.createFromChessCoord("d4"));
        assertEquals(expected_w, out_w);
        assertEquals(expected_b, out_b);
        // TODO : la même chose mais pour les pions noirs
    }
}