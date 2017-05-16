package algorithme.formule;

import arbre.Noeud;

public class MaxiRobuste implements FormuleSelection {

	public Noeud selectionner(Noeud noeud) {
		int k = 0;
		double maxi = Double.MIN_VALUE;
		int indice = 0;
		double value = 0;
		int enfants = noeud.nbEnfant();
		
		System.out.println("(ROBUSTE) sur " + enfants + " noeuds");
		while(k < enfants ) {
			value = noeud.retournerEnfant(k).nbRecompense()/noeud.retournerEnfant(k).nbSimulation();
			System.out.println("Noeud num " 
			+ k 
			+ " - position : " + (double)noeud.retournerEnfant(k).getEtat().getPosition()
			+ " - recompense : " + value
			+ " - enfantsPieges : " + noeud.retournerEnfant(k).nbEnfantPiege()
			+ "/" + noeud.retournerEnfant(k).nbEnfant()
			+ " possède " + noeud.retournerEnfant(k).nbSimulation() + " simulations");
			if (value > maxi) {
				maxi = value;
				indice = k;
			}
			k++;
		}
		return noeud.retournerEnfant(indice);
	}
	
	public String toString() {
		return "Robuste";
	}

}
