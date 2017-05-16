/**
 * 
 */
package arbre;

import java.util.Date;
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
	public boolean contientEnfant(Noeud enfant);
	
	public List<Action> actionsPossible(int k);
	
	public Noeud appliquer(Action action); // ajouter Enfant version simulation
	public Noeud ajouterEnfant(Action action);
	public Noeud predecesseur();
	public Noeud retournerEnfant(int indice);
	public Noeud retournerEnfant(Noeud enfant);
	
	public Noeud setAction(Action action);
	public Action getAction();
	
	public Etat getEtat();
	
	public Date getDate();
	
	public double resultat();
	public double nbRecompense();
	public double totalRecompense();
	
	public int nbEnfant();
	public int nbSimulation();
	public int nbEnfantPiege();
	
	public void visiter();
	public void visiter(double r);
	
	public void afficherStatistiques();
}
