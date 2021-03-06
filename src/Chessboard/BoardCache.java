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

	/**
	 * Permet de récupérer le nombre de pièces de l'adversaire
	 * @param isWhite true si c'est un roi de couleur blanche, false sinon
	 * @return les coordonnées du roi sous forme d'un vec2
	 */
	public int getPieceCounterOf(boolean isWhite) {
		return (isWhite) ? pieceCounter.getX() : pieceCounter.getY();
	}

	/**
	 * Permet de récupérer la positon d'un roi de la couleur donnée
	 * @param isWhite true si c'est un roi de couleur blanche, false sinon
	 * @return les coordonnées du roi sous forme d'un vec2
	 */
	public vec2 getKingPosOfColor(boolean isWhite) {
		return (isWhite) ? kingPos_white : kingPos_black;
	}

	/**
	 * Unused
	 * @return l'affichage
	 */
	public String toString() {
		return "~ joueur blanc : "+ pieceCounter.getX() +" pièces, roi en 1+"+ kingPos_white.toString() + ENDL +
				"~ joueur noir : "+ pieceCounter.getY() +" pièces, roi en 1+"+ kingPos_black.toString();
	}
	
	/**
	 * Mets à jour le cache. Visibilité paquetage car seul le plateau de jeu peut mettre à jour le cache.
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
	
	// Par encapsulation, seul le chessboard peut modifier les valeurs. C'est sympa car pas besoin de .clone au getter
	private void addToPieceCounterOf(int value, boolean isWhite) {
		if (isWhite) {
			pieceCounter.addAndApply(value, 0);
		} else {
			pieceCounter.addAndApply(0, value);
		}
	}
}
