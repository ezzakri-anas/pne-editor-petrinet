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
	private AbstractNode source;
	private AbstractNode destination;

	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, boolean isRegular){
		Transition t = transition.getModelTransition();
		Place p = place.getModelPlace();
		this.arc = new Arc(t, p, !isRegular);
		LinkedList<Arc> input_arcs = t.getInputArcs();
		if(input_arcs.contains(this.arc)){
			this.source = this.arc.getPlace();
			this.destination = t;
		} else {
			this.source = t;
			this.destination = this.arc.getPlace();
		}
		
	}
	@Override
	public  Arc getArc() {
		return this.arc;
	}
	@Override
	public AbstractNode getSource() {
		return this.source;
	}

	@Override
	public AbstractNode getDestination() {
		return this.destination;
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
