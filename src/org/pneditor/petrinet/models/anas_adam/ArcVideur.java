package org.pneditor.petrinet.models.anas_adam;

/**
 * Classe ArcVideur, une sous-classe de Arc, représentant un type spécial d'arc dans un réseau de Petri.
 * Ce type d'arc vide entièrement la place de ses jetons lorsqu'il est activé.
 */
public class ArcVideur extends Arc{

	/**
	 * Constructeur de la classe ArcVideur. Initialise l'ArcVideur avec une transition et une place.
	 * Le paramètre isVideurOrZero est utilisé pour indiquer que cet arc est un arc spécial.
	 * @param transition Transition associée à l'ArcVideur.
	 * @param place Place associée à l'ArcVideur.
	 * @param isVideurOrZero Indique si l'arc est un ArcVideur.
	 */
	public ArcVideur(Transition transition, Place place, boolean isVideurOrZero) {
		super(transition, place, isVideurOrZero);
	}

	/**
	 * Méthode isActive surchargée pour toujours retourner vrai.
	 * Cela indique que l'ArcVideur est toujours actif, quel que soit le nombre de jetons dans la place.
	 * @return Toujours vrai pour un ArcVideur.
	 */
	@Override
	public boolean isActive() {
		return true;
	}

	/**
	 * Méthode fire surchargée pour vider entièrement la place de ses jetons lorsque l'arc est activé.
	 * Réinitialise le nombre de jetons de la place à 0.
	 */
	@Override
	public void fire() {
		Place p = this.getPlace();
		p.setTokenNbre(0);
	}
}
