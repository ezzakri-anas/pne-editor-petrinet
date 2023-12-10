package org.pneditor.petrinet.adapters.anas_adam;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.PetriNetwork;


/**
 * Cette classe sert d'adaptateur entre l'interface graphique de l'éditeur de réseau de Petri
 * (PetriNetInterface) et le modèle sous-jacent (PetriNetwork).
 */
public class PetriNetAdapter extends PetriNetInterface {
	private PetriNetwork petriNetwork;

	/**
	 * Constructeur de la classe PetriNetAdapter. Initialise le réseau de Petri sous-jacent.
	 */
	public PetriNetAdapter() {
		this.petriNetwork = new PetriNetwork();
	}

	/**
	 * Ajoute une place au réseau de Petri.
	 * @return La place ajoutée.
	 */
	@Override
	public AbstractPlace addPlace() {
		AbstractPlace place = new PlaceAdapter("Place");

		this.petriNetwork.addPlace(((PlaceAdapter)place).getModelPlace());
		return place;
	}

	/**
	 * Ajoute une transition au réseau de Petri.
	 * @return La transition ajoutée.
	 */
	@Override
	public AbstractTransition addTransition() {
		AbstractTransition transition = new TransitionAdapter("Transition");
		this.petriNetwork.addTransition(((TransitionAdapter)transition).getModelTransition());
		return transition;
	}

	/**
	 * Ajoute un arc régulier reliant deux nœuds du réseau de Petri.
	 * @param source Le nœud source de l'arc.
	 * @param destination Le nœud destination de l'arc.
	 * @return L'arc ajouté.
	 * @throws UnimplementedCaseException Si l'arc n'est pas unique.
	 */
	@Override
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {

		// Determine la place (p) et la transition (t) en fonction des labels des nœuds source et destination.
		PlaceAdapter p = source.getLabel()=="Place" ? ((PlaceAdapter) source) : ((PlaceAdapter) destination);
		TransitionAdapter t = source.getLabel()=="Place" ? ((TransitionAdapter) destination) : ((TransitionAdapter) source);

		// Determine si l'arc va vers la transition ou non.
		boolean toTransition = source.getLabel()=="Place" ? true : false;


		ArcAdapter arc = new ArcAdapter(t, p, true, toTransition);

		// Vérifie si l'arc est unique, sinon lance une exception.
		if(!(this.isArcUnique(arc))) {
			throw new IllegalArgumentException("Arc is not unique:" + arc.getModelArc());
		}

		// Ajoute l'arc au réseau de Petri sous-jacent.
		this.petriNetwork.addArc(arc.getModelArc(), toTransition);

		return arc;
	}

	/**
	 * Vérifie si l'arc est unique dans le réseau de Petri.
	 * @param arc L'arc à vérifier.
	 * @return True si l'arc est unique, sinon False.
	 */
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
		this.petriNetwork.removePlace(((PlaceAdapter)place).getModelPlace());
	}

	/**
	 * Supprime une transition spécifique du réseau.
	 * @param transition Transition à supprimer.
	 */
	@Override
	public void removeTransition(AbstractTransition transition) {
		this.petriNetwork.removeTransition(((TransitionAdapter)transition).getModelTransition());
	}

	/**
	 * Supprime un arc spécifique des listes d'arcs entrants ou sortants, selon sa présence.
	 * @param arc Arc à supprimer.
	 */
	@Override
	public void removeArc(AbstractArc arc) {
		Arc modelArc = ((ArcAdapter)arc).getModelArc();
		this.petriNetwork.removeArc(modelArc);
	}

	/**
	 * Vérifie si une transition est activée dans le réseau de Petri.
	 * @param transition La transition à vérifier.
	 * @return True si la transition est activée, sinon False.
	 * @throws ResetArcMultiplicityException Si l'arc est videur.
	 */
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

	/**
	 * Déclenche une transition dans le réseau de Petri.
	 * @param transition La transition à déclencher.
	 * @throws ResetArcMultiplicityException.
	 */
	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		this.petriNetwork.step(((TransitionAdapter)transition).getModelTransition());
	}

	/**
	 * Ajoute un arc zero reliant une place et une transition dans le réseau de Petri.
	 * @param place La place source de l'arc zero.
	 * @param transition La transition destination de l'arc zero.
	 * @return L'arc zero ajouté.
	 * @throws UnimplementedCaseException.
	 */
	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc arc = new ArcAdapter((TransitionAdapter) transition, (PlaceAdapter) place, "Inhibitor");
		this.petriNetwork.addArcZero(((ArcAdapter)arc).getModelArc());
		return arc;
	}

	/**
	 * Ajoute un arc videur reliant une place et une transition dans le réseau de Petri.
	 * @param place La place source de l'arc videur.
	 * @param transition La transition destination de l'arc videur.
	 * @return L'arc videur ajouté.
	 * @throws UnimplementedCaseException.
	 */
	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		AbstractArc arc = new ArcAdapter((TransitionAdapter) transition, (PlaceAdapter) place, "Reset");
		this.petriNetwork.addArcVideur(((ArcAdapter)arc).getModelArc());
		return arc;
	}
}