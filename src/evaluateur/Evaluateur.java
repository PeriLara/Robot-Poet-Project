package evaluateur;

import lexique.ChaineLexicale;

/** Interface de toute évaluation de contrainte formelle sur une chaine lexicale */
public interface Evaluateur {

	/**
	 * Evalue la qualite d'une chaine lexicale sous forme de note.
	 * @param c la chaine lexiacle à evaluer
	 * @return le resultat de l'evaluation, typiquement un nombre compris entre 0 et 1.
	 */
	public abstract double evaluate(ChaineLexicale c);
	
}
