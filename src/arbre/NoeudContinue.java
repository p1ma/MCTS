/**
 * 
 */
package arbre;

import java.util.LinkedList;
import java.util.List;

import arbre.Etat.FinDePartie;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 22, 2017
 */
public abstract class NoeudContinue implements Noeud{

	protected List<Noeud>  enfants;
	
	protected Noeud parent = null;
	protected Action action = null;

	protected Etat etat;

	protected int simulations = 0;
	protected double victoires = 0.0;
	
	public NoeudContinue() {
		enfants = new LinkedList<Noeud>();
	}
	
	public NoeudContinue(Etat e) {
		etat = e;
		enfants = new LinkedList<Noeud>();
	}

	public NoeudContinue(Noeud p, Etat e, Action a) {
		parent = p;
		action = a;
		etat = e;
		enfants = new LinkedList<Noeud>();
	}
	
	@Override
	public boolean estTerminal() {
		return this.etat.testFin() != FinDePartie.NON;
	}
	
	@Override
	public boolean estRacine() {
		return this.parent == null;
	}
	
	@Override
	public Noeud predecesseur() {
		return this.parent;
	}
	
	@Override
	public Noeud retournerEnfant(int indice) {
		return enfants.get(indice);
	}
	
	@Override
	public double retournerRecompense() {
		return victoires;
	}
	
	@Override
	public int retournerNbSimulation() {
		return this.simulations;
	}
	
	@Override
	public int retournerNbEnfant() {
		return enfants.size();
	}
	
	@Override
	public Action getAction() {
		return this.action;
	}

	@Override
	public Etat getEtat() {
		return this.etat;
	}
	
	@Override
	public void visiter(double recompense) {
		this.victoires += recompense;
		this.simulations++;
	}

	@Override
	public void setStatistique(int s, double v) {
		this.simulations = s;
		this.victoires = v;
	}
	
	@Override
	public void afficherStatistiques() {
		System.out.println("Statistiques : ");
		if( action != null ) {
			System.out.println("\t-Action : " + action);
		} else {
			System.out.println("\t-Action : none");
		}
		System.out.println("\t-Recompense : " + resultat());
		System.out.println("\t-Nombre de simulation(s) : " + simulations);
		System.out.println("\t-Nombre d'enfant(s) : " + enfants.size());
	}

	@Override
	public Noeud setAction(Action action) {
		this.action = action;
		return this;
	}
	
	@Override
	public double resultat() {
		return etat.resultat();
	}
}
