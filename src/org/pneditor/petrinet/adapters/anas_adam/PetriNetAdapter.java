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
    	
    	Place place = new Place(0);
    	AbstractPlace model_place = new PlaceAdapter("Place",place);// Assuming Place is a suitable class
    	
        this.petriNetwork.addPlace(place);
        System.out.println(this.petriNetwork.getPlaceList().size()+"number of Places");
        return model_place; // or an adapter/wrapper of place
    }

    @Override
    public AbstractTransition addTransition() {
        // Similar to addPlace
        Transition transition = new Transition(new LinkedList<Arc>(),new LinkedList<Arc>()); // Assuming Transition is a suitable class
        AbstractTransition modal_transition = new TransitionAdapter("Transition",transition);
        this.petriNetwork.addTransition(transition);
        System.out.println(this.petriNetwork.getTransitionList().size()+"number of transitions");
        return modal_transition; // or an adapter/wrapper of transition
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
        // Implementation depends on how arcs are managed in PetriNetwork
        // Example:
        
    	if(source.getLabel()=="Place") {
    
    	System.out.println("hello there");
    	AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) destination,  (PlaceAdapter) source, true);
    	//addArc(Transition transition, Place place, int weight, boolean entrsort, boolean isZeroorVideur)
    	this.petriNetwork.addArc(((TransitionAdapter) destination).getModelTransition(), ((PlaceAdapter) source).getModelPlace(), 1, true, false);
    	System.out.println(((TransitionAdapter) destination).getModelTransition().getInputArcs().size());
    	System.out.println(this.petriNetwork.getArc().size()+"number of transitions");
    	
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
    	System.out.println(transition.getModelTransition().getInputArcs().size());
    	if (transition.getModelTransition().getInputArcs().size()==0) {
    		return false;
    	}
		for (Arc arc : transition.getModelTransition().getInputArcs()) {
			if(!arc.isActive()) {
				enabled= false;
			}
		}
		System.out.println(enabled);
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