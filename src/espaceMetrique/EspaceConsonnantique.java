package espaceMetrique;

import java.text.DecimalFormat;

/**
 * Espace permettant la mesure de la distance entre deux consonnes.
 * Les dimensions retenues sont :
 * 				- la position de la langue (vélaire, alveolaire,labiodentale,bilabiale)
 * 				- le type de consonnes (glide+liquide+nasale+roulee vs fricatives+occlusives)
 * 				- le voisement
 * 				- la nasalite
 * 				- la frication
 * Toutes ces dimensions sont binaires sauf la position de la langue
 * 
 * Les glides sont considérés ici comme des voyelles OU consonnes ; le h muet est ignoré des deux
 */

@SuppressWarnings("serial")
public class EspaceConsonnantique extends EspaceMetriqueCoefficiente<Character> {
	
	/**Toutes les distances superieures à MIN_QUALITY sont ramenées à la distance maximale. Cela permet de ne prendre en compte que les distances suffisemment proches et de négliger les autres.*/
	public static final double MIN_QUALITY = 0.2;

	static final double COEF_TYPE_CONSONNE = 0.2;
	static final double COEF_VOISEMENT = 0.033333333333;
	static final double COEF_NASALITE = 0.23333333333;
	static final double COEF_FRICATION = 0.33333333333;
	static final double COEF_POSITION = 0.2;

	static final double POSITION_VELAIRES = 0;
	static final double POSITION_ALVEOLAIRES = 1.4;
	static final double POSITION_LABIO_DENTALES = 1.6;
	static final double POSITION_BILABIALES = 3;
	
	/**
	 * Calcule la distance entre deux consonnes
	 * 
	 * Dans le cas où l'une des deux consonnes est vide (cf MetriqueGroupePhonetique.java) la distance est automatiquement 1
	 * 
	 * @param a : premiere consonne 
	 * @param b : deuxieme consonne
	 * 
	 * @return la distance entre les deux consonnes. 
	 */
	//on ajoute la distance au phone nul qui est égal à la distance à [j]+0.1
	@Override
	public double distance(Character a, Character b){
		if(a.equals('0')||b.equals('0')){
			//if(a.equals('0')&&b.equals('0')) return 1;
			//else{
				/*Character phoneNonNul = null;
				if(!a.equals('0')) phoneNonNul = a;
				else phoneNonNul = b;*/
				return 1;
			//}
		}
		else{
			double dist = super.distance(a, b);
			return dist<=MIN_QUALITY?dist:1;
		}
	}

	private Dimension<Character> typeConsonne = new Dimension<Character>(COEF_TYPE_CONSONNE); //consonne (qui ont une version voisée et une non voisée), semi-consonne(pas de version non-voisée explicite)
	private Dimension<Character> voisement = new Dimension<Character>(COEF_VOISEMENT);
	private Dimension<Character> nasalite = new Dimension<Character>(COEF_NASALITE);
	private Dimension<Character> frication = new Dimension<Character>(COEF_FRICATION);
	private Dimension<Character> position = new Dimension<Character>(COEF_POSITION);
	
	private void init(){
		initTypeConsonne();
		initVoisement();
		initNasalite();
		initFrication();
		initPosition();
	}
	
	private void initTypeConsonne(){
		this.add(typeConsonne);
		Character[] consonnes = {'b','p','t','d','f','v','s','z','S','Z','k','g'};
		Character[] semiConsonne = {'m','n','l','N','r','w','j','5'};
		this.typeConsonne.add(new Curseur<Character>(consonnes, 0));
		this.typeConsonne.add(new Curseur<Character>(semiConsonne, 1));
	}
	
	private void initVoisement(){
		this.add(voisement);
		Character[] voisees = {'b','m','d','n','N','v','z','Z','g','l','r','w','j','5'};
		Character[] nonVoisees = {'p','t','f','s','S','k'};
		this.voisement.add(new Curseur<Character>(voisees, 0));
		this.voisement.add(new Curseur<Character>(nonVoisees, 1));
	}
	
	private void initNasalite(){
		this.add(nasalite);
		Character[] nasales = {'b','p','t','d','f','v','s','z','S','Z','k','g','l','r','w','j','5'};
		Character[] nonNasales = {'m','n','N'};
		this.nasalite.add(new Curseur<Character>(nasales, 0));
		this.nasalite.add(new Curseur<Character>(nonNasales, 1));
	}
	
	private void initFrication(){
		this.add(frication);
		Character[] occlusives  = {'b','p','t','d','k','g'};
		Character[] fricatives= {'f','v','s','z','S','Z','m','n','N','l','r','w','j','5'};
		this.frication.add(new Curseur<Character>(occlusives, 0));
		this.frication.add(new Curseur<Character>(fricatives, 1));
	}
	
	private void initPosition(){
		this.add(position);
		Character[] velaires  = {'k','g','r','w','5','N'};
		Character[] alveolaires = {'S','Z'};
		Character[] labio_dentales  = {'t','d','n','j','s','z','l','j'};
		Character[] bilabiales = {'b','p','m','f','v'};
		this.position.add(new Curseur<Character>(velaires, EspaceConsonnantique.POSITION_VELAIRES));
		this.position.add(new Curseur<Character>(alveolaires, EspaceConsonnantique.POSITION_ALVEOLAIRES));
		this.position.add(new Curseur<Character>(labio_dentales, EspaceConsonnantique.POSITION_LABIO_DENTALES));
		this.position.add(new Curseur<Character>(bilabiales, EspaceConsonnantique.POSITION_BILABIALES));
	}
	
	public EspaceConsonnantique(){
		super();
		init();
	}
	
	private static Character[] consonnes  = {'b','p','t','d','f','v','s','z','S','Z','k','g','l','m','n','r','w','j','5','0'};
	
	public static void main(String[] args){
		EspaceConsonnantique espace = new EspaceConsonnantique();
		System.out.print("\t");
		for(Character a : consonnes){
			System.out.print(a+"\t");
		}
		System.out.println();
		for(Character a : consonnes){
			System.out.print(a+"\t");
			for(Character b : consonnes){
				double dist = espace.distance(a, b);
				DecimalFormat numberFormat = new DecimalFormat("0.00");
				System.out.print(numberFormat.format(dist)+"\t");
			}
			System.out.println();
		}
	}
	
}
