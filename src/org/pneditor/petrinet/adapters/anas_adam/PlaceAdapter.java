package org.pneditor.petrinet.adapters.anas_adam;

public class PlaceAdapter {

	public PlaceAdapter() {
		
	}
	
	public void addToken() {
		this.setTokens(this.getTokens() + 1);
	}

	public void removeToken() {
		removeTokenNbre(1);
	}

	public int getTokens() {
		return getTokenNbre();
	}

	public void setTokens(int tokens) {
		setTokenNbre(tokens)
	}
	
	public boolean isPlace() {
		return true;
	}

}
