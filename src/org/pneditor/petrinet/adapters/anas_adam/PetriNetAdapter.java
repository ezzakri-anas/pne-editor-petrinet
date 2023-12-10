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
        Transition model_transition = new Transition(new LinkedList<Arc>(),new LinkedList<Arc>()); // Assuming Transition is a suitable class
        AbstractTransition transition = new TransitionAdapter("Transition", model_transition);
        this.petriNetwork.addTransition(model_transition);
        System.out.println(this.petriNetwork.getTransitionList().size()+"number of transitions");
        return transition; // or an adapter/wrapper of transition
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
    	// Implementation depends on how arcs are managed in PetriNetwork
    	// Example:
    	System.out.println(this.petriNetwork.showPetriNet());
    	
    	PlaceAdapter p = source.getLabel()=="Place" ? ((PlaceAdapter) source) : ((PlaceAdapter) destination);
    	TransitionAdapter t = source.getLabel()=="Place" ? ((TransitionAdapter) destination) : ((TransitionAdapter) source);
    	boolean to_transition = source.getLabel()=="Place" ? true : false;

		System.out.println("hello there");

		//addArc(Transition transition, Place place, int weight, boolean entrsort, boolean isZeroorVideur)
		Arc model_arc = new Arc(1, t.getModelTransition(), p.getModelPlace(), false);
		ArcAdapter arc = new ArcAdapter(model_arc, t, p, true);
		if(!(this.isArcUnique(arc))) {
			throw new IllegalArgumentException("Arc is not unique:" + model_arc);
		}
		this.petriNetwork.addArc(t.getModelTransition(), p.getModelPlace(), 1, to_transition, false);
		
		System.out.println(t.getModelTransition().getInputArcs().size());
		System.out.println(this.petriNetwork.getArc().size()+"number of transitions");

		return arc;
 

    }
    
    
    public boolean isArcUnique(AbstractArc arc) {
    	if(this.petriNetwork.isArcUnique(arc.getArc())) {
    		System.out.println("BBBBBBBBBBBBBBBBBB");
    		return true;
    	}
    	
    	for(Transition T: this.petriNetwork.getTransitionList()) {
    		if ((T.getInputArcs().contains(arc.getArc()) && ((ArcAdapter)arc).getDirection())) {
    			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC 1"+ ((ArcAdapter)arc).getDirection());
    			return false;
    		}
    		if (T.getOutputArcs().contains(arc.getArc()) && !((ArcAdapter)arc).getDirection()){
    			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC 2"+ ((ArcAdapter)arc).getDirection());
    			return false;
    		}
    	}
    	System.out.println("AAAAAAAAAAAAAAAAA");
    	return true;
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