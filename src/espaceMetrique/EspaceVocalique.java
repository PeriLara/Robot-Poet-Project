package espaceMetrique;

import java.text.DecimalFormat;

/**
 * Espace permettant la mesure de la distance entre deux voyelles.
 * Les dimensions retenues sont :
 * 				- la position de la langue (anterieure, mi-anterieur, medianes, posterieurs)
 * 				- l'aperture (fermée, mi-fermée, mi-ouverte, ouverte)
 * 				- le type de voyelle (voyelles vs glides)
 * 				- la nasalite
 * 				- l'arrondissement
 * Les deux dernières dimensions sont binaires.
 * 
 * Les glides sont considérés ici comme des voyelles OU consonnes ; le h muet est ignoré des deux
 */


//les glides sont considérés ici comme des voyelles OU consonne ; le h muet est ignoré des deux
@SuppressWarnings("serial")
public class EspaceVocalique extends EspaceMetriqueCoefficiente<Character> {

	/**Toutes les distances superieures à MIN_QUALITY sont ramenées à la distance maximale. Cela permet de ne prendre en compte que les distances suffisemment proches et de négliger les autres.*/
	static final double MIN_QUALITY = 0.2;

	static final double COEF_TYPE_VOYELLE 		= 0.01754385964; //voyelle vs glide
	static final double COEF_NASALITE 			= 0.24561403508;
	static final double COEF_ARRONDIE 			= 0.14035087719;
	static final double COEF_POSITION 			= 0.03508771929;
	static final double COEF_APERTURE 			= 0.52631578947;

	static final double POSITION_ANTERIEURES 	= 0;
	static final double POSITION_MI_ANTERIEURES	= 0.2;
	static final double POSITION_MEDIANES 		= 0.5;	
	static final double POSITION_POSTERIEURES	= 1;	
	
	static final double APERTURE_FERMEES 		= 0;
	static final double APERTURE_MI_FERMEES 	= 0.5;
	static final double APERTURE_MI_OUVERTES 	= 0.6;
	static final double APERTURE_OUVERTES 		= 1;
	
	
	/**
	 * Calcule la distance entre deux voyelles
	 * 
	 * Dans le cas où l'une des deux consonnes est vide (cf MetriqueGroupePhonetique.java) la distance est automatiquement 1
	 * 
	 * @param a : premiere voyelle
	 * @param b : deuxieme voyelle
	 * 
	 * @return la distance entre les deux voyelles. 
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
	
	private Dimension<Character> typeVoyelle 	= new Dimension<Character>(COEF_TYPE_VOYELLE); 
	private Dimension<Character> nasalite 		= new Dimension<Character>(COEF_NASALITE);
	private Dimension<Character> arrondie		= new Dimension<Character>(COEF_ARRONDIE);
	private Dimension<Character> position 		= new Dimension<Character>(COEF_POSITION); //consonne (qui ont une version voisée et une non voisée), semi-consonne(pas de version non-voisée explicite)
	private Dimension<Character> aperture 		= new Dimension<Character>(COEF_POSITION);
	
	private void init(){
		initTypeVoyelle();
		initPosition();
		initNasalite();
		initArrondissement();
		initAperture();	
	}

	private void initTypeVoyelle(){
		this.add(typeVoyelle);
		Character[] voyelle = {'a','@','e','i','o','u','y','E','O','1','2','3','4', '7'};
		Character[] semivoyelle = {'w','j','5'};
		this.typeVoyelle.add(new Curseur<Character>(voyelle, 0));
		this.typeVoyelle.add(new Curseur<Character>(semivoyelle, 1));
	}
	
	private void initNasalite(){
		this.add(nasalite);
		Character[] nonNasales = {'a','@','e','i','o','u','y','E','O','w','j','5', '7'};
		Character[] nasales = {'1','2','3','4'};
		this.nasalite.add(new Curseur<Character>(nonNasales, 0));
		this.nasalite.add(new Curseur<Character>(nasales, 1));
	}
	
	private void initArrondissement(){
		this.add(arrondie);
		Character[] arrondies = {'i','j','e','E','2','4','j'};
		Character[] nonArrondies = {'a','@','o','u','w','y','5','O','1','3', '7'};
		this.arrondie.add(new Curseur<Character>(arrondies, 0));
		this.arrondie.add(new Curseur<Character>(nonArrondies, 1));
	}

	private void initPosition(){
		this.add(position);
		Character[] anterieures  = {'e','i','j','E','2'};
		Character[] miAnterieures = {'y','5','4','7'};
		Character[] medianes  = {'@'};
		Character[] posterieures= {'a','o','O','u','w','1','3'};
		this.position.add(new Curseur<Character>(anterieures, EspaceVocalique.POSITION_ANTERIEURES));
		this.position.add(new Curseur<Character>(miAnterieures, EspaceVocalique.POSITION_MI_ANTERIEURES));
		this.position.add(new Curseur<Character>(medianes, EspaceVocalique.POSITION_MEDIANES));
		this.position.add(new Curseur<Character>(posterieures, EspaceVocalique.POSITION_POSTERIEURES));
	}
	
	private void initAperture(){
		this.add(aperture);
		Character[] fermees  	= {'i','j','y','5','u','w'};
		Character[] miFermees 	= {'e','@','o','O'};
		Character[] miOuvertes  = {'E','2','3','7'};
		Character[] ouvertes 	= {'a','1','4'};
		this.aperture.add(new Curseur<Character>(fermees, EspaceVocalique.APERTURE_FERMEES));
		this.aperture.add(new Curseur<Character>(miFermees, EspaceVocalique.APERTURE_MI_FERMEES));
		this.aperture.add(new Curseur<Character>(miOuvertes, EspaceVocalique.APERTURE_MI_OUVERTES));
		this.aperture.add(new Curseur<Character>(ouvertes, EspaceVocalique.APERTURE_OUVERTES));
	}
	
	public EspaceVocalique(){
		super();
		init();
	}
	
	private static Character[] voyelles  = {'@','a','e','i','o','u','y','E','O','1','2','3','4','w','j','5','0'};
	
	public static void main(String[] args){
		EspaceVocalique espace = new EspaceVocalique();
		System.out.print("\t");
		for(Character a : voyelles){
			System.out.print(a+"\t");
		}
		System.out.println();
		
		for(Character a : voyelles){
			System.out.print(a+"\t");
			for(Character b : voyelles){
				double dist = espace.distance(a, b);
				DecimalFormat numberFormat = new DecimalFormat("0.00");
				System.out.print(numberFormat.format(dist)+"\t");
			}
			System.out.println();
		}
	}
	
}
