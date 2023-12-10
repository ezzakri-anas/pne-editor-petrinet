package org.pneditor.petrinet.adapters.anas_adam;



import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.Transition;

public class TransitionAdapter extends AbstractTransition{
	private Transition model_transition;

	public TransitionAdapter(final String label,Transition T) {
		super(label);
		this.model_transition = T;
	}
	@Override
	public Transition getModelTransition() {
		return this.model_transition;
	}
	@Override
	public boolean isPlace() {
		boolean enabled= true;
		for (Arc arc : this.model_transition.getInputArcs()) {
			if(!arc.isActive()) {
				enabled = false;
			}
		}
		return enabled;
	}

}
