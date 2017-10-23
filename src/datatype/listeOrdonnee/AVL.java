package datatype.listeOrdonnee;

import datatype.binaryTree.*;

/**
 * Classe AVL, fichier AVL.java Créer un objet AVL, arbre binaire de recherche
 * équilibré ajoute des éléments comparables à l'intérieur recherche rapidement
 * grâce à la structure ces éléments
 */



public class AVL<E extends Comparable<E>> extends BinaryTree<E> {

	private int desequilibre; // différence de déséquilibre entre ses deux fils,
								// compris entre -2 et 2
	private E pere;

	// Constructeurs
	/** Consruit un arbre d'étiquette label avec 2 sous arbres. */
	public AVL(E label, AVL<E> left, AVL<E> right) {
		super(label, left, right);
		this.desequilibre = left.getHeight() - right.getHeight();
		this.pere = label; // racine
	}

	/** Construit un AVL vide */
	public AVL() {
		super();
		this.desequilibre = 0;
		this.pere = label; // racine
	}

	/** Consruit une feuille d'étiquette label. */
	public AVL(E label) {
		super(label);
		this.desequilibre = 0;
		this.pere = label; // racine
	}

	// ------------- FONCTIONS UTILES -------------

	/** Renvoie True si l'arbre est vide, False sinon */
	public boolean isEmpty() {
		return left == null && right == null && label == null;
	}

	/** Calcul le déséquilibre pour une nœud donné */
	private void calculDesequilibre() {
		// this
		if (this.left == null && this.right == null)
			this.desequilibre = 0;
		else if (this.left == null && this.right != null)
			this.desequilibre = -1;
		else if (this.left != null && this.right == null)
			this.desequilibre = 1;
		else
			this.desequilibre = this.left.getHeight() - this.right.getHeight();
		// this.left
		// ((AVL<E>)this.left).calculDesequilibre();
		// this.right
		// ((AVL<E>)this.right).calculDesequilibre();
	}

	public void printAVL() {
		System.out.println("label " + this.label + " \n" + "deseq "
				+ this.desequilibre);
		if (this.left != null)
			((AVL<E>) this.left).printAVL();
		if (this.right != null)
			((AVL<E>) this.right).printAVL();

	}

	// ----------------------- INSERTION -----------------------
	/**
	 * Première fonction add Cherche les erreurs et le cas où l'arbre est vide
	 * Renvoie vers la fonction addrecursif() sinon
	 * 
	 * @param elt
	 *            , l'élément à ajouter
	 */
	public void add(E elt) {
		if (elt == null)
			throw new IllegalArgumentException(
					"On ne peut ajouter d'élément nul");
		if (this.label == null) {
			this.label = elt;
			this.left = new AVL<E>();
			this.right = new AVL<E>();
			this.desequilibre = 0;
			if (pere == null)
				this.pere = this.label;
			else
				this.pere = pere;
		} else
			add(elt, this.pere);

	}

	private void add(E elt, E pere) {
		if (this.label.compareTo(elt) < 0) {// ajout à droite
			// this.desequilibre--;
			this.right = new AVL<E>();
			((AVL<E>) this.right).add(elt, this.label);
		} else if (this.label.compareTo(elt) > 0) {
			// this.desequilibre++;
			this.left = new AVL<E>();
			((AVL<E>) this.left).add(elt, this.label);
		}
		this.reequilibrer();
	}

	/**
	 * Rééquilibrage utilise les fonctions de rotation Complexité : O(log n)
	 */
	private void reequilibrer() {
		// System.out.println(this.label+"  "+this.desequilibre);
		switch (this.desequilibre) {
		case -2:
			int leftdeseq = ((AVL<E>) this.left).desequilibre;
			// System.out.println("ld"+leftdeseq);
			if (leftdeseq == -1) {
				System.out.println("birbej");
				this.rotGauche();
			} else if (leftdeseq == 1)
				this.doublerotDroite();
		case 2:
			int rightdeseq = ((AVL<E>) this.right).desequilibre;
			// System.out.println("rd"+rightdeseq);
			if (rightdeseq == 1)
				this.rotGauche();
			else if (rightdeseq == -1)
				this.doublerotGauche();
		case 1:
			break;
		case 0:
			break;
		case -1:
			break;
		default:
			System.out
					.println("Problème au niveau du déséquilibre du noeud racine");
		}
	}

	// ----------------------- ROTATIONS -----------------------
	private void rotGauche() {
		this.left = new AVL<E>(this.label, (AVL<E>) this.getLeft(),
				(AVL<E>) this.getRight().getLeft());
		this.label = this.right.getLabel();
		this.right = this.right.getRight();
		this.setHeight(1 + java.lang.Math.max(this.getLeft().getHeight(), this
				.getRight().getHeight()));
		this.calculDesequilibre();
		((AVL<E>) this.pere).calculDesequilibre();
		((AVL<E>) this.right).calculDesequilibre();
	}

	private void rotDroite() {
		this.right = new AVL<E>(this.label, (AVL<E>) this.getLeft().getRight(),
				(AVL<E>) this.getRight());
		this.label = this.left.getLabel();
		this.left = this.left.getLeft();
		this.setHeight(1 + java.lang.Math.max(this.getLeft().getHeight(), this
				.getRight().getHeight()));
		this.calculDesequilibre();
		((AVL<E>) this.pere).calculDesequilibre();
		((AVL<E>) this.left).calculDesequilibre();
	}

	private void doublerotGauche() {
		((AVL<E>) this.getRight()).rotDroite();
		this.rotGauche();
	}

	private void doublerotDroite() {
		((AVL<E>) this.getLeft()).rotGauche();
		this.rotDroite();
	}

	// ----------------------- RECHERCHE -----------------------

	public boolean recherche(E elt) {
		E bfr = this.label;
		while (!this.isEmpty()) {
			int comp = elt.compareTo(bfr);
			if (comp < 0) {
				return ((AVL<E>) this.getLeft()).recherche(elt);
			} else if (comp > 0) {
				return ((AVL<E>) this.getRight()).recherche(elt);
			} else {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		AVL<Integer> a = new AVL<Integer>(2);
		System.out.println("add 2");
		a.printAVL();
		// boolean b = a.recherche(2);
		// Test recherche
		// System.out.println(b);
		// Test add
		a.add(4);
		System.out.println("add 4");
		a.printAVL();
		System.out.println();
		a.add(1, null);
		System.out.println("add 1");
		a.printAVL();
		System.out.println();
		a.add(7, null);
		System.out.println("add 7");
		a.printAVL();
		System.out.println();
		a.add(10, null);
		System.out.println("add 10");
		a.printAVL();
		System.out.println();

		for (Integer i : a) {
			System.out.print(i + ",");
		}
	}

}
