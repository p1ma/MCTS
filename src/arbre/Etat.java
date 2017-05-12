package arbre;

import java.util.List;

public interface Etat {
	
	// Critéres de fin de partie
	public enum FinDePartie {NON, MATCHNUL, ORDI_GAGNE, HUMAIN_GAGNE};
	
	public void afficherJeu();
	public void setScore(double score);

	public List<Action> coups_possibles(int k);
	public FinDePartie testFin();
	
	public boolean initial();
	public boolean jouerAction(Action action);
	public void jouerActionBruite(Action action);
	
	public int getPas();
	
	public Object getPosition();
	public Object getScore();
	
	public Object[][] getPlateau();
	
	public double resultat();
}
