package Chessboard;

import static Appli.Application.ENDL;

import Game.Ipiece;
import vec2.vec2;

/**
 * On a choisi que c'est le plateau qui stocke les pièces, et les pièces qui ne connaissent pas leur propre position
 * Mais de là à chercher le position du roi à chaque tour pour des vérifs de pat/échec/mat, je préfère largement
 * stocker quelque chose de redondant plutôt que de faire tourner un algo de recherche tout le temps.
 * (n'est-ce pas le principe d'un cache ?)
 */
public class BoardCache {
	private vec2 pieceCounter = new vec2();
	private vec2 kingPos_black = new vec2();
	private vec2 kingPos_white = new vec2();
	
	// pas très conventionnel comme getter, mais c'est plus pratique à utiliser.
	public int getPieceCounterOf(boolean isWhite) {
		return (isWhite) ? pieceCounter.getX() : pieceCounter.getY();
	}
	public vec2 getKingPos_black() {
		return kingPos_black;
	}
	public vec2 getKingPos_white() {
		return kingPos_white;
	}
	
	public String toString() {
		return "~ joueur blanc : "+ pieceCounter.getX() +" pièces, roi en 1+"+ kingPos_white.toString() + ENDL +
				"~ joueur noir : "+ pieceCounter.getY() +" pièces, roi en 1+"+ kingPos_black.toString();
	}
	
	/**
	 * Mets à jour le cache. Visibilité package.
	 * @param old la pièce se trouvant dans la case actuelle
	 * @param target la pièce allant remplacer celle qui se trouve dans la case actuelle
	 * @param line la ligne où se trouve la case concernée
	 * @param column la colonne où se trouve la case concernée
	 */
	protected void updateCache(Ipiece old, Ipiece target, int line, int column) {
		boolean oldEmpty = (old == null) || old.isEmpty();
		boolean targetEmpty = target.isEmpty();
		if (oldEmpty && targetEmpty) {
			return; // vide sur vide : aucune action
		}
		if (!oldEmpty) {
			// on ajoute quelque chose par dessus une pièce existante: l'ancienne disparait (-1)
			addToPieceCounterOf(-1, old.isWhite());
		}
		if (!targetEmpty) {
			// on ajoute une pièce : soit elle a été prise (-1), soit on vient de l'ajouter (0). On lui fait +1.
			addToPieceCounterOf(1, target.isWhite());
			updateKingPos(target, line, column);
		}
	}
	
	private void updateKingPos(Ipiece target, int line, int column) {
		if (! target.isKing()) {
			return;
		}
		if (target.isWhite()) {
			this.kingPos_white.set(column, line);
		} else {
			this.kingPos_black.set(column, line);
		}
	}
	
	
	// Seul le chessboard modifie les valeurs. C'est sympa car pas besoin de .clone au getter
	private void addToPieceCounterOf(int value, boolean isWhite) {
		if (isWhite) {
			pieceCounter.addAndApply(value, 0);
		} else {
			pieceCounter.addAndApply(0, value);
		}
	}
}
