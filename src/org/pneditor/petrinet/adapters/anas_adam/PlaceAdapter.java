package org.pneditor.petrinet.adapters.anas_adam;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.anas_adam.Place;

public class PlaceAdapter extends AbstractPlace{
	private Place model_place;

	public PlaceAdapter(final String label) {
		super(label);
		this.model_place = new Place(0);
	}
	
	
	public Place getModelPlace() {
		return this.model_place;
	}
	
	@Override
	public void addToken() {
		this.setTokens(this.getTokens() + 1);
	}

	@Override
	public void removeToken() {
		this.model_place.removeTokenNbre(1);
	}

	@Override
	public int getTokens() {
		return this.model_place.getTokenNbre();
	}
	
	@Override
	public void setTokens(int tokens) {
		this.model_place.setTokenNbre(tokens);
	}
	
	@Override
	public boolean isPlace() {
		return true;
	}

}
