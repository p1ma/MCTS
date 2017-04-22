package arbre;

import java.util.List;

public interface Etat {
	
	// Critéres de fin de partie
	public enum FinDePartie {NON, MATCHNUL, ORDI_GAGNE, HUMAIN_GAGNE};
	
	public void afficherJeu();

	public List<Action> coups_possibles(int k);
	public FinDePartie testFin();
	
	public boolean jouerAction(Action action);
	
	public int getPas();
	public double getScore();
	public Object getPosition();
	
	public Object[][] getPlateau();
	
	public double resultat();
	
}
