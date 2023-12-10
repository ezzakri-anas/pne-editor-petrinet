/**
 * Ce package contient des adaptateurs entre l'interface graphique de l'éditeur de réseau de Petri
 * et le modèle sous-jacent, permettant l'intégration de ces éléments dans l'éditeur.
 * Les principaux adaptateurs inclus sont les suivants :
 * 
 * - {@link org.pneditor.petrinet.adapters.anas_adam.PetriNetAdapter} : Adaptateur entre PetriNetInterface et PetriNetwork.
 * - {@link org.pneditor.petrinet.adapters.anas_adam.TransitionAdapter} : Adaptateur entre AbstractTransition et Transition.
 * - {@link org.pneditor.petrinet.adapters.anas_adam.PlaceAdapter} : Adaptateur entre AbstractPlace et Place.
 * - {@link org.pneditor.petrinet.adapters.anas_adam.ArcAdapter} : Adaptateur entre AbstractArc et Arc, gérant différents types d'arcs.
 * 
 * Ces adaptateurs facilitent l'interaction entre l'interface graphique et le modèle de réseau de Petri sous-jacent.
 * 
 */
package org.pneditor.petrinet.adapters.anas_adam;