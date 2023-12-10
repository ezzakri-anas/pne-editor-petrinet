package org.pneditor.petrinet.adapters.anas_adam;

import java.util.LinkedList;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
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

	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, boolean isRegular){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		this.model_arc = new Arc(this.model_transition, this.model_place, !isRegular);
		LinkedList<Arc> input_arcs = this.model_transition.getInputArcs();
		if(input_arcs.contains(this.model_arc)){
			this.to_transition = true;
		} else {
			this.to_transition = false;
		}
		
	}
	@Override
	public  Arc getArc() {
		return this.model_arc;
	}
	@Override
	public AbstractNode getSource() {
		return this.to_transition? this.transition: this.place;
	}

	@Override
	public AbstractNode getDestination() {
		return this.to_transition? this.place: this.transition;
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
