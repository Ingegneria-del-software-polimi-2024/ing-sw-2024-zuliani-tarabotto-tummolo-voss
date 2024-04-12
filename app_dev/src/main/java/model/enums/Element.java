package model.enums;

/**
 * the elements that can be present on the corners of the cards
 */
public enum Element {
    mushrooms,
    animals,
    vegetals,
    insects;

    public Element calculateComplementar() {
        switch (this){
            case animals:
                return mushrooms;
            case mushrooms:
                return vegetals;
            case insects:
                return animals;
            case vegetals:
                return insects;
        }
        return null;
    }
}