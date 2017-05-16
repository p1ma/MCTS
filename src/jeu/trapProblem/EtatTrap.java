/**
 * 
 */
package jeu.trapProblem;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dao.StatistiqueDAO;

import arbre.Action;
import arbre.Etat;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 16, 2017
 */
public class EtatTrap implements Etat {

	private Double[][] plateau = {{1.,70.}, {1.7,0.}, {5.,100.}};
	private double position, score;

	private int pas = 2;
	private final int min = 0, max = 1; 
	private final Random random = new Random();

	private List<Action> options = null;

	public EtatTrap(Etat etat) {
		position = (double)etat.getPosition();
		pas = etat.getPas();
		score = (double)etat.getScore();
	}

	public EtatTrap() {
		position = 0.0;	
		pas = 2;
		score = 0.0;
	}
	
	public void setScore(double score) {
		this.score += score;
	}
	
	public Object getScore() {
		return score;
	}

	@Override
	public void afficherJeu() {
		System.out.println("----------------------------------------------------");
		System.out.println("Position du joueur : " + position);
		System.out.println("Score : " + score);
		System.out.print("O \t\t");
		for (Double[] tab : this.plateau) {
			System.out.print(tab[0] + "\t\t");
		}
		System.out.println("");
		for (Double[] tab : this.plateau) {
			System.out.print("| " + tab[1] + "\t\t");
		}
		System.out.println("\n");
	}

	@Override
	public List<Action> coups_possibles(int k) {
		if (options == null) {
			options = new LinkedList<Action>();
		}
		//uniforme(k);
		gaussien(k);
		return options;
	}

	private void gaussien(int k) {
		// options is not null
		int t = options.size();
		double step = .0;
		double esperance = (max - min) / 2;
		double ecartType = .2;
		for(int i = 0 ; i < (k - t) ; i++) {
			step = (( esperance + (ecartType * random.nextGaussian()) ));
			step = step < 0 ? max - (-step) % max : step %max;
			//StatistiqueDAO.getInstance().ecrire(step);
			options.add( new ActionTrap(step) );
		}
	}
	
	private void uniforme(int k) {
		// options is not null
		int t = options.size();
		double step;
		
		for(int i = 0 ; i < (k - t) ; i++) {
			step = ((random.nextDouble() * max) + min) % max;
			//StatistiqueDAO.getInstance().ecrire(step);
			options.add( new ActionTrap(step) );
		}
	}

	@Override
	public FinDePartie testFin() {
		return pas == 0 ? FinDePartie.HUMAIN_GAGNE : FinDePartie.NON;
	}

	@Override
	public boolean jouerAction(Action action) {
		if (pas > 0) {
			position += (double) action.getRepresentation();
			pas--;
			return true;
		}
		return false;
	}
	
	@Override
	public void jouerActionBruite(Action action) {
		/*
		 * Action contient un bruit,
		 * donc on ajoute ce bruit à la position initiale
		 * sans changer le nombre de pas.
		 */
		position += (double) action.getRepresentation();
	}

	@Override
	public int getPas() {
		return pas;
	}

	@Override
	public Object getPosition() {
		return position;
	}

	@Override
	public Object[][] getPlateau() {
		return plateau;
	}

	@Override
	public boolean initial() {
		return (pas == 2) && (position == 0.00);
	}
	
	@Override
	public double resultat() {
		if ( !initial() ) {
			for (Double[] i : (Double[][])plateau) {
				if (position <= i[0]) {
					return i[1];
				}
			}
			return plateau[plateau.length - 1][1];
		} else {
			return 0.0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EtatTrap etat = (EtatTrap) obj;
		double pos = (double)etat.getPosition();

		if(position != pos) {
			return false;
		}

		double res = etat.resultat();
		double thisRes = resultat();
		if (res != thisRes) {
			return false;
		}

		if (etat.getPas() != pas) {
			return false;
		}
		return true;
	}
}