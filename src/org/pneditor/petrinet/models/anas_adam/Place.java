 package org.pneditor.petrinet.models.anas_adam;

/**
 * Classe représentant une place dans un réseau de Petri, caractérisée par un nombre de jetons.
 */
public class Place {

	private int tokenNbre;

	/**
	 * Constructeur de la classe Place. Initialise la place avec un nombre de jetons donné.
	 * Si le nombre de jetons est inférieur à 1, il est initialisé à 0 pour éviter les nombres négatifs.
	 * @param tokenNbre Nombre initial de jetons dans la place.
	 */
	public Place(int tokenNbre) {
		this.tokenNbre = tokenNbre < 1 ? 0 : tokenNbre;
	}

	/**
	 * Obtient le nombre de jetons actuellement dans la place.
	 * @return Le nombre de jetons dans la place.
	 */
	public int getTokenNbre() {
		return this.tokenNbre;
	}

	/**
	 * Définit le nombre de jetons dans la place. 
	 * Si le nombre est inférieur à 1, il est fixé à 0.
	 * @param tokens Nouveau nombre de jetons pour la place.
	 */
	public void setTokenNbre(int tokens){
		this.tokenNbre = tokens < 1 ? 0 : tokens;
	}

	/**
	 * Réduit le nombre de jetons dans la place par une quantité spécifiée.
	 * Si la quantité à retirer est supérieure au nombre de jetons existants, tous les jetons sont retirés.
	 * @param to_remove Nombre de jetons à retirer.
	 */
	public void removeTokenNbre(int to_remove) {
		this.tokenNbre -= to_remove <= this.getTokenNbre() ? to_remove : this.getTokenNbre();
	}
}
