package syntaxe;

import lexique.ChaineLexicale;
import main.Main;

import java.util.Collections;
import java.util.LinkedList;

/** Combine et/ou concatène plusieurs catégories afin de sortir un résultat grammaticalement correcte. */
@SuppressWarnings("serial")
public class ChaineCategorielle extends LinkedList<ChaineLexicale> {
	

	// --------------- CONSTRUCTEURS ---------------
	/** Instancie une catégorie "nulle" */
	public ChaineCategorielle() {
		super();
	}
	// inutile dans la mesure où les fichiers sont des categories simples
	// Instancie la categorie à partir du nom du fichier contenant les lexemes 
/*	public ChaineCategorielle(String nom) throws IOException {
		super();
		this.nom = nom;
		File fic = new File(Dossier.PATH + nom);
		FileReader in = new FileReader(fic);
		BufferedReader br = new BufferedReader(in);
		String readLine = "";
		while ((readLine = br.readLine()) != null && this.size()< MAX_CAPACITY) {
			System.out.println(readLine);
			//String formeSurface = readLine.substring(0, readLine.indexOf(' '));
			//this.add(new Mot(readLine, , ));
		}
		in.close();
	}
*/
	
	
	// --------------- METHODES ---------------

	/**
	 * Combine deux chaines catégorielles entre elles renvoie une nouvelle
	 * chaine catégorielle. Seuls sont conservés les chaines lexicales de qualité suffisante
	 */
	public static ChaineCategorielle combine(ChaineCategorielle c1, ChaineCategorielle c2) {
		ChaineCategorielle c3 = new Categorie();
		for (int i = 0; i < Main.MAX_CAPACITY && i < c1.size(); i++) {
			for (int j = 0; j < Main.MAX_CAPACITY && j < c2.size(); j++) { 
				ChaineLexicale l1 = c1.get(i);
				ChaineLexicale l2 = c2.get(j);
				if(l1.estConcatenableAvec(l2)){
					ChaineLexicale l3 = l1.concatener(l2);
					//System.out.println("ici : "+l3);
					//pour passer les erreurs liees a la liste ordonne
					try{
						if(l3.evaluate()<Main.MIN_QUALITY){
							//System.out.println(l3);
							c3.add(l3);
						}
					}
					catch(java.lang.IndexOutOfBoundsException|java.lang.IllegalStateException e ){
						j++;
						continue;
					}
				}
			}
			Collections.sort(c3);
		}
		return c3;
	}

	
	/**
	 * Combine deux chaines catégorielles entre elles renvoie une nouvelle
	 * chaine catégorielle. Seuls sont conservés les chaines lexicales dont la moyenne des qualités est suffisante
	 */
	public static ChaineCategorielle conc(ChaineCategorielle c1, ChaineCategorielle c2) {
		ChaineCategorielle c3 = new Categorie();
		for (int i = 0; i < Main.MAX_CAPACITY && i < c1.size(); i++) {
			for (int j = 0; j < Main.MAX_CAPACITY && j < c2.size(); j++) { 
				ChaineLexicale l1 = c1.get(i);
				ChaineLexicale l2 = c2.get(j);
				double noteL1 = l1.evaluate();
				double noteL2 = l2.evaluate();
				double moyenne = (noteL1+noteL2)/2;
				if(l1.estConcatenableAvec(l2)){
					ChaineLexicale l3 = l1.concatener(l2);
					//System.out.println("ici : "+l3);
					//pour passer les erreurs liees a la liste ordonne
					try{
						if(moyenne<Main.MIN_QUALITY){
							//System.out.println(l3);
							c3.add(l3);
						}
					}
					catch(java.lang.IndexOutOfBoundsException|java.lang.IllegalStateException e ){
						j++;
						continue;
					}
				}
			}
			Collections.sort(c3);
		}
		return c3;
	}


	public String toString() {
		Collections.sort(this);
		StringBuilder sb = new StringBuilder(this.size() * 25);
		int i = 0;
		for (ChaineLexicale m : this) {
			if(i >= Main.MAX_CAPACITY) break;
			//if(m.estUnAlexandrin()){
			//if(EvaluateurBegue.evaluate(m)<ChaineCategorielle.MIN_QUALITY){
				sb.append(m.toString());
			//}
			//}
			i++;
		}
		return sb.toString();
	}
	
	protected void addRandomly(ChaineLexicale s){
		int i = (int) (Math.random()*(this.size()+1));
		super.add(i, s);
	}
}
