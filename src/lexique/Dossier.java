package lexique;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/** Représente le moyen d'accès du programme vers le dossier contenant les ressources lexicales. */
public class Dossier {
	
	/** Chemin d'accès de la racine du projet au dossier "lexique". */
	public final static String PATH = "./datas/lexique/";
	
	private String path;
	
	/**
	 * Constructeur à partir du dossier lexique.
	 * @param path : le chemin relatif depuis le dossier lexique.
	 */
	public Dossier(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	/**
	 * Liste les fichiers contenus dans ce dosser 
	 * @return liste des fichiers contenus dans ce dosser
	 */
	public ArrayList<String> listFichiers(){
		File repertoire = new File(PATH+this.path);
		File[] fichiers = repertoire.listFiles();
		ArrayList<String> retour = new ArrayList<String>(fichiers.length);
		for(File f : fichiers){
			retour.add(f.getName());
		}
		return retour;
	}
	
	static void main(String[] args) throws IOException{
		/*File directory = new File("./Categories");
		String path = directory.getAbsolutePath().toString();*/
		String path = PATH+"NonFormates/";
		Dossier d = new Dossier(path);
		ArrayList<String> s = d.listFichiers();
		for(String i : s){
			if(!Pattern.matches(".*\\.cat",i)){
				System.out.println("formatage fichier "+i);
				FormateurLexical.formaterFichier(i);
			}
		}
		System.out.println("fin");
	}
	
}
