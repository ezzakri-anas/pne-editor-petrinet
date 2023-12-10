package org.pneditor.petrinet.adapters.anas_adam;



import java.util.LinkedList;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.Transition;

/**
 * Cette classe sert d'adaptateur entre l'interface graphique de la transition
 * (AbstractTransition) et le modèle sous-jacent (Transition).
 */
public class TransitionAdapter extends AbstractTransition{
	private Transition model_transition;
	
    /**
     * Constructeur de la classe TransitionAdapter.
     * @param label Le label de la transition.
     */
	public TransitionAdapter(final String label) {
		super(label);
		this.model_transition = new Transition(new LinkedList<Arc>(),new LinkedList<Arc>());
	}

    /**
     * Obtient le modèle de transition sous-jacent.
     * @return Le modèle de transition.
     */
	public Transition getModelTransition() {
		return this.model_transition;
	}
	
    /**
     * Vérifie si ce noeud est une place dans le réseau de Petri.
     * @return false.
     */
	@Override
	public boolean isPlace() {
		return false;
	}

}
