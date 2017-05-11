/**
 * 
 */
package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import arbre.Etat;
import arbre.Noeud;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class StatistiqueDAO {

	//  temps | simulation | position | recompenses
	public final static String SIMUS = "simulations noeud racine";
	public final static String SSIMUS = "simulations noeud enfant sélectionné";
	public final static String ENFANTS = "nombre d'enfants";
	public final static String C = "C";
	public final static String ALPHA = "alpha";
	public final static String POS = "position noeud racine";
	public final static String SPOS = "position noeud enfant sélectionné";
	public final static String RECOMP = "recompenses";
	public final static String TEMP = "temps(ms)";
	public final static String ITER = "iterations";
	public final static String VALEURS = "valeurs";
	public final static String BRUIT = "dont bruit";
	
	private static StatistiqueDAO INSTANCE = null;
	private FileWriter writer;
	private FileWriter writer2;
	private FileWriter writer3;
	private final String file = "statsPW.csv";
	private final String file2 = "statsDPW.csv";
	private final String random = "randoms.csv";
	private final String separateur = ",";
	
	/*
	 * Pour donner une idée de comment récupérer les stats dans un tableur
	 * ca marche sur mon openoffice
	 */
	private StatistiqueDAO() {
		try {
			File f = new File(file);
			if (!f.exists()) {
				writer = new FileWriter(file, false);
				writer.append(TEMP);
				writer.append(separateur);
				writer.append(ITER);
				writer.append(separateur);
				writer.append(C);
				writer.append(separateur);
				writer.append(ALPHA);
				writer.append(separateur);
				writer.append(ENFANTS);
				writer.append(separateur);
				writer.append(SIMUS);
				writer.append(separateur);
				writer.append(SSIMUS);
				writer.append(separateur);
				writer.append(POS);
				writer.append(separateur);
				writer.append(SPOS);
				writer.append(separateur);
				writer.append(RECOMP);
				writer.append(separateur);
				writer.append("\n");
				writer.flush();
			} else {
				writer = new FileWriter(file, true);
				writer.append("\n");
				writer.flush();
			}
			f = new File(random);
			if (!f.exists()) {
				writer2 = new FileWriter(random, false);
				writer2.append(VALEURS);
				writer2.append(separateur);
				writer2.append("\n");
				writer2.flush();
			} else {
				writer2 = new FileWriter(random, true);
				writer2.append("\n");
				writer2.flush();
			}
			
			f = new File(file2);
			if (!f.exists()) {
				writer3 = new FileWriter(file2, false);
				writer3.append(TEMP);
				writer3.append(separateur);
				writer3.append(ITER);
				writer3.append(separateur);
				writer3.append(C);
				writer3.append(separateur);
				writer3.append(ALPHA);
				writer3.append(separateur);
				writer3.append(ENFANTS);
				writer3.append(separateur);
				writer3.append(SIMUS);
				writer3.append(separateur);
				writer3.append(SSIMUS);
				writer3.append(separateur);
				writer3.append(POS);
				writer3.append(separateur);
				writer3.append(BRUIT);
				writer3.append(separateur);
				writer3.append(SPOS);
				writer3.append(separateur);
				writer3.append(BRUIT);
				writer3.append(separateur);
				writer3.append(RECOMP);
				writer3.append(separateur);
				writer3.append("\n");
				writer3.flush();
			} else {
				writer3 = new FileWriter(file2, true);
				writer3.append("\n");
				writer3.flush();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static StatistiqueDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StatistiqueDAO();
		}
		return INSTANCE;
	}
	
	public void ecrire(double valeur) {
		try {
			writer2.append(valeur + "");
			writer2.append(separateur);
			writer2.append("\n");
			writer2.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//  temps | simulation | position | recompenses
	public void ecrireSPW(long temps, 
			double C, 
			double a, 
			int iter, 
			Noeud noeudRacine,
			Noeud noeudEnfant) {
		try {
			writer.append(temps + "");
			writer.append(separateur);
			writer.append(iter + "");
			writer.append(separateur);
			writer.append(C + "");
			writer.append(separateur);
			writer.append(a + "");
			writer.append(separateur);
			writer.append(noeudRacine.nbEnfant() + "");
			writer.append(separateur);
			writer.append(noeudRacine.nbSimulation() + "");
			writer.append(separateur);
			writer.append(noeudEnfant.nbSimulation() + "");
			writer.append(separateur);
			Etat e = noeudRacine.getEtat();
			writer.append((double)e.getPosition() + "");
			writer.append(separateur);
			e = noeudEnfant.getEtat();
			writer.append((double)e.getPosition() + "");
			writer.append(separateur);
			writer.append(noeudEnfant.totalRecompense() + "");
			writer.append(separateur);
			writer.append("\n");
			writer.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void ecrireDPW(long temps, 
			double C, 
			double a, 
			int iter, 
			Noeud noeudRacine,
			Noeud noeudEnfant) {
		try {
			writer3.append(temps + "");
			writer3.append(separateur);
			writer3.append(iter + "");
			writer3.append(separateur);
			writer3.append(C + "");
			writer3.append(separateur);
			writer3.append(a + "");
			writer3.append(separateur);
			writer3.append(noeudRacine.nbEnfant() + "");
			writer3.append(separateur);
			writer3.append(noeudRacine.nbSimulation() + "");
			writer3.append(separateur);
			writer3.append(noeudEnfant.nbSimulation() + "");
			writer3.append(separateur);
			Etat e = noeudRacine.getEtat();
			writer3.append((double)e.getPosition() + "");
			writer3.append(separateur);
			writer3.append( "");
			writer3.append(separateur);
			e = noeudEnfant.getEtat();
			writer3.append((double)e.getPosition() + "");
			writer3.append(separateur);
			writer3.append( "");
			writer3.append(separateur);
			writer3.append(noeudEnfant.totalRecompense() + "");
			writer3.append(separateur);
			writer3.append("\n");
			writer3.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void nouvelleLigne() {
		try {
			writer.append("\n");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
