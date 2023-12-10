/**
 * Ce package contient des classes représentant un réseau de Petri, comprenant des places, des transitions et des arcs.
 * Les éléments principaux de ce package sont les suivants :
 * 
 * - {@link org.pneditor.petrinet.models.anas_adam.Arc} : Classe représentant un arc liant une place à une transition dans un réseau de Petri.
 * - {@link org.pneditor.petrinet.models.anas_adam.ArcVideur} : Classe héritante de Arc, représentant un type spécial d'arc qui vide entièrement la place de ses jetons.
 * - {@link org.pneditor.petrinet.models.anas_adam.ArcZero} : Classe héritante de Arc, représentant un type spécial d'arc actif uniquement lorsque la place associée ne contient aucun jeton.
 * - {@link org.pneditor.petrinet.models.anas_adam.PetriNetwork} : Classe principale représentant un réseau de Petri, contenant des places, transitions et arcs.
 * - {@link org.pneditor.petrinet.models.anas_adam.Place} : Classe représentant une place dans un réseau de Petri, caractérisée par un nombre de jetons.
 * - {@link org.pneditor.petrinet.models.anas_adam.Transition} : Classe représentant une transition dans un réseau de Petri, contenant des arcs entrants et sortants.
 * 
 * Les méthodes de ces classes permettent de manipuler le réseau de Petri, d'ajouter des éléments tels que des places, transitions et arcs, 
 * d'effectuer des pas (tirages ou steps) sur les transitions, et d'afficher l'état actuel du réseau.
 * 
 * Exemple d'utilisation :
 * 
 * ```java
 * PetriNetwork petriNetwork = new PetriNetwork();
 * 
 * Transition transition = new Transition(new LinkedList<>(), new LinkedList<>());
 * petriNetwork.addTransition(T1);
 * 
 * Place place = new Place(5);
 * petriNetwork.addPlace(T1);
 * 
 * petriNetwork.addArc(transition, place, 2, true, false);
 * petriNetwork.stepAll();
 * petriNetwork.showPetriNet();
 * ```
 */
package org.pneditor.petrinet.models.anas_adam;
