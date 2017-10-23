package phonetiseur;

import java.util.regex.Matcher;

/** La strucuture d'une syllabe permet de calculer la distance entre deux syllabes sur les critères de la commparaison des quatres éléments deux à deux : prononciation, attaque, noyeau et coda. */

public class Syllabe {

	public String prononciation;// representation phonétique
	public String attaque;
	public String noyeau;
	public String coda;
	
	/**
	 * Constuit une Syllabe à partir de sa pronociation
	 * @param prononciation : String
	 * @throws Exception
	 */
	public Syllabe(String prononciation) throws Exception{
		this.prononciation = prononciation;
		Matcher m = RegExp.regExVoyelle.matcher(prononciation);
		m.find();
		int i = m.start();	
		this.attaque 	= this.prononciation.substring(0,i);
		this.noyeau 	= this.prononciation.charAt(i)+"";
		this.coda		= this.prononciation.substring(i+1);
	}
	
	// Getters/Setters
	public String getPrononciation() {
		return prononciation;
	}
	public void setPrononciation(String prononciation) {
		this.prononciation = prononciation;
	}
	
	public String toString(){
		return attaque+"/"+noyeau+"/"+coda;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(new Syllabe("str1p"));
	}

}
