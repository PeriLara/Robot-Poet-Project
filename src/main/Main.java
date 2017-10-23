package main;
import java.io.IOException;
import java.util.ArrayList;

import lexique.Dossier;

import syntaxe.Categorie;
import syntaxe.ChaineCategorielle;

/**
 * Permet de passer en paramètre la chaine categorielle. 
 * Le paramètre passé en console peut spécifier la chaine catégorielle.
 * Sur linux (où nous pouvons utiliser espeaks), il est possible de fixer un mot unique dans la chaîne catégorielle.
 */
public class Main {
	
	/** Pour éviter l'explosion exponentielle au moment de la combinaison, on impose un capacité maximum contenant les meilleurs résultats */
	public static int MAX_CAPACITY = 500;
	/** Pour éviter l'explosion exponentielle au moment de la combinaison, on impose un qualité minimum (de 0 à 1), plus on s'approche de 0, plus la qualité est bonne, moins il y aura de résultats */
	public static double MIN_QUALITY = 0.18;
	
	public static void main(String[] args) throws IOException {
		String 				strFull 		= args[0];
		if(args.length>1) 	MAX_CAPACITY	= Integer.parseInt(args[1]);
		if(args.length>2) 	MIN_QUALITY		= Double.parseDouble(args[2]);
		strFull+=" "; 
		String[]	strTab  		= strFull.split(" "); 
		ArrayList<Categorie> cats	= new ArrayList<Categorie>(strTab.length);
		Dossier 	lexique 		= new Dossier("Categories");
		ArrayList<String> fichiers 	= lexique.listFichiers();
		
		for(String  str : strTab){
			if(fichiers.contains(str)) cats.add(new Categorie(str, true));
			else{
				cats.add(new Categorie(str, false));
			}
		}
		
		ChaineCategorielle result = cats.get(0);
		for(int i = 1; i < cats.size(); i++){
			result = Categorie.combine(result, cats.get(i));
		}
		String s = result.toString();
		System.out.println(s);
		System.out.println("fin : "+result.size()+" resultats");
	}
}
