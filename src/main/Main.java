package main;

import algorithme.formule.DPWidening;
import algorithme.formule.PWidening;
import algorithme.formule.Robuste;
import config.TrapFactory;
import dao.StatistiqueDAO;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Jan 26, 2017
 */
public class Main {

	public static void main(String[] args) {
		// si on desire des affichages sur la sortie std
		boolean output = true;
		int boucle = 100;
		double C = 6.0;
		double a = 0.4;
		
		int n = 1;
		while (fibonacci(n) < 200) {
			C = fibonacci(n);
			//pw(boucle, C, a, output);
			dpw(boucle, C, a, output);
			n++;
		}
	}
	
	public static void pw(int boucle, double C, double alpha, boolean output) {
		PWidening.C = C;
		PWidening.alpha = alpha;
		for(int i = 0 ; i < boucle ; i++) {
			new TrapFactory().jouer(new PWidening(), 
					new Robuste(), output);
		}
		StatistiqueDAO.getInstance().update();
	}
	
	public static void dpw(int boucle, double C, double alpha, boolean output) {
		DPWidening.C = C;
		DPWidening.alpha = alpha;
		for(int i = 0 ; i < boucle ; i++) {
			new TrapFactory().jouer(new DPWidening(), 
					 new Robuste(), output);
		}
		StatistiqueDAO.getInstance().update();
	}
	
	public static int fibonacci(int x) {
        if (x <= 1) return x;
        else return fibonacci(x-1) + fibonacci(x-2);
    }
}