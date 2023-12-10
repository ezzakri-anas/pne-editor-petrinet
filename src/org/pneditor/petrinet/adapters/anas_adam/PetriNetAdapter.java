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
    	AbstractPlace place = new PlaceAdapter("Place");
    	
        this.petriNetwork.addPlace(((PlaceAdapter)place).getModelPlace());
        return place;
    }

    @Override
    public AbstractTransition addTransition() {
        AbstractTransition transition = new TransitionAdapter("Transition");
        this.petriNetwork.addTransition(((TransitionAdapter)transition).getModelTransition());
        return transition;
    }

    @Override
    public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
    	
    	PlaceAdapter p = source.getLabel()=="Place" ? ((PlaceAdapter) source) : ((PlaceAdapter) destination);
    	TransitionAdapter t = source.getLabel()=="Place" ? ((TransitionAdapter) destination) : ((TransitionAdapter) source);
    	boolean to_transition = source.getLabel()=="Place" ? true : false;

		ArcAdapter arc = new ArcAdapter(t, p, true, to_transition);
		if(!(this.isArcUnique(arc))) {
			throw new IllegalArgumentException("Arc is not unique:" + arc.getModelArc());
		}
		this.petriNetwork.addArc(arc.getModelArc(), to_transition);

		return arc;
    }
    
    
    public boolean isArcUnique(AbstractArc arc) {
    	return this.petriNetwork.isArcUnique(((ArcAdapter)arc).getModelArc());
    }

    // Implement other methods like addInhibitoryArc, addResetArc, removePlace, etc.
    // based on the available methods in PetriNetwork and the specific requirements of these methods.

	/**
	 * Supprime une place spécifique du réseau.
	 * @param place Place à supprimer.
	 */
    @Override
    public void removePlace(AbstractPlace place) {
        LinkedList<Place> places_list = this.petriNetwork.getPlaceList();
        places_list.removeIf(p -> p.equals(((PlaceAdapter)place).getModelPlace()));
        this.petriNetwork.setPlaceList(places_list);
    }

	/**
	 * Supprime une transition spécifique du réseau.
	 * @param transition Transition à supprimer.
	 */
    @Override
    public void removeTransition(AbstractTransition transition) {
        LinkedList<Transition> transitions_list = this.petriNetwork.getTransitionList();
        transitions_list.removeIf(p -> p.equals(((TransitionAdapter)transition).getModelTransition()));
        this.petriNetwork.setTransitionList(transitions_list);
    }

	/**
	 * supprime l'arc donné
	 * @throws Exception 
	 */
    @Override
    public void removeArc(AbstractArc arc) {
    	Arc model_arc = ((ArcAdapter)arc).getModelArc();
    	
        LinkedList<Arc> arcs_list = this.petriNetwork.getArcList();
        arcs_list.removeIf(p -> p.equals(model_arc));
        this.petriNetwork.setArcList(arcs_list);
        
		for(Transition T: this.petriNetwork.getTransitionList()) {
			LinkedList<Arc> outputs = T.getOutputArcs();
			LinkedList<Arc> inputs = T.getInputArcs();
			outputs.removeIf(a -> a.equals(model_arc));
			inputs.removeIf(a -> a.equals(model_arc));
			T.setOutputArcs(outputs);
			T.setInputArcs(inputs);
		}
        
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
    	boolean enabled= true;
    	
    	if (((TransitionAdapter)transition).getModelTransition().getInputArcs().size()==0) {
    		return false;
    	}
		for (Arc arc : ((TransitionAdapter)transition).getModelTransition().getInputArcs()) {
			if(!arc.isActive()) {
				enabled= false;
			}
		}
		return enabled;
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
    	this.petriNetwork.step(((TransitionAdapter)transition).getModelTransition());
    }

	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc arc = new ArcAdapter((TransitionAdapter) transition, (PlaceAdapter) place, "Inhibitor");
    	this.petriNetwork.addArcZero(((ArcAdapter)arc).getModelArc());
    	return arc;
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc arc = new ArcAdapter((TransitionAdapter) transition, (PlaceAdapter) place, "Reset");
    	this.petriNetwork.addArcVideur(((ArcAdapter)arc).getModelArc());
    	return arc;
	}
}