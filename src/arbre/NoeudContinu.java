/**
 * 
 */
package arbre;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import arbre.Etat.FinDePartie;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 22, 2017
 */
public abstract class NoeudContinu implements Noeud {

	/**
	 * Liste des Noeuds enfant
	 */
	protected List<NoeudContinu> enfants;

	/**
	 * Noeud parent,
	 * si null alors le Noeud this est racine
	 */
	protected Noeud parent = null;
	
	/**
	 * Action choisit
	 */
	protected Action action = null;

	/**
	 * Etat du Noeud this
	 */
	protected Etat etat;

	/**
	 * Nombre de simulations
	 */
	protected int simulations = 0;
	
	/**
	 * Récompense du Noeud
	 */
	protected double recompenses = 0.0;
	
	/**
	 * Bruit
	 */
	public Set<Double> bruits;
	
	/**
	 * Constructeur de NoeudContinu
	 */
	public NoeudContinu() {
		enfants = new LinkedList<NoeudContinu>();
		bruits = new HashSet<Double>();
	}

	/**
	 * Constructeur de NoeudContinu
	 */
	public NoeudContinu(Etat e) {
		parent = null;
		action = null;
		etat = e;
		etat.setScore(0);
		enfants = new LinkedList<NoeudContinu>();
		bruits = new HashSet<Double>();
	}

	/**
	 * Constructeur de NoeudContinu
	 */
	public NoeudContinu(Noeud p, Etat e, Action a) {
		parent = p;
		action = a;
		etat = e;
		enfants = new LinkedList<NoeudContinu>();
		bruits = new HashSet<Double>();
		/*etat.setScore(parent.resultat());
		etat.jouerAction(a);*/
	}
	
	/*
	 * FONCTIONS ABSTRAITES
	 */
	public abstract NoeudContinu bruite();
	
	public abstract NoeudContinu ajouterEnfantBruite(Action action);
	
	@Override
	public abstract boolean resteAction();

	@Override
	public abstract List<Action> actionsPossible(int k);

	@Override
	public abstract NoeudContinu appliquer(Action action);

	@Override
	public abstract NoeudContinu ajouterEnfant(Action action);
	
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
	public NoeudContinu retournerEnfant(int indice) {
		if (enfants != null && indice < enfants.size()) {
			return enfants.get(indice);
		} else {
			return null;
		}
	}

	@Override
	public double nbRecompense() {
		return recompenses;
	}

	@Override
	public int nbSimulation() {
		return simulations;
	}

	@Override
	public int nbEnfant() {
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
	public void visiter() {
		this.simulations++;
	}

	@Override
	public void visiter(double r) {
		this.recompenses += r;
	}

	@Override
	public void afficherStatistiques() {
		System.out.println("Statistiques : ");
		if( action != null ) {
			System.out.println("\t-Action : " + action);
		} else {
			System.out.println("\t-Action : aucune");
		}

		if( parent != null ) {
			System.out.println("\t-Racine : non");
		} else {
			System.out.println("\t-Racine : oui");
		}
		System.out.println("\t-Recompense : " + resultat());
		System.out.println("\t-Position : " + (double)etat.getPosition());
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
	
	@Override
	public double totalRecompense() {
		if ( estRacine() ) {
			return resultat();
		} else {
			return resultat() + parent.totalRecompense();
		}
	}

	public NoeudContinu recuperer(Action action) {
		for(NoeudContinu noeud : enfants) {
			Action a = noeud.getAction();
			if (a.equals(action)) {
				return noeud;
			}
		}
		return appliquer( action );
	}

	@Override
	public boolean contientEnfant(Noeud enfant) {
		for(Noeud noeud : enfants) {
			if (noeud.equals(enfant)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		Noeud verif = (NoeudContinu) obj;
		boolean simu = verif.nbSimulation() == simulations;
		if (!simu ) {
			return false;
		}
		boolean en = verif.nbEnfant() == enfants.size();
		if (!en) {
			return false;
		}

		Action act = verif.getAction();
		if (act == null) {
			if (action != null) {
				return false;
			}
		} else {
			if (action == null) {
				return false;
			} else {
				if ( !action.equals(act) ) {
					return false;
				}
			}
		}

		if ( !etat.equals(verif.getEtat()) ) {
			return false;
		}
		return true;
	}
}
