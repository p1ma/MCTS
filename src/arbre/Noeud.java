/**
 * 
 */
package arbre;

import java.util.List;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Jan 26, 2017
 */
public interface Noeud {

	public boolean estTerminal();
	public boolean resteAction();
	public boolean estRacine();
	
	public List<Action> actionsPossible(int k);
	
	public Noeud appliquer(Action action); // ajouter Enfant version simulation
	public Noeud ajouterEnfant(Action action);
	public Noeud predecesseur();
	public Noeud retournerEnfant(int indice);
	
	public Noeud setAction(Action action);
	public Action getAction();
	
	public Etat getEtat();
	
	public double resultat();
	public double retournerRecompense();
	
	public int retournerNbEnfant();
	public int retournerNbSimulation();
	
	public void visiter(double recompense);
	
	public void setStatistique(int s, double v);
	public void afficherStatistiques();
}
