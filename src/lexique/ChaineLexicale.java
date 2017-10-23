package lexique;

import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import evaluateur.EvaluateurBegue;
import phonetiseur.RegExp;
import phonetiseur.Syllabe;
import phonetiseur.Syllabeur;
/**
 *  Une chaine lexicale est un enchainement de mots ayant une écriture, une prononciation et un contexte phonétique dans lequel il peut se réaliser.
 *  Elle est produite à partir de la combinaison d'une chaine categorielle.  
 *   */
public class ChaineLexicale implements Comparable<ChaineLexicale>{

    private String 		ecriture;
    private String 		prononciation;
    private Pattern 	regExp;
    //public 	static Evaluateur 	evaluateurCourant;
    
    private void init(String ecriture, String prononciation, Pattern regExp){
		this.ecriture = ecriture;
		this.prononciation = prononciation; 
		this.regExp = regExp;
    }
    
    /** 
     * Construit une chainelexicale à partir d'un mot, de sa prononciation et son contexte phonétique en regEx
     * @param ecriture
     * @param prononciation
     * @param regExp
     */
    public ChaineLexicale(String ecriture, String prononciation, Pattern regExp){
    	init(ecriture, prononciation,regExp);
    }
    
    //inutile dans l'absolu --> seulement pour le main
    /*public ChaineLexicale(String ecriture, Pattern regExp){
    	try {
			init(ecriture, SynthetiseurVocal.synthese(ecriture),regExp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    /**
     * Construit une chaine lexicale à partir d'un fichier contenant les informations
     * @param ligneFichier
     */
    public ChaineLexicale(String ligneFichier){
    	String[] ligne = ligneFichier.split("\t");
		String ecriture = ligne[0];
		String prononciation = ligne[1].substring(1, ligne[1].length()-1);
		String contexte = ligne[2].substring(1);
		Pattern patternContext = Pattern.compile(contexte);
		init(ecriture, prononciation, patternContext);
    }
    
    /**
     * Test les liaisons en fin de mot, créé un seul mot si il n'y a pas de différence suivant le contexte, deux mots sinon
     * nous jouons sur la syntaxe new ChaineLexicale pour permettre d'instancier des mots seulement à partir d'une écriture 
     * (attention à une meme écriture peuvent correspondre plusieurs prononciation (exemple : laison), nous aurons alors plusieurs mots leurs correspondant)
     * @param ecriture
     * @return une Linkedlist contenant soit un soit deux mots
     */
    public static LinkedList<ChaineLexicale> newChaineLexicale(String ecriture){
    	
    	LinkedList<ChaineLexicale> retour = new LinkedList<ChaineLexicale>();
    	
		String consonne = " rats";
		String voyelle 	= " eaux";
		String testDevantConsonne = null;
		String testDevantVoyelle = null;
		
		try {
			testDevantConsonne = SynthetiseurVocal.synthese(ecriture + consonne);
			testDevantVoyelle = SynthetiseurVocal.synthese(ecriture + voyelle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String prononciationDevantConsonne = testDevantConsonne.substring(0, testDevantConsonne.lastIndexOf(" "));
		String prononciationDevantVoyelle = testDevantVoyelle.substring(0, testDevantVoyelle.lastIndexOf(" "));
		if(prononciationDevantConsonne.equals(prononciationDevantVoyelle)){ //un seul mot car prononciation unique
			ChaineLexicale mot = new ChaineLexicale(ecriture, prononciationDevantConsonne, RegExp.regExKleen);
			retour.add(mot);
			return retour;
		}
		else{//deux mots car deux prononciations (liaison)
			ChaineLexicale mot1 = new ChaineLexicale(ecriture,prononciationDevantConsonne, RegExp.regExNonLiaisonConsonnantique);
			ChaineLexicale mot2 = new ChaineLexicale(ecriture, prononciationDevantVoyelle, RegExp.regExLaisonVocalique);
			retour.add(mot1);
			retour.add(mot2);
			return retour;

		}
    }   

    public String getEcriture(){return this.ecriture;}
    public String getPrononciation(){return this.prononciation;}
    public Pattern getRegExp(){return this.regExp;}
    public LinkedList<Syllabe> getSyllabes(){
    	return Syllabeur.syllaber(this);
    }

    public void setEcriture(String ecriture){this.ecriture = ecriture;}
    public void setPrononciation(String prononciation){this.prononciation = prononciation;}
    public void setRegExp(Pattern regExp){this.regExp = regExp;}

    
    public boolean estUnAlexandrin(){
    	LinkedList<Syllabe> syllabation = Syllabeur.syllaber(this);
    	return syllabation.size() == 12;
    }
    
    public boolean estConcatenableAvec(ChaineLexicale motDroite){
    	Matcher bonContext = this.regExp.matcher(motDroite.prononciation.charAt(0)+"");
    	return bonContext.matches();
    }
    
    /**
     * Concatène deux chaines lexicales this et b
     * @param b la deuxième chainelexicale
     * @return la concaténation des deux chaines lexicales
     */
    //ne verifie pas la conceptabilité en contexte
    public ChaineLexicale concatener(ChaineLexicale b){
    	String ecriture2 = this.ecriture.charAt(this.ecriture.length()-1)=='\''? this.ecriture+b.ecriture : this.ecriture+" "+b.ecriture ;
    	String prononciation2 = this.prononciation + b.prononciation;
    	Pattern regExp2 = b.regExp;
    	return new ChaineLexicale(ecriture2, prononciation2, regExp2);
    }

    //TODO : faire un toString canonique qui s'imprime dans le fichier
    public String toLexicalString(){
    	StringBuilder sb = new StringBuilder(this.ecriture.length()+this.prononciation.length()+20);
		sb.append(this.getEcriture());
		sb.append("\t[");
		sb.append(this.getPrononciation());
		sb.append("]\t_");
		sb.append(this.getRegExp());
		return sb.toString();
    }
    
    public String toString(){
		StringBuilder sb = new StringBuilder(this.ecriture.length()+this.prononciation.length()+30);
		sb.append(this.getEcriture());
		int nbEspaces  = 80-this.ecriture.length();
		for(int i = 0; i < nbEspaces; i++){
			sb.append(" ");
		}
		/*sb.append(" : ");
		sb.append(this.evaluate());

		
		sb.append(" : \t\t");
		LinkedList<Syllabe> syllabes = Syllabeur.syllaber(this);
		sb.append(syllabes);*/
		sb.append("\n");
		return sb.toString();
    }

	@Override
	public int compareTo(ChaineLexicale c) {
		return ((Double)(this.evaluate())).compareTo((Double)(c.evaluate()));
	}
	
	public double evaluate() {
		return EvaluateurBegue.EVALUATEUR.evaluate(this);
	}
	
	/*
    public static void main (String[] args){
    	LinkedList<ChaineLexicale> mots = newChaineLexicale("les");
    	for(ChaineLexicale cc : mots){
    		System.out.println(cc);
    	}
    }
  */  
}
