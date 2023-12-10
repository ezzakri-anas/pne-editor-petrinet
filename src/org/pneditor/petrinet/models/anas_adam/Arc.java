package org.pneditor.petrinet.models.anas_adam;

/**
 * Classe représentant un arc dans un réseau de Petri, liant une place à une transition.
 */
public class Arc {
	private int weight;
	private Place place;
	private Transition transition;
	private boolean isVideurOrZero;

	/**
	 * Constructeur principal de la classe Arc. Initialise l'arc avec un weight spécifié, une place, une transition et un marqueur pour indiquer s'il s'agit d'un arc spécial.
	 * @param weight Poids de l'arc.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 * @param isVideurOrZero Booléen indiquant si l'arc est un arc spécial (Videur ou Zero).
	 */
	public Arc(int weight, Transition transition, Place place, boolean isVideurOrZero) {
		this.weight = Math.max(weight, 1);
		this.place = place;
		this.transition = transition;
		this.isVideurOrZero = isVideurOrZero;
	}

	/**
	 * Constructeur surchargé pour créer un arc avec un weight par défaut de 1.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 * @param isVideurOrZero Booléen indiquant si l'arc est un arc spécial (Videur ou Zero).
	 */
	public Arc(Transition transition, Place place, boolean isVideurOrZero) {
		this.weight = 1;
		this.place = place;
		this.transition = transition;
		this.isVideurOrZero = isVideurOrZero;
	}

	/**
	 * Retourne le weight de cet arc.
	 * @return Le weight de l'arc.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Définit le weight de cet arc. Si la valeur donnée est inférieure à 1, le weight est fixé à 1.
	 * @param weight Nouveau weight de l'arc.
	 */
	public void setWeight(int weight) {
		this.weight = Math.max(weight, 1);
	}

	/**
	 * Retourne la place associée à cet arc.
	 * @return La place associée.
	 */
	public Place getPlace() {
		return this.place;
	}

	/**
	 * Définit la place associée à cet arc.
	 * @param place Nouvelle place à associer à l'arc.
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * Retourne la transition associée à cet arc.
	 * @return La transition associée.
	 */
	public Transition getTransition() {
		return this.transition;
	}

	/**
	 * Définit la transition associée à cet arc.
	 * @param transition Nouvelle transition à associer à l'arc.
	 */
	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	/**
	 * Retourne un booléen indiquant si cet arc est un arc spécial (Videur ou Zero).
	 * @return Booléen indiquant si l'arc est un arc spécial.
	 */
	public boolean isVideurOrZero() {
		return this.isVideurOrZero;
	}

	/**
	 * Définit si cet arc est un arc spécial (Videur ou Zero).
	 * @param isVideurOrZero Booléen pour définir si l'arc est un arc spécial.
	 */

	public void setVideurOrZero(boolean isVideurOrZero) {
		this.isVideurOrZero = isVideurOrZero;
	}
	/**
	 * Ajoute des jetons à la place associée à cet arc, selon le weight de l'arc.
	 */
	public void addToken() {
		place.setTokenNbre(place.getTokenNbre() + weight);
	}

	/**
	 * Retire des jetons de la place associée à cet arc, selon le weight de l'arc.
	 */
	public void removeTokens() {
		place.removeTokenNbre(weight);
	}

	/**
	 * Vérifie si l'arc est actif, c'est-à-dire si la place associée a suffisamment de jetons selon le weight de l'arc.
	 * @return True si l'arc est actif, sinon false.
	 */
	public boolean isActive() {
		Place p = this.getPlace();
		return p.getTokenNbre() >= this.getWeight();
	}
	/*
	 *  Méthode pour effectuer l'action de l'arc (à définir dans les sous-classes).
	 */

	public void fire() {}
	
	@Override
	public boolean equals(Object obj) {
		
		if((this.place.equals(((Arc)obj).getPlace()))&&(this.transition.equals(((Arc)obj).getTransition()))) {
			return true;
		}
		return false;
		
	}

}
