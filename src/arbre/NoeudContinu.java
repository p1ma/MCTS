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

		//peut etre
		recompenses = (double)etat.getScore();
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
	public Noeud retournerEnfant(Noeud enfant) {
		for( NoeudContinu nd : enfants) {
			if( nd.equals(enfant)) {
				return nd;
			}
		}
		return enfant;
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
		double debut = .0;
		System.out.println("Statistiques : ");
		if( action != null ) {
			System.out.println("\t-Action : " + action);
			debut = (double)action.getRepresentation();
		} else {
			System.out.println("\t-Action : aucune");
		}

		if( parent != null ) {
			System.out.println("\t-Racine : non");
		} else {
			System.out.println("\t-Racine : oui");
		}
		if (FinDePartie.NON == etat.testFin()) {
			System.out.println("\t-Noeud : non terminal");
		} else {
			System.out.println("\t-Noeud : terminal");
		}
		double pos = (double)etat.getPosition();
		System.out.println("\t-Recompense : " + recompenses);
		System.out.println("\t-Resultat : " + resultat());
		System.out.println("\t-Position avant : " + (pos - debut));
		System.out.println("\t-Position : " + pos);
		System.out.println("\t-Nombre de simulation(s) : " + simulations);
		System.out.println("\t-Nombre d'enfant(s) : " + enfants.size());
		int k = 1;
		for(NoeudContinu enfant : enfants) {
			System.out.println("\t\t-Position enfant " + k + " : " + (double)enfant.getEtat().getPosition());
			System.out.println("\t\t-Action enfant " + k + " : " + enfant.getAction());
			System.out.println("\t\t-Resultat enfant " + k + ": " + enfant.resultat());
			System.out.println("\t\t-Recompense enfant " + k + ": " + enfant.nbRecompense());
			System.out.println("\t\t-Simulations enfant " + k + ": " + enfant.nbSimulation()+ "\n");
			k++;
		}
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
			return 0.0;
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
		double action = (double)enfant.getAction().getRepresentation();
		return bruits.contains( action );
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
