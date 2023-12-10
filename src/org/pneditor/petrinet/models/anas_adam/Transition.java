package org.pneditor.petrinet.models.anas_adam;

import java.util.LinkedList;

/**
 * Classe représentant une transition dans un réseau de Petri, contenant des arcs entrants et sortants.
 */
public class Transition {
	private LinkedList<Arc> inputArcs;
	private LinkedList<Arc> outputArcs;

	/**
	 * Constructeur de la classe Transition. Initialise la transition avec des listes d'arcs entrants et sortants.
	 * @param inputArcs Liste des arcs entrants.
	 * @param outputArcs Liste des arcs sortants.
	 */
	public Transition(LinkedList<Arc> inputArcs, LinkedList<Arc> outputArcs) {
		this.inputArcs = inputArcs;
		this.outputArcs = outputArcs;
	}

	/**
	 * Retourne la liste des arcs entrants de cette transition.
	 * @return Liste des arcs entrants.
	 */
	public LinkedList<Arc> getInputArcs() {
		return this.inputArcs;
	}

	/**
	 * Définit la liste des arcs entrants pour cette transition.
	 * @param inputArcs Liste des arcs entrants à définir.
	 */
	public void setInputArcs(LinkedList<Arc> inputArcs) {
		this.inputArcs = inputArcs;
	}

	/**
	 * Retourne la liste des arcs sortants de cette transition.
	 * @return Liste des arcs sortants.
	 */
	public LinkedList<Arc> getOutputArcs() {
		return this.outputArcs;
	}

	/**
	 * Définit la liste des arcs sortants pour cette transition.
	 * @param outputArcs Liste des arcs sortants à définir.
	 */
	public void setOutputArcs(LinkedList<Arc> outputArcs) {
		this.outputArcs = outputArcs;
	}

	/**
	 * Ajoute un arc entrant à la liste des arcs entrants de cette transition.
	 * @param arc Arc à ajouter à la liste des entrants.
	 */
	public void addInputArc(Arc arc) {
		LinkedList<Arc> arcs = this.getInputArcs();
		arcs.add(arc);
		this.setInputArcs(arcs);// à tester si on peut ajouter un arc sans le setter
	}
	/**
	 * Ajoute un arc sortant à la liste des arcs sortants de cette transition.
	 * @param arc Arc à ajouter à la liste des sortants.
	 */
	public void addOutputArc(Arc arc) {
		LinkedList<Arc> arcs = this.getOutputArcs();
		arcs.add(arc);
		this.setOutputArcs(arcs);// à tester si on peut ajouter un arc sans le setter
	}
	/**
	 * Supprime un arc spécifique des listes d'arcs entrants ou sortants, selon sa présence.
	 * Lève une exception si l'arc n'est trouvé ni dans les arcs entrants ni dans les arcs sortants.
	 * @param arc Arc à supprimer.
	 * @throws Exception Si l'arc n'est pas trouvé.
	 */
	public void removeArc(Arc arc) throws Exception {
		LinkedList<Arc> outputs = this.getOutputArcs();
		LinkedList<Arc> inputs = this.getInputArcs();

		if (outputs.contains(arc)) {
			outputs.remove(arc);
			this.setOutputArcs(outputs);
		} else if (inputs.contains(arc)) {
			inputs.remove(arc);
			this.setInputArcs(inputs);
		} else {
			throw new Exception("Number must be non-negative");
		}
	}


}
