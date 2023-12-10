package org.pneditor.petrinet.adapters.anas_adam;

import java.util.LinkedList;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.ArcVideur;
import org.pneditor.petrinet.models.anas_adam.ArcZero;
import org.pneditor.petrinet.models.anas_adam.Place;
import org.pneditor.petrinet.models.anas_adam.Transition;

public class ArcAdapter extends AbstractArc{
	private Arc model_arc;
	private Transition model_transition;
	private Place model_place;
	private AbstractNode transition;
	private AbstractNode place;
	private boolean to_transition;

	public ArcAdapter(int weight, TransitionAdapter transition, PlaceAdapter place, boolean isRegular, boolean to_transition){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		this.model_arc = new Arc(weight, model_transition, model_place, !isRegular);;
		this.to_transition = to_transition;

	}

	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, boolean isRegular, boolean to_transition){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		this.model_arc = new Arc(1, model_transition, model_place, !isRegular);;
		this.to_transition = to_transition;

	}

	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, String specialType){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		if(specialType == "Inhibitor") {
			this.model_arc = new ArcZero(model_transition, model_place, true);
		} else {
			this.model_arc = new ArcVideur(model_transition, model_place, true);
		}
		this.to_transition = true;

	}

	public boolean getDirection() {
		return this.to_transition;
	}


	public Arc getModelArc() {
		return this.model_arc;
	}

	@Override
	public AbstractNode getSource() {
		return this.to_transition? this.place: this.transition;
	}

	@Override
	public AbstractNode getDestination() {
		return this.to_transition? this.transition: this.place;
	}

	@Override
	public boolean isReset() {
		return model_arc instanceof ArcVideur;
	}

	@Override
	public boolean isRegular() {
		return !model_arc.isVideurOrZero();
	}

	@Override
	public boolean isInhibitory() {
		return model_arc instanceof ArcZero;
	}

	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return model_arc.getWeight();
	}

	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		model_arc.setWeight(multiplicity);
	}


}
