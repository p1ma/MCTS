package config;

import algorithme.formule.FormuleSelection;
import arbre.Etat;
import arbre.Noeud;

public interface GameFactory {
	public Etat getEtat();
	public Noeud getNoeud(Etat etat);
	public void jouer(long temps, FormuleSelection strategie);
}
