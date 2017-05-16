package algorithme.formule;

import arbre.Noeud;

public class MaxiRobuste implements FormuleSelection {

	public Noeud selectionner(Noeud noeud) {
		int k = 0;
		double maxi = Double.MIN_VALUE;
		int indice = 0;
		double value = 0;
		int enfants = noeud.nbEnfant();
		System.out.println("MAXI sur " + enfants + " noeuds");
		while(k < enfants ) {
			value = noeud.retournerEnfant(k).nbRecompense() / noeud.retournerEnfant(k).nbSimulation();

			if (value > maxi) {
				maxi = value;
				indice = k;
			}
			k++;
		}
		return noeud.retournerEnfant(indice);
	}

	public String toString() {
		return "Maxi-Robuste";
	}
}
