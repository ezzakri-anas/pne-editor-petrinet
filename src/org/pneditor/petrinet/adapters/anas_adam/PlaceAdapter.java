package org.pneditor.petrinet.adapters.anas_adam;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.anas_adam.Place;

/**
 * Cette classe sert d'adaptateur entre l'interface graphique de la place
 * (AbstractPlace) et le modèle sous-jacent (Place).
 */
public class PlaceAdapter extends AbstractPlace{
	private Place modelPlace;

	/**
	 * Constructeur de la classe PlaceAdapter.
	 * @param label Le label de la place.
	 */
	public PlaceAdapter(final String label) {
		super(label);
		this.modelPlace = new Place(0);
	}

	/**
	 * Obtient le modèle de place sous-jacent.
	 * @return Le modèle de place.
	 */
	public Place getModelPlace() {
		return this.modelPlace;
	}

	/**
	 * Ajoute un jeton à la place.
	 */
	@Override
	public void addToken() {
		this.setTokens(this.getTokens() + 1);
	}

	/**
	 * Supprime un jeton de la place.
	 */
	@Override
	public void removeToken() {
		this.modelPlace.removeTokenNbre(1);
	}

	/**
	 * Obtient le nombre de jetons dans la place.
	 * @return Le nombre de jetons dans la place.
	 */
	@Override
	public int getTokens() {
		return this.modelPlace.getTokenNbre();
	}

	/**
	 * Définit le nombre de jetons dans la place.
	 * @param tokens Le nouveau nombre de jetons dans la place.
	 */
	@Override
	public void setTokens(int tokens) {
		this.modelPlace.setTokenNbre(tokens);
	}

	/**
	 * Vérifie si l'objet est une place.
	 * @return True si l'objet est une place, sinon False.
	 */
	@Override
	public boolean isPlace() {
		return true;
	}

}
