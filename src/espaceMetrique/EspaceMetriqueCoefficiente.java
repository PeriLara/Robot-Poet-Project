package espaceMetrique;

import java.util.LinkedList;

/** 
 * Le super-type des espaces métriques consonnantiques, syllabiques et vocaliques
 * @param <T>, type des éléments que nous plaçons dans l'espace
 */
@SuppressWarnings("serial")
public abstract class EspaceMetriqueCoefficiente<T> extends LinkedList<Dimension<T>> implements Mesure<T> {

	//implementer un moyen de ne pas etre trop penalisé par dist(glide, 0), sans etre trop avantagé
	//public static char		PHONE_DE_REFERENCE = 'j';
	//public static double		DISTANCE_PHONE_NUL_A_PHONE_DE_REFERENCE = 0.1;
	
	/** 
	 * Calcule la distance entre les éléments a et b comme moyenne des distances de ceux-ci sur chacune des dimensions coefficientee.
	 * @param a : premier élément 
	 * @param b : second élément
	 * 
	 * @return la moyenne coefficientee
	 */
	public double distance(T a, T b){
		double retour = 0;
		for(Dimension<T> d : this){
			retour += (Double)(d.coefficient * d.distance(a, b));
		}
		return retour;
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < this.size(); i++){
			s += "Dimension "+i+" : \n";
			s += this.get(i).toString();
		}
		return s;
	}
}
