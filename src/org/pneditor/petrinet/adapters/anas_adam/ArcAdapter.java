package org.pneditor.petrinet.adapters.anas_adam;

import java.util.LinkedList;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.anas_adam.Arc;
import org.pneditor.petrinet.models.anas_adam.ArcVideur;
import org.pneditor.petrinet.models.anas_adam.ArcZero;
import org.pneditor.petrinet.models.anas_adam.Place;
import org.pneditor.petrinet.models.anas_adam.Transition;

/**
 * Cette classe sert d'adaptateur entre l'interface graphique de l'éditeur d'arc
 * (AbstractArc) et le modèle sous-jacent (Arc).
 */
public class ArcAdapter extends AbstractArc{
	private Arc model_arc;
	private Transition model_transition;
	private Place model_place;
	private AbstractNode transition;
	private AbstractNode place;
	private boolean to_transition;

    /**
     * Constructeur de la classe ArcAdapter pour un arc régulier avec un poids donné.
     * @param multiplicity La multiplicité de l'arc.
     * @param transition La transition associée à l'arc.
     * @param place La place associée à l'arc.
     * @param isRegular True si c'est un arc régulier, sinon False.
     * @param toTransition True si l'arc va vers la transition, sinon False.
     */
	public ArcAdapter(int multiplicity, TransitionAdapter transition, PlaceAdapter place, boolean isRegular, boolean to_transition){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		this.model_arc = new Arc(multiplicity, model_transition, model_place, !isRegular);;
		this.to_transition = to_transition;

	}

    /**
     * Constructeur de la classe ArcAdapter pour un arc régulier avec une multiplicité de 1 par défaut.
     * @param transition La transition associée à l'arc.
     * @param place La place associée à l'arc.
     * @param isRegular True si c'est un arc régulier, sinon False.
     * @param toTransition True si l'arc va vers la transition, sinon False.
     */
	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, boolean isRegular, boolean to_transition){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		this.model_arc = new Arc(1, model_transition, model_place, !isRegular);;
		this.to_transition = to_transition;

	}

    /**
     * Constructeur de la classe ArcAdapter pour un arc zero ou videur.
     * @param transition La transition associée à l'arc.
     * @param place La place associée à l'arc.
     * @param specialType Le type spécial de l'arc ("Inhibitor" ou "Reset").
     */
	public ArcAdapter(TransitionAdapter transition, PlaceAdapter place, String specialType){
		this.transition = transition;
		this.place = place;
		this.model_transition = transition.getModelTransition();
		this.model_place = place.getModelPlace();
		if(specialType == "Inhibitor") {
			this.model_arc = new ArcZero(model_transition, model_place, true);
		} else {
			this.model_arc = new ArcVideur(model_transition, model_place, true);
		}
		this.to_transition = true;

	}

    /**
     * Obtient la direction de l'arc.
     * @return True si l'arc va vers la transition, sinon False.
     */
	public boolean getDirection() {
		return this.to_transition;
	}

    /**
     * Obtient le modèle d'arc sous-jacent.
     * @return Le modèle d'arc.
     */
	public Arc getModelArc() {
		return this.model_arc;
	}
	
    /**
     * Obtient le nœud source de l'arc.
     * @return Le nœud source de l'arc.
     */
	@Override
	public AbstractNode getSource() {
		return this.to_transition? this.place: this.transition;
	}

    /**
     * Obtient le nœud destination de l'arc.
     * @return Le nœud destination de l'arc.
     */
	@Override
	public AbstractNode getDestination() {
		return this.to_transition? this.transition: this.place;
	}

    /**
     * Vérifie si l'arc est un arc videur.
     * @return True si c'est un arc videur, sinon False.
     */
	@Override
	public boolean isReset() {
		return model_arc instanceof ArcVideur;
	}

    /**
     * Vérifie si l'arc est un arc régulier.
     * @return True si c'est un arc régulier, sinon False.
     */
	@Override
	public boolean isRegular() {
		return !model_arc.isVideurOrZero();
	}

    /**
     * Vérifie si l'arc est un arc zero.
     * @return True si c'est un arc zero, sinon False.
     */
	@Override
	public boolean isInhibitory() {
		return model_arc instanceof ArcZero;
	}

    /**
     * Obtient la multiplicité de l'arc.
     * @return La multiplicité de l'arc.
     * @throws ResetArcMultiplicityException.
     */
	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return model_arc.getWeight();
	}

    /**
     * Définit la multiplicité de l'arc.
     * @param multiplicity La nouvelle multiplicité de l'arc.
     * @throws ResetArcMultiplicityException.
     */
	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		model_arc.setWeight(multiplicity);
	}
}
