package evaluateur;

import java.util.LinkedList;

import phonetiseur.Syllabe;
import espaceMetrique.EspaceSyllabique;
import lexique.ChaineLexicale;

/**Evalue des chaines lexicales selon la contrainte du poeme pour begue. */

public class EvaluateurBegue implements Evaluateur{

	static EspaceSyllabique ESPACE = new EspaceSyllabique();
	/**Instanciation statique d'un evaluateur.*/
	public static EvaluateurBegue  EVALUATEUR = new EvaluateurBegue();
	
	/**
	 * Evalue le respect de la contrainte du poeme pour begue en faisant la moyenne des distances inter-syllabiques pour chaque paire de syllabe successive.
	 * @param c : la chaine lexicale à évaluer
	 * @return une note entre 0 et 1, 0 étant la meilleure note, 1 la moins bonne.
	 */
	public double evaluate(ChaineLexicale c){
		double retour = 0;
		LinkedList<Syllabe> syllabes = c.getSyllabes();
		int length = syllabes.size();
		Syllabe precedente = syllabes.pollFirst();
		while(!syllabes.isEmpty()){
			Syllabe suivante = syllabes.pollFirst();
			retour += ESPACE.distance(precedente, suivante);
			precedente = suivante;
		}
		return retour/length;
	}
	
	//double syllabes
	/*public static double evaluate(ChaineLexicale c){
		double retour = 0;
		LinkedList<Syllabe> syllabes = c.getSyllabes();
		if(syllabes.size()<4) return retour;
			else{
			int length = syllabes.size();
			Syllabe[] couplePrecedent = new Syllabe[2];
			couplePrecedent[0] = syllabes.pollFirst();
			couplePrecedent[1] = syllabes.pollFirst();
			while(syllabes.size() > 2){
				Syllabe[] coupleSuivant = new Syllabe[2];
				coupleSuivant[0] = syllabes.get(0);
				coupleSuivant[1] = syllabes.get(1);
				double distancePremiers = (ESPACE.distance(couplePrecedent[0], coupleSuivant[0]));
				double distancesSeconds = (ESPACE.distance(couplePrecedent[1], coupleSuivant[1]));
				double distanceCouples = (distancePremiers+distancesSeconds)/2;			
				retour += distanceCouples;
				couplePrecedent[0] = syllabes.pollFirst();
				couplePrecedent[1] = syllabes.pollFirst();
			}
			return retour/(length-1);
		}
	}*/
	
	/*
	public static void main (String[] args) throws IOException{
		String test = "Un eunuque ne nique que quand Candide dit des idées idiotes";
		String pron = SynthetiseurVocal.synthese(test);
		System.out.println(pron);
		ChaineLexicale cc = new ChaineLexicale(test, pron, RegExp.regExPhone);
		System.out.println(evaluate(cc));
	}*/
	
}
