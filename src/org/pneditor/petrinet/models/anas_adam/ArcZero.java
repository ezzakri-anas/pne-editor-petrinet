package org.pneditor.petrinet.models.anas_adam;

/**
 * Classe ArcZero, une sous-classe de Arc, représentant un type spécial d'arc dans un réseau de Petri.
 * Cet arc est uniquement actif lorsque la place associée ne contient aucun jeton.
 */
public class ArcZero extends Arc {

    /**
     * Constructeur de la classe ArcZero. Initialise l'ArcZero avec une transition, une place et un marqueur isVideurOrZero.
     * isVideurOrZero est utilisé pour indiquer que cet arc est un arc spécial (bien que le nom puisse ne pas être entièrement descriptif pour ArcZero).
     * @param transition Transition associée à l'ArcZero.
     * @param place Place associée à l'ArcZero.
     * @param isVideurOrZero Indique si l'arc est de type spécial (ArcZero ou non).
     */
    public ArcZero(Transition transition, Place place, boolean isVideurOrZero) {
        super(transition, place, isVideurOrZero);
    }


    /**
     * Méthode isActive surchargée pour vérifier si l'arc est actif.
     * Un ArcZero est actif uniquement si le nombre de jetons dans la place associée est égal à zéro.
     * @return True si la place associée n'a aucun jeton, sinon false.
     */
    @Override
    public boolean isActive() {
        return this.getPlace().getTokenNbre() == 0;
    }

    // Note: La méthode fire() n'est pas surchargée ici, car le comportement par défaut d'Arc est suffisant.
    // ArcZero ne modifie pas le nombre de jetons dans la place lorsqu'il est activé.

}


