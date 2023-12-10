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
    	

    	AbstractPlace place = new PlaceAdapter("Place");// Assuming Place is a suitable class
    	
        this.petriNetwork.addPlace(((PlaceAdapter)place).getModelPlace());
        System.out.println(this.petriNetwork.getPlaceList().size()+"number of Places");
        return place; // or an adapter/wrapper of place
    }

    @Override
    public AbstractTransition addTransition() {
        // Similar to addPlace
        AbstractTransition transition = new TransitionAdapter("Transition");
        this.petriNetwork.addTransition(((TransitionAdapter)transition).getModelTransition());
        System.out.println(this.petriNetwork.getTransitionList().size()+"number of transitions");
        return transition; // or an adapter/wrapper of transition
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
    	// Implementation depends on how arcs are managed in PetriNetwork
    	// Example:

    	
    	PlaceAdapter p = source.getLabel()=="Place" ? ((PlaceAdapter) source) : ((PlaceAdapter) destination);
    	TransitionAdapter t = source.getLabel()=="Place" ? ((TransitionAdapter) destination) : ((TransitionAdapter) source);
    	boolean to_transition = source.getLabel()=="Place" ? true : false;


		ArcAdapter arc = new ArcAdapter(t, p, true, to_transition);
		if(!(this.isArcUnique(arc))) {
			throw new IllegalArgumentException("Arc is not unique:" + arc.getModelArc());
		}
		this.petriNetwork.addArc(t.getModelTransition(), p.getModelPlace(), 1, to_transition, false);
		
		System.out.println(t.getModelTransition().getInputArcs().size());
		System.out.println(this.petriNetwork.getArc().size()+"number of transitions");

		return arc;
 

    }
    
    
    public boolean isArcUnique(AbstractArc arc) {
    	return this.petriNetwork.isArcUnique(((ArcAdapter)arc).getModelArc());
//    	if(this.petriNetwork.isArcUnique(arc.getArc())) {
//    		System.out.println("BBBBBBBBBBBBBBBBBB");
//    		return true;
//    	}
//    	
//    	for(Transition T: this.petriNetwork.getTransitionList()) {
//    		if ((T.getInputArcs().contains(arc.getArc()) && ((ArcAdapter)arc).getDirection())) {
//    			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC 1"+ ((ArcAdapter)arc).getDirection());
//    			return false;
//    		}
//    		if (T.getOutputArcs().contains(arc.getArc()) && !((ArcAdapter)arc).getDirection()){
//    			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC 2"+ ((ArcAdapter)arc).getDirection());
//    			return false;
//    		}
//    	}
//    	System.out.println("AAAAAAAAAAAAAAAAA");
//    	return true;
    }

    // Implement other methods like addInhibitoryArc, addResetArc, removePlace, etc.
    // based on the available methods in PetriNetwork and the specific requirements of these methods.

    @Override
    public void removePlace(AbstractPlace place) {
        // Implementation depends on how places are managed in PetriNetwork
        this.petriNetwork.removePlace(((PlaceAdapter)place).getModelPlace()); // Cast or convert as necessary
    }

    @Override
    public void removeTransition(AbstractTransition transition) {
        // Similar to removePlace
        this.petriNetwork.removeTransition(((TransitionAdapter)transition).getModelTransition()); // Cast or convert as necessary
    }

    @Override
    public void removeArc(AbstractArc arc) {
        // Implementation depends on how arcs are managed in PetriNetwork
        this.petriNetwork.removeArc(((ArcAdapter)arc).getModelArc()); // Cast or convert as necessary
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
    	boolean enabled= true;
    	System.out.println(((TransitionAdapter)transition).getModelTransition().getInputArcs().size());
    	if (((TransitionAdapter)transition).getModelTransition().getInputArcs().size()==0) {
    		return false;
    	}
		for (Arc arc : ((TransitionAdapter)transition).getModelTransition().getInputArcs()) {
			if(!arc.isActive()) {
				enabled= false;
			}
		}
		System.out.println(enabled);
		return enabled;
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
    	this.petriNetwork.step(((TransitionAdapter)transition).getModelTransition());
    }

	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) transition,  (PlaceAdapter) place, false, true);
    	this.petriNetwork.addArcZero(((TransitionAdapter) transition).getModelTransition(),((PlaceAdapter) place).getModelPlace());
    	return modal_arc;
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc modal_arc = new ArcAdapter((TransitionAdapter) transition,  (PlaceAdapter) place, false, true);
    	this.petriNetwork.addArcVideur(((TransitionAdapter) transition).getModelTransition(),((PlaceAdapter) place).getModelPlace());
    	return modal_arc;
	}
}