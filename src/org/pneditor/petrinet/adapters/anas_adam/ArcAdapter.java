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
	private Arc arc;
	private Transition transition;
	private Place place;
	private boolean to_transition;

	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, boolean isRegular){
		this.transition = transition.getModelTransition();
		this.place = place.getModelPlace();
		this.arc = new Arc(this.transition, this.place, !isRegular);
		LinkedList<Arc> input_arcs = this.transition.getInputArcs();
		if(input_arcs.contains(this.arc)){
			this.to_transition = true;
		} else {
			this.to_transition = false;
		}
		
	}
	@Override
	public  Arc getArc() {
		return this.arc;
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
		return arc instanceof ArcVideur;
	}

	@Override
	public boolean isRegular() {
		return !arc.isVideurOrZero();
	}

	@Override
	public boolean isInhibitory() {
		return arc instanceof ArcZero;
	}

	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return arc.getWeight();
	}

	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		arc.setWeight(multiplicity);
	}

}
