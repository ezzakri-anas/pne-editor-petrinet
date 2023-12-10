package org.pneditor.petrinet.models.anas_adam;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Classe principale représentant un réseau de Petri, contenant des places, transitions et arcs.
 */
public class PetriNetwork {
	private LinkedList<Arc> list_of_arcs;
	private LinkedList<Transition> list_of_transitions;
	private LinkedList<Place> list_of_places;

	/**
	 * Constructeur de la classe PetriNetwork. Initialise les listes pour les arcs, transitions et places.
	 */
	public PetriNetwork() {
		this.list_of_arcs= new LinkedList<Arc>();
		this.list_of_transitions= new LinkedList<Transition>();
		this.list_of_places=new LinkedList<Place>();
	}

	/**
	 * Définit la liste des transitions dans le réseau.
	 * @param Transitions Liste des transitions à définir.
	 */
	public void setTransitionList(LinkedList<Transition> Transitions) {
		this.list_of_transitions=Transitions;
	}

	/**
	 * Obtient la liste actuelle des transitions dans le réseau.
	 * @return La liste des transitions.
	 */
	public LinkedList<Transition> getTransitionList(){
		return this.list_of_transitions;
	}

	/**
	 * Définit la liste des places dans le réseau.
	 * @param Places Liste des places à définir.
	 */
	public void setPlaceList(LinkedList<Place> Places) {
		this.list_of_places = Places;
	}

	/**
	 * Obtient la liste actuelle des places dans le réseau.
	 * @return La liste des places.
	 */
	public LinkedList<Place> getPlaceList(){
		return this.list_of_places;
	}

	/**
	 * Ajoute une transition spécifique au réseau.
	 * @param transition Transition à ajouter.
	 */
	public void addTransition(Transition transition) {
		this.list_of_transitions.add(transition);
	}

	/**
	 * Ajoute une place spécifique au réseau.
	 * @param place Place à ajouter.
	 */
	public void addPlace(Place place) {
		this.list_of_places.add(place);
	}

	/**
	 * Ajoute un arc au réseau avec les paramètres spécifiés.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 * @param weight Poids de l'arc.
	 * @param entrsort Booléen indiquant si l'arc est entrant ou sortant.
	 * @param isZeroorVideur Booléen indiquant si l'arc est de type ArcZero ou ArcVideur.
	 */
	public void addArc(Arc arc, boolean to_transition) {
		if (this.isArcUnique(arc)) {
			this.list_of_arcs.add(arc);
			for(Transition T: this.list_of_transitions) {
				if(T.equals(arc.getTransition())) {
					if(to_transition) {T.addInputArc(arc);}
					else {T.addOutputArc(arc);}
				}
			}
		}

	}
	/**
	 * Ajoute un ArcZero au réseau, qui est un type spécial d'arc avec des règles de tirage spécifiques.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 */
	public void addArcZero(Arc arc) {
		if (this.isArcUnique(arc)) {
			this.list_of_arcs.add(arc);
			Transition T = arc.getTransition();
			T.addInputArc(arc);
		}
	}
	
	/**
	 * Ajoute un ArcVideur au réseau, un type spécial d'arc qui vide entièrement la place de ses jetons lorsqu'il est tiré.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 */
	public void addArcVideur(Arc arc) {
		if (this.isArcUnique(arc)) {
			this.list_of_arcs.add(arc);
			Transition T = arc.getTransition();
			T.addInputArc(arc);
		}
	}


	/**
	 * Retourne la liste des arcs présents dans le réseau.
	 * @return La liste des arcs.
	 */
	public LinkedList<Arc> getArcList() {
		return this.list_of_arcs;
	}
	
	/**
	 * Définit la liste des arcs dans le réseau.
	 * @param Arcs Liste des arcs à définir.
	 */
	public void setArcList(LinkedList<Arc> Arcs) {
		this.list_of_arcs = Arcs;
	}

	/**
	 * Modifie le poids d'un arc spécifique.
	 * @param arc Arc dont le poids doit être modifié.
	 * @param poids Nouveau poids de l'arc.
	 */
	public void changeArcValue (Arc arc, int poids) {
		arc.setWeight(poids);

	}
	/**
	 * Définit le nombre de jetons pour une place donnée.
	 * @param place Place pour laquelle modifier le nombre de jetons.
	 * @param token_nbre Nouveau nombre de jetons pour la place.
	 */
	public void setPlaceToken (Place place, int token_nbre) {
		place.setTokenNbre(token_nbre);

	}
	/**
	 * Vérifie si un arc est unique dans le réseau.
	 * @param arc Arc à vérifier.
	 * @return Booléen indiquant si l'arc est unique.
	 */

