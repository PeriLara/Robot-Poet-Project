package espaceMetrique;

import datatype.listeOrdonnee.ListeOrdonnee;

//représente les curseurs<T> dans l'ordre d'apparitions
@SuppressWarnings("serial")
class Dimension<T> extends ListeOrdonnee<Curseur<T>> {

	//represente le coefficient qui sera attribué à la dimension
	public double coefficient;
	
	//au minimum la dimension est constituée de deux classes d'objets ()
	public Dimension(double coefficient){
		super();
		this.coefficient = coefficient;
	}

	//renvoie la distance DANS la dimension (donc distance non coefficientée)
	public double distance(T a, T b) {
		double positionA = Double.POSITIVE_INFINITY;
		double positionB = Double.NEGATIVE_INFINITY;
		for(Curseur<T> c : this){
			if(c.contains(a)) positionA = c.position;
			if(c.contains(b)) positionB = c.position;
		}
		return Math.abs(positionA - positionB);
	}
	
	public String toString(){
		String s = "Coef : "+this.coefficient+" - \n";
		for(Curseur<T> curs : this){
			s += "\t"+curs.toString()+"\n";
		}
		return s;
	}
}
