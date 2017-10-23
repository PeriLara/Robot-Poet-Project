package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Classe contenant des fonctions statiques utilisées dans d'autres classes.*/

public class Util {

	static String sautLigne = System.getProperty("line.separator");
	
	//code trouve sur http://stackoverflow.com/
	public static String readFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader (path));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		try {
			while( ( line = reader.readLine() ) != null ) {
				stringBuilder.append( line );
				stringBuilder.append( ls );
			}
			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}
	
	//code deduit de http://stackoverflow.com/
	public static ArrayList<String> readFileLineByLine(String path) throws IOException {
		
		BufferedReader 			reader = new BufferedReader(new FileReader (path));
		String         			line = null;
		ArrayList<String> 		retour = new ArrayList<String>();		

		try {
			while( ( line = reader.readLine() ) != null ) {
				retour.add(line);
			}
			return retour;
		} finally {
			reader.close();
		}
	}
	
	//ecriture dans le fichier grace a un Buffered writer (plus rapide) http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
	public static void write (String s, String path) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		bw.write(s);
		bw.close();
	}
	
	static double fact(int k){
		double retour = 1;
		for(int i = 1; i <= k; i++){
			retour *= i;
		}
		return retour;
	}
	
	static double parmi(int k, int n){
		return fact(n)/(fact(n-k)*fact(k));
		
	}
	
	//combinaison i parmi k suivante et null si la dernier combinaison a ete parcourue
	//la liste en argument est un echantillon de K parmi N qu'on veut incrementer
	//pour etre bien employee, les incrementations successives doivent avoir lieu sur une liste bien formee des I premiers elements de K
	private static int[] incrementerLeComptageIParmiNFoisN(int[] tab, int n){
		//indice du dernier element (:= dernier "digit")
		int i = tab.length - 1;
						
		// pour chaque digit, la valeur limite est = a la valeur limite moins sa distance au dernier digit
		int limite = n - (tab.length-i);
		
		//jusqu'a ce qu'on ait depasse le digit 0
		while(i != -1){
			
			if(tab[i] < limite){
			//Dans le cas ou tout s'est bien passe (pas entre dans le else),
							
				//incrementation de ce digit
				tab[i]++;
							
				//on reinitisalise toutes les valeurs a droite du digit
				for(int k = i+1; k < tab.length; k++){
					tab[k] = tab[k-1]+1;
					if(tab[k] > n-1) return null;
				}
					
				return tab;
			}
			//sinon on passe au digit precedent
			else{
				//sinon au passe au precedent
				i--;
				limite--;
				continue;
			}
		}
		return null;
	}
	
	//enumeration des k parmi n dans un tableau
	public static int[][] enumererKParmiN(int k, int n){
		
		if(k<=0 || k>n){
			return null;
		}
		
		//taille de l'array de retour
		int nombreDeKParmiI = (int) parmi(k, n);
		
		//tableau de retour 
		int[][] retour = new int[nombreDeKParmiI][k];
		
		//initialisation  du tableau a la premiere combinaison binomiale
		int[] tab = new int[k];
		for(int i = 0; i < k; i++){
			tab[i] = i;
		}
		
		int c = 0 ;
		while(tab != null){
			retour[c] = tab.clone();
			c++;
			tab = incrementerLeComptageIParmiNFoisN(tab, n);
		}
		
		return retour;
	}
	

	//Cas de n objets indistinguables a ranger dans r urnes distinguables
	//soit un vecteur X = (x1, x2, ..., xr), tel que x1 + x2 + ... + xr = n, on cherche a enumerer les vecteurs X verifiant la propriete
	public static int[][] enumererVecteursDeTailleRDontSommeInterneVautN(int r, int n){
		//cas problematique ou n vaut 0 on retourne un vecteur null
		if(n == 0){
			return null;
		}
		//cas ou on ne veut un vecteur a un seul element
		if(r == 1){
			int [][] retour = {{n}};
			return retour;
		}
		//il suffit de choisir r-1 separations parmi n-1 ecarts entre les objets, afin de distinguer ces objets en r groupes dont la somme fait n
		int[][] enumerationsDesCombinaisonsDeRMoinsUnParmiNMoinsUn = enumererKParmiN(r-1, n-1);
		int		nombreDeCombinaisonsBinomiales = enumerationsDesCombinaisonsDeRMoinsUnParmiNMoinsUn.length;
		
		//comme les objets eux-memes sont indistinguables ce qui nous interesse c'est le nombre de boules que contiennent chaque urne
		int[][] enumerationTailleDesUrnes = new int[nombreDeCombinaisonsBinomiales][r];
		
		
		//Pour chaqune des combinaisons de seprations possibles
		for(int i = 0; i < enumerationsDesCombinaisonsDeRMoinsUnParmiNMoinsUn.length; i++){
			int[] comb = enumerationsDesCombinaisonsDeRMoinsUnParmiNMoinsUn[i];
			//la variable suivante est une variable temporaire enregistrent au fur et a mesure l'indice de la derniere separation (afin que la taille des urnes soit indice - indicePrecedent)
			int indiceSeparationPrecedente = 0;
			//l'indice de la separtion indique la taille de l'urne
			for(int j = 0; j < comb.length; j++){
				int indiceSepartion = comb[j];
				int nouvelIndiceSeparation = indiceSepartion+1;
				enumerationTailleDesUrnes[i][j] = nouvelIndiceSeparation - indiceSeparationPrecedente;
				indiceSeparationPrecedente = nouvelIndiceSeparation;
			}
			//et a droite de la derniere separation
			enumerationTailleDesUrnes[i][comb.length] = (n) - indiceSeparationPrecedente;			
		}
		
		return enumerationTailleDesUrnes;			
	}
	/*
	public static int[][] enumererLesCombinaisonsDeNVecteurs(ArrayList<int[]> listeDesNVecteurs){
		ArrayList<ArrayList<Integer>> combinaisonsSuccesives = new ArrayList<<Integer>>();
		
		int i = 0;
		int[] temp = listeDesNVecteurs.get(i);
		
		do{
			for(ArrayList<Integer> l : combinaisonsSuccesives){
				for(int k : temp)
					temp = listeDesNVecteurs.get(i);
				}
			}
		}
	}

	
	//vecteur lu a l'envers
	public static int[] vecteurInverse(int[] vecteur){
		int[] retour = new int [vecteur.length];
		for(int i = 0; i < vecteur.length; i++){
			retour[(vecteur.length-1)-i] = vecteur[i];
		}
		return retour;
	}
	
	//la taille est a priori non connue donc le retour sera une arrayList
	public static ArrayList<int[]> supprimerVecteursDontUnComposantEstPlusGrandQueK(int[][] tab, int k){
		ArrayList<int[]> retour = new ArrayList<int[]>();
		for(int[] comb : tab){
			boolean addComb = true;
			for(int elem : comb){
				if (elem > k){
					addComb = false;
				}
			}
			if(addComb){
				retour.add(comb);
			}
		}
		return retour;
	}
	/*
	//cas d'une ArrayList en entree
	private static ArrayList<int[]> supprimerVecteursDontUnComposantEstPlusGrandQueK(ArrayList<int[]> tab, int k){
		int[][] retour = new int[tab.size()][];
		int i = 0;
		for(int[] comb : tab){
			retour[i] = comb;
			i++;
		}
		return supprimerVecteursDontUnComposantEstPlusGrandQueK(retour, k);
	}

	//une methode qui compare deux tableaux terme a terme
	public static boolean equals(int[] array1, int[] array2) {
        boolean b = true;
        if (array1 != null && array2 != null){
          if (array1.length != array2.length)
              b = false;
          else
              for (int i = 0; i < array2.length; i++) {
                  if (array2[i] != array1[i]) {
                      b = false;    
                  }                 
            }
        }else{
          b = false;
        }
        return b;
    }
	
	public static boolean ArrayListAContainsCombC(ArrayList<int[]> a, int[] c){
		for(int[] combRetour : a){
			if(Util.equals(combRetour, c)){
				return true;
			}
		}
		return false;
	}
	
	//la taille est a priori non connue donc le retour sera une arrayList
	public static ArrayList<int[]> supprimerLesDoublonsParSymetrie(int[][] tab){
			ArrayList<int[]> retour = new ArrayList<int[]>();
			for(int[] comb : tab){
				int[] vecteurInverse = Util.vecteurInverse(comb);
				
				boolean vecteurContainsInverse = ArrayListAContainsCombC(retour, vecteurInverse);
				
				if(!vecteurContainsInverse){
					retour.add(comb);
				}
			}
			return retour;
		}
	
	//cas d'une ArrayList en entree
	public static ArrayList<int[]> supprimerLesDoublonsParSymetrie(ArrayList<int[]> tab){
		int[][] retour = new int[tab.size()][];
		int i = 0;
		for(int[] comb : tab){
			retour[i] = comb;
			i++;
		}
		return supprimerLesDoublonsParSymetrie(retour);
	}
	*/
	
	public static void main(String[] args) {
		int[][] combBinom = Util.enumererKParmiN(3, 25);
		
		for(int[] comb : combBinom){
			System.out.print("{");
			for(int j : comb){
				System.out.print(j+",");
			}
			System.out.println("}");
		}
	}
	
	
	//code pour mesurer le temps entre deux evenements : 
	//long start = System.nanoTime();
	//long time = System.nanoTime() - start;
}