	public boolean isArcUnique(Arc arc) {
		for (Arc arc_transitions : this.list_of_arcs) {
			if (arc_transitions.equals(arc)) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Exécute un pas (tirage) sur une transition donnée.
	 * @param T Transition sur laquelle effectuer le pas.
	 */
	public void step(Transition T) {
		//on verifie d abrd que c est titable

		boolean enabled= true;
		for (Arc arc : T.getInputArcs()) {
			if(!arc.isActive()) {
				enabled= false;
			}
		}
		
		if(enabled) {
			for (Arc arc : T.getInputArcs()) {
				if(arc.isVideurOrZero()) {
					arc.fire();
				}
				else {
					if(arc.isActive()) {
						arc.getPlace().removeTokenNbre(arc.getWeight());	
					}
				}
			}
			for (Arc arc : T.getOutputArcs()) {
				arc.getPlace().setTokenNbre(arc.getPlace().getTokenNbre() + arc.getWeight());	
			}
		}
	}

	/**
	 * Exécute un pas (tirage) sur toutes les transitions du réseau.
	 */
	public void stepAll() {
		for (Transition T : this.list_of_transitions) {
			this.step(T);
		}

	}

	/**
	 * Affiche l'état actuel du réseau de Petri, y compris les informations sur les places, transitions et arcs.
	 */
	public String showPetriNet() {

		String final_message = "Réseau de Petri \n";

		int arcs_nbre = this.list_of_arcs.size();
		int transitions_nbre = this.list_of_transitions.size();
		int places_nbre = this.list_of_places.size();

		final_message += places_nbre + " places\n";
		final_message += transitions_nbre + " transitions\n";	
		final_message += arcs_nbre + " arcs\n";	


		String transitions_message = "\nListe des transitions\n";

		for(int i=0; i<transitions_nbre; i++) {
			Transition t = this.list_of_transitions.get(i);
			LinkedList<Arc> input_arcs = t.getInputArcs();
			LinkedList<Arc> output_arcs = t.getOutputArcs();
			transitions_message += i+1 + " : transition, " + input_arcs.size() + " arc(s) entrant(s), " + output_arcs.size() + " arc(s) sortant(s)\n";	
		}

		final_message += transitions_message;

		Map<Object, Object> Arc_destination = new HashMap<>();
		Map<Object, Object> Arc_source = new HashMap<>();

		Map<Object, LinkedList<Object>> inputs_of_places = new HashMap<>();
		Map<Object, LinkedList<Object>> outputs_of_places = new HashMap<>();

		for(int i=0; i<transitions_nbre; i++) {
			Transition t = this.list_of_transitions.get(i);

			LinkedList<Arc> input_arcs = t.getInputArcs();
			LinkedList<Arc> output_arcs = t.getOutputArcs();


			for(int j=0; j<input_arcs.size(); j++) {
				Arc arc = input_arcs.get(j);

				Place place = arc.getPlace();

				Arc_destination.put(arc, t);
				Arc_source.put(arc, arc.getPlace());

				if(outputs_of_places.containsKey(place)) {
					LinkedList<Object> values = outputs_of_places.get(place);
					values.addFirst(arc);
					outputs_of_places.put(place, values);
				} else {
					LinkedList<Object> values = new LinkedList<Object>();
					values.addFirst(arc);
					outputs_of_places.put(place, values);
				}
			}

			for(int j=0; j<output_arcs.size(); j++) {
				Arc arc = output_arcs.get(j);
				Place place = arc.getPlace();

				Arc_destination.put(arc, arc.getPlace());
				Arc_source.put(arc, t);

				if(inputs_of_places.containsKey(place)) {
					LinkedList<Object> values = inputs_of_places.get(place);
					values.addFirst(arc);
					inputs_of_places.put(place, values);
				} else {
					LinkedList<Object> values = new LinkedList<Object>();
					values.addFirst(arc);
					inputs_of_places.put(place, values);
				}
			}
		}

		String arcs_message = "\nListe des arcs\n";	

		for(int i=0; i<arcs_nbre; i++) {
			Arc arc = this.list_of_arcs.get(i);

			Class<?> arc_class = arc.getClass();

			if(Arc_destination.get(arc) instanceof Place) {
				arcs_message += i+1 + " : arc de type " + arc_class + " poids " + arc.getWeight() + 
						" (place avec " + arc.getPlace().getTokenNbre() + " jetons vers transition)\n";	
			} else {
				arcs_message += i+1 + " : arc de type " + arc_class + " poids " + arc.getWeight() + 
						" (Transition vers place avec " + arc.getPlace().getTokenNbre() + " jetons)\n";	
			}
		}

		final_message += arcs_message;


		String places_message = "\nListe des places\n";

		for(int i=0; i<places_nbre; i++) {
			String imput_arc_message = "";
			String output_arc_message = "";

			Place place = this.list_of_places.get(i);
			if(inputs_of_places.containsKey(place)) {
				int input_arcs_nbre = inputs_of_places.get(place).size();
				imput_arc_message = " " + input_arcs_nbre + " arcs entrants,";
			}

			if(outputs_of_places.containsKey(place)) {
				int output_arcs_nbre = outputs_of_places.get(place).size();
				output_arc_message = " " + output_arcs_nbre + " arcs sortants,";
			}

			places_message += i+1 + " : place avec " + place.getTokenNbre() + " jetons, " + imput_arc_message + "," + output_arc_message +"\n";
		}

		final_message += places_message;

		return final_message;

	}
	// Méthode principale pour exécuter et tester le réseau de Petri. Crée un réseau, y ajoute des éléments et exécute des pas.

	public static void main(String[] args) {
		PetriNetwork Petri= new PetriNetwork();
		Transition T1=  create_transition_for_test(Petri);
		Transition T2=  create_transition_for_test(Petri);
		Place P1= create_place_for_test(5,Petri);
		Place P2= create_place_for_test(0,Petri);
		Place P3= create_place_for_test(1,Petri);
		Arc A = new Arc(4, T1, P1, false);
		Petri.addArc(A, true);
		ArcZero AZ1 = new ArcZero(T1, P2, true);
		ArcZero AZ2 = new ArcZero(T2, P3, true);
		Petri.addArcZero(AZ1);
		Petri.addArcZero(AZ2);

		//		Petri.AfficherPetriNet();


		Petri.stepAll();


		Petri.showPetriNet();

	}


	/**
	 * Méthode utilitaire pour créer et ajouter une nouvelle transition au réseau.
	 * @param Petri Réseau de Petri auquel la transition sera ajoutée.
	 * @return La transition créée et ajoutée au réseau.
	 */
	public static Transition create_transition_for_test( PetriNetwork Petri) {
		LinkedList<Arc> arcsEntrants= new LinkedList<Arc>() ;
		LinkedList<Arc> arcsSortants= new LinkedList<Arc>() ;
		Transition T1= new Transition(arcsEntrants,arcsSortants);
		Petri.addTransition(T1);
		return T1;


	}
	/**
	 * Méthode utilitaire pour créer et ajouter une nouvelle place au réseau.
	 * @param poids Nombre initial de jetons dans la place.
	 * @param Petri Réseau de Petri auquel la place sera ajoutée.
	 * @return La place créée et ajoutée au réseau.
	 */
	public static Place create_place_for_test(int poids,PetriNetwork Petri) {

		Place T1= new Place(poids);
		Petri.addPlace(T1);
		return T1;

	}
	/**
	 * Méthode utilitaire pour créer et ajouter un nouvel arc au réseau.
	 * @param poids Poids de l'arc à créer.
	 * @param transition Transition associée à l'arc.
	 * @param place Place associée à l'arc.
	 * @param entrsortie Booléen indiquant si l'arc est entrant ou sortant.
	 * @param isVideurOrZero Booléen indiquant si l'arc est de type ArcZero ou ArcVideur.
	 * @param Petri Réseau de Petri auquel l'arc sera ajouté.
	 * @return L'arc créé et ajouté au réseau.
	 */
	public static Arc create_arc_for_test(int poids, Transition transition, Place place,boolean entrsortie, boolean isVideurOrZero,PetriNetwork Petri) {
		Arc arc= new Arc(poids, transition, place, isVideurOrZero);
		Petri.addArc(arc, entrsortie);

		return arc;


	}
	/**
	 * Méthode utilitaire pour créer et ajouter un ArcVideur au réseau.
	 * @param transition Transition associée à l'ArcVideur.
	 * @param place Place associée à l'ArcVideur.
	 * @param Petri Réseau de Petri auquel l'ArcVideur sera ajouté.
	 * @return L'ArcVideur créé et ajouté au réseau.
	 */
	public static Arc create_arc_videur_for_test( Transition transition, Place place,PetriNetwork Petri) {
		Arc arc= new ArcVideur(transition, place ,true);
		Petri.addArcVideur(arc);

		return arc;

	}
	/**
	 * Méthode utilitaire pour créer et ajouter un ArcZero au réseau.
	 * @param transition Transition associée à l'ArcZero.
	 * @param place Place associée à l'ArcZero.
	 * @param Petri Réseau de Petri auquel l'ArcZero sera ajouté.
	 * @return L'ArcZero créé et ajouté au réseau.
	 */
	public static Arc create_arc_zero_for_test( Transition transition, Place place,PetriNetwork Petri) {
		Arc arc = new ArcZero(transition, place ,true);
		Petri.addArcZero(arc);

		return arc;

	}

}
