package org.pneditor.petrinet.adapters.anas_adam;

import java.util.LinkedList;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.PetriNetwork;
import org.pneditor.petrinet.models.anas_adam.Place;
import org.pneditor.petrinet.models.anas_adam.Transition;

public class PetriNetAdapter extends PetriNetInterface {
    private PetriNetwork petriNetwork;

    public PetriNetAdapter() {
        this.petriNetwork = new PetriNetwork();
    }

    @Override
    public AbstractPlace addPlace() {
        // Implementation depends on how places are managed in PetriNetwork
        // Example:
    	AbstractPlace model_place = new PlaceAdapter("Place");// Assuming Place is a suitable class
    	Place place = new Place(0);
        this.petriNetwork.addPlace(place);
        return model_place; // or an adapter/wrapper of place
    }

    @Override
    public AbstractTransition addTransition() {
        // Similar to addPlace
        Transition transition = new Transition(new LinkedList<Arc>(),new LinkedList<Arc>()); // Assuming Transition is a suitable class
        AbstractTransition modal_transition = new TransitionAdapter("Transition");
        this.petriNetwork.addTransition(transition);
        return modal_transition; // or an adapter/wrapper of transition
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
        // Implementation depends on how arcs are managed in PetriNetwork
        // Example:
        if(source.getLabel()=="Place") {
        	AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) destination,  (PlaceAdapter) source, true);
        	this.petriNetwork.addArc(((TransitionAdapter) destination).getModelTransition(), ((PlaceAdapter) source).getModelPlace(), 1, true, false);
        	return modal_arc;
        }
        else  {
        	AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) source,  (PlaceAdapter) destination, true);
        	this.petriNetwork.addArc(((TransitionAdapter) source).getModelTransition(),((PlaceAdapter) destination).getModelPlace(), 1, false, false);
        	return modal_arc;
        	
        }
    }

    // Implement other methods like addInhibitoryArc, addResetArc, removePlace, etc.
    // based on the available methods in PetriNetwork and the specific requirements of these methods.

    @Override
    public void removePlace(AbstractPlace place) {
        // Implementation depends on how places are managed in PetriNetwork
        this.petriNetwork.removePlace(place.getModelPlace()); // Cast or convert as necessary
    }

    @Override
    public void removeTransition(AbstractTransition transition) {
        // Similar to removePlace
        this.petriNetwork.removeTransition(transition.getModelTransition()); // Cast or convert as necessary
    }

    @Override
    public void removeArc(AbstractArc arc) {
        // Implementation depends on how arcs are managed in PetriNetwork
        this.petriNetwork.removeArc(arc.getArc()); // Cast or convert as necessary
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
    	boolean enabled= true;
		for (Arc arc : transition.getModelTransition().getInputArcs()) {
			if(!arc.isActive()) {
				enabled= false;
			}
		}
		return enabled;
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
        
    	petriNetwork.stepAll();
    }

	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) transition,  (PlaceAdapter) place, false);
    	this.petriNetwork.addArcZero(((TransitionAdapter) transition).getModelTransition(),((PlaceAdapter) place).getModelPlace());
    	return modal_arc;
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) transition,  (PlaceAdapter) place, false);
    	this.petriNetwork.addArcVideur(((TransitionAdapter) transition).getModelTransition(),((PlaceAdapter) place).getModelPlace());
    	return modal_arc;
	}
}