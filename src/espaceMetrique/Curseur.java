package espaceMetrique;

import java.util.LinkedHashSet;

//ce curseur sera placé à une position determinée de la dimension dans laquelle il se trouve, tous les objets contents à ce curseur ont cette valeur pour projeter orthogonal sur cette dimension
//nous ne verifions pas que les caracteres constituent une partition de l'alphabet ce qui est essentiel pour le bon fonctionnement de cette classe et de Dimension
@SuppressWarnings("serial")
class Curseur<T> extends LinkedHashSet<T> implements Comparable<Curseur<T>> {

	double 	position;
	
	public Curseur(T[] entrees, double position){
		super();
		this.position = position;
		for(T t : entrees){
			this.add(t);
		}
	}
	
	public String toString(){
		String s = "{";
		for(T t : this){
			s += t.toString()+", ";
		}
		s += "}.position : "+position;
		return s;
	}
	
	@Override
	public int compareTo(Curseur<T> o) {
		return ((Double)(this.position)).compareTo((Double)(o.position));
	}
	
	public static void main (String[] args){
		char[] tab = "bcdfghjklmnpqrstvwx".toCharArray();
		Character[] entrees = new Character[tab.length];
		for(int i = 0; i < tab.length; i++){
			entrees[i] = (Character)tab[i]; 
		}
		Curseur<Character> cc = new Curseur<Character>(entrees, 0.5);
		System.out.println(cc.contains('a'));
		System.out.println(cc.contains('b'));
	}
	
}
