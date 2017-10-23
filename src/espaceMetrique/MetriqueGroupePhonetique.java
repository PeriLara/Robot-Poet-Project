package espaceMetrique;

import util.Util;
import datatype.listeOrdonnee.ListeOrdonnee;

//[attention bugue si utilisé avec des ecarts trop important de taille]

class MetriqueGroupePhonetique implements Mesure<String> {

	EspaceMetriqueCoefficiente<Character> espacePhonetiqueMesurable;
	
	public MetriqueGroupePhonetique(EspaceMetriqueCoefficiente<Character> espace){
		super();
		this.espacePhonetiqueMesurable = espace;
		//init();
	}

	//les deux groupes doivent etre du même type (C-C, ou V-V ou eventuellement C-Glide/V-Glide) 
	//distance minimale en ajoutant suffisement de phonemes '0' pour avoir deux groupes de même taille
	@Override
	public double distance(String a, String b) {
		ListeOrdonnee<Double> distances = new ListeOrdonnee<Double>();
		int		minLength 	= Math.min(a.length(), b.length());
		int		maxLength 	= Math.max(a.length(), b.length());
		if(a.length()==0 || b.length() == 0){//si l'un des deux est la chaine vide on le remplace par autant de '0'
			String replace = "";
			for(int i = 0; i < maxLength; i++){
				replace += '0';
			}
			if(a.length()==0) a = replace;
			else b = replace;
			return this.distanceFormate(a, b);
		}
		if(minLength == maxLength) return this.distanceFormate(a, b); //deja formate
		else{//a formater
			String 	strMin		= a.length()==minLength?a:b;
			String 	strMax		= a.length()==maxLength?a:b;
			String 	str;
			int 	difference 	= Math.abs(a.length()-b.length());
			int[][] combBinom 	= Util.enumererKParmiN(difference, minLength+1);
			for(int[] comb : combBinom){
				StringBuilder 	sb 	= new StringBuilder(maxLength);
				int	i = 0;
				str	= strMin;
				sb.append(str);
				for(int j : comb){
					sb.insert(j+i, '0');
					i++;
				}
				double result = this.distanceFormate(strMax, sb.toString());
				if(!distances.contains(result)){
					distances.add(result);
				}
			}
			return distances.min();
		}
	}
	
	//les deux groupes doivent etre du même type (C-C, ou V-V ou eventuellement C-Glide/V-Glide)
	//ET de même taille
	private double distanceFormate(String a, String b) {
		if(a.length()==0) return 0;
		double total = 0;
		for (int i = 0; i < a.length(); i++) {
		    total += this.espacePhonetiqueMesurable.distance(a.charAt(i), b.charAt(i));
		}
		double moyenne = total / a.length();
		return moyenne;
	}
	
	static void main(String[] args){
		EspaceConsonnantique espaceConsonantique = new EspaceConsonnantique();
		MetriqueGroupePhonetique metriqe = new MetriqueGroupePhonetique(espaceConsonantique);
		System.out.println(metriqe.distance("tsr","tbr"));
	}
	
}
