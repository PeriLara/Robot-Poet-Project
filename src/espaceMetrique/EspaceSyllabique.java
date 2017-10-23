package espaceMetrique;


import phonetiseur.Syllabe;

/**
 * Espace permettant la mesure de la distance entre deux syllabes.
 * Les dimensions retenues sont :
 * 				- l'attaque
 * 				- le noyau
 * 				- la coda
 * Chacune de ces dimensions correspond à des groupes de phones pouvant être comparés deux-à-deux
 */

@SuppressWarnings("serial")
public class EspaceSyllabique extends EspaceMetriqueCoefficiente<Syllabe>{

	/**Toutes les distances superieures à MIN_QUALITY sont ramenées à la distance maximale. Cela permet de ne prendre en compte que les distances suffisemment proches et de négliger les autres.*/
	static final double MIN_QUALITY = 0.3;//0.2;
	
	static final double COEF_ATTAQUE 	= 0.4;
	static final double COEF_NOYEAU 	= 0.2;
	static final double COEF_CODA 		= 0.4;
	//static final double COEF_GLIDES 	= 0.1; //pas encore implémenté pour ?

	
	MetriqueGroupePhonetique metriqueGroupeConsonnantique = new MetriqueGroupePhonetique(new EspaceConsonnantique());
	MetriqueGroupePhonetique metriqueGroupeVocalique = new MetriqueGroupePhonetique(new EspaceVocalique());
	
	/**
	 * Calcule la distance entre deux syllabes
	 * 
	 * Dans le cas où l'une des deux syllabes est vide (cf MetriqueGroupePhonetique.java) la distance est automatiquement 1
	 * 
	 * @param a : premiere syllabe
	 * @param b : deuxieme syllabe
	 * 
	 * @return la distance entre les deux syllabes. 
	 */
	//on ajoute la distance au phone nul qui est égal à la distance à [j]+0.1
	@Override
	public double distance(Syllabe a, Syllabe b){
		double retour = 0;
		try{
			String attaqueA = a.attaque;
			String attaqueB = b.attaque;
			String noyeauA = a.noyeau;
			String noyeauB = b.noyeau;
			String codaA = a.coda;
			String codaB = b.coda;
			retour += COEF_ATTAQUE*metriqueGroupeConsonnantique.distance(attaqueA, attaqueB);
			retour += COEF_NOYEAU*metriqueGroupeVocalique.distance(noyeauA, noyeauB);		
			retour += COEF_CODA*metriqueGroupeConsonnantique.distance(codaA, codaB);
		}
		catch(Exception e){
			//System.out.println("probleme distance syllabes : "+a+" : "+b);
		}
		return retour<=MIN_QUALITY?retour:1;
	}
	
	public EspaceSyllabique(){
		super();
	}
	
	public static void main(String[] args) throws Exception{
		EspaceSyllabique espace = new EspaceSyllabique();
		Syllabe a = new Syllabe("trwas");
		Syllabe b = new Syllabe("traz");
		System.out.println(espace.distance(a, b));
	}
	
}
