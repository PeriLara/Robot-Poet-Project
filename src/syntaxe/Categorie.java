package syntaxe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import phonetiseur.RegExp;
import lexique.Dossier;
import lexique.ChaineLexicale;
import lexique.SynthetiseurVocal;

/**Correspond à une catégorie syntaxique, chaque catégorie étant reliée à un fichier lexical.*/

@SuppressWarnings("serial")
public class Categorie extends ChaineCategorielle {

	public Categorie() {
		super();
	}

	/**
	 * Construit une Catégorie à partir du nom d'un fichier contenant les lexemes OU une "catégorie" contenant un mot unique (a condition d'être sur linux et d'avoir espeaks espeaks)
	 * @param nom nom du fichier à partir duquel la catégorie est crée 
	 * @param fichier si le boolen est faux, une catégorie est crée contenant comme unique element le parametre String
	 * @throws IOException
	 */
	public Categorie(String nom, boolean fichier) throws IOException {
		super();
		if(fichier){
			File fic = new File(Dossier.PATH + "Categories/" + nom);
			FileReader in = new FileReader(fic);
			BufferedReader br = new BufferedReader(in);
			String readLine = "";
			while ((readLine = br.readLine()) != null) {
				ChaineLexicale m = new ChaineLexicale(readLine);
				this.addRandomly(m);
			}
			in.close();
		}
		else{
			//le contexte n'est pas pris en compte
			String prononciation = SynthetiseurVocal.synthese(nom);
			this.add(new ChaineLexicale(nom, prononciation, RegExp.regExPhone));
		}
	}

}
