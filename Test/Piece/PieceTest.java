package Piece;

import Chessboard.Chessboard;
import org.junit.Test;

import static Chessboard.Chessboard.BOARD_SIZE;
import static Piece.Piece.IS_WHITE;
import static org.junit.Assert.*;

import Chessboard.vec2;
import Game.BadMoveException;
import Game.Game;

public class PieceTest {

    // MÉTHODES STATIQUES UTILITAIRES ----------------------------------------------------------------------------------
    // (utilisé à l'extérieur de cette classe)
	
	
	// Les tests sont bien plus lisibles sur IntelliJ Idea, ou à défaut, sur notre GitHub.
	// Mais pas sur éclipse
	

    /**
     * Effectue une suite de mouvements (argument 2) et vérifie si celui-ci devrait (ou pas) throw une BadMoveException
     * Si il n'y a pas correspondance, alors exécute fail(), qui va faire échouer le test appellant cette fonction.
     * @param chessboard Le tableau de jeu contenant les pièces
     * @param moves un tableau de String contenant une suite de mouvements à effectuer (ex: [a2a3, b2b4, c2c3])
     * @param isMoveValid un tableau booléen de même taille que l'argument précédent. true si le mouvement associé
     *                    est valide (ne devrait pas throw), false sinon.
     */
    protected static void testBadMoves(Chessboard chessboard, String[] moves, boolean[] isMoveValid) {
    	Game game = new Game(chessboard);
        assert moves.length == isMoveValid.length : "Erreur d'écriture de test, String.length != boolean.length";
        for (int i = 0; i < moves.length; i++) {
            String s = moves[i];
            String throwMsg = "";
            boolean didThrow = false;
            try {
                game.play(s.substring(0, 2), s.substring(2, 4));
            } catch (BadMoveException e) {
                didThrow = true;
                throwMsg = e.toString();
            }
            if (isMoveValid[i] == didThrow){ // (V et V) ou (F et F)
                fail( " Move " + s
                    + " is " + (isMoveValid[i] ? "valid": "not valid")
                    + " but play() " +  (didThrow ? "thrown "+throwMsg+"": "didn't throw anything")
                    + "\nChessboard state : \n" + chessboard.toString()
                );
            }
        }
    }

    /**
     * Utilitaire graphique permettant de vérifier les règles de déplacement possibles d'une pièce
     * Elle permet de tester {@link Piece#isValidMove(vec2, vec2)} par correspondance graphique (plus visuel dans
     * l'écriture des tests, cf exemples plus bas). N'oublions pas que isValidMove ne teste que la validité du schéma de
     * mouvement, et ne tiens PAS compte de l'état actuel du plateau (excepté les sorties de plateau)
     * @param toTest la pièce à tester
     * @param piecePosition la position de la pièce au sein du plateau
     * @return Le résultat graphique généré.
     */
    protected static String graphical_moveDefsTester(Piece toTest, vec2 piecePosition) {
        // Ne pas modifier ces constantes. Soit c'est ça, soit je devrai faire un code certes adaptable MAIS illisible
        final char ME = '☺', EMPTY_WHITE = '□', EMPTY_BLACK='▤', NOT_EMPTY='▣', SPACE = ' ', LINE_SEPARATOR='\n';

        StringBuilder buffer = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (piecePosition.getX() == x && piecePosition.getY() == y) {
                    buffer.append(ME);
                } else if ( toTest.isValidMove(piecePosition, new vec2(x, y)) ) {
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


    // TEST UNITAIRE DE LA CLASSE PIECE --------------------------------------------------------------------------------

    /**
     * Cette classe teste visuellement les mouvements en diagonale, ie. {@link Bishop#isDiagMove}
     * @see Bishop#isValidMove(vec2, vec2)
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
        String out_w = graphical_moveDefsTester(new Bishop(IS_WHITE), vec2.createFromChessCoord("d4"));
        String out_b = graphical_moveDefsTester(new Bishop(! IS_WHITE), vec2.createFromChessCoord("d5"));
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
        String out = graphical_moveDefsTester(new Knight(IS_WHITE), vec2.createFromChessCoord("d4"));
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
        String out = graphical_moveDefsTester(new Queen(IS_WHITE), vec2.createFromChessCoord("d4"));
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
        String out = graphical_moveDefsTester(new King(IS_WHITE), vec2.createFromChessCoord("d4"));
        assertEquals(expected, out);
    }
}
