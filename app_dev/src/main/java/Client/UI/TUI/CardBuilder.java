package Client.UI.TUI;

import model.PointsStrategy.CornersPoints;
import model.PointsStrategy.SimplePoints;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.enums.Element;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class responsible for building each card, the building method is sensible to whether the card has a covered corner or not
 * The class has a different method for each row of the card
 * The cards are 6 rows high and 15 characters wide
 */
public class CardBuilder {

    /**
     * based on the "row" parameter, it calls a different building method which will return a line containing the specified "row"
     * for the specified "card"
     * @param row the row of the card that has to be built
     * @param card the corresponding PlayableCard
     * @return a line containing the desired row of characters
     */
    public String buildLine(int row, PlayableCard card){
        String line = new String();
        switch (row) {
            case 0:
                line = buildTop(card);
                break;
            case 1:
                line = buildSecondRow(card);
                break;
            case 2:
                line = buildThirdRow(card);
                break;
            case 3:
                line = buildFourthRow(card);
                break;
            case 4:
                line = buildFifthRow(card);
                break;
            case 5:
                line = buildBottom(card);
                break;
        }
        return line;
    }


    /**
     * builds the first row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildTop(PlayableCard card){
        String line = new String();

        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        //LEFT CORNER
        if(card.getCorner(0) != null && card.getCorner(0).getIsAvailable()){
            line = String.valueOf(ansi().fg(color).a("╔═╤").reset());

        } else if(card.getCorner(0) == null){line = String.valueOf(ansi().fg(color).a("╔══").reset());}

        //CENTER
        line += String.valueOf(ansi().fg(color).a("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550").reset());

        //RIGHT CORNER
        if(card.getCorner(1) != null && card.getCorner(1).getIsAvailable()){
            line += String.valueOf(ansi().fg(color).a("\u2564\u2550\u2557").reset());

        }else if(card.getCorner(1) == null){line += String.valueOf(ansi().fg(color).a("\u2550\u2550\u2557").reset());}

        return line;
    }


    /**
     * builds the second row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildSecondRow(PlayableCard card){

        String line;
        //LEFT CORNER
        line = leftCornersBuilder(0, card);

        //CENTER
        if(card instanceof StarterCard && card.getFaceSide()) line += buildStarterBackSecondRow(card);
        else if(card.getFaceSide()) line += pointsBuilder(card);
        else line += "         ";

        //RIGHT CORNER
        line += rightCornersBuilder(1, card);

        return line;
    }


    /**
     * builds the third row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildThirdRow(PlayableCard card) {
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = "";
        //LEFT CORNER
        if(card.getCorner(0) != null && card.getCorner(0).getIsAvailable()){
            line = String.valueOf(ansi().fg(color).a("\u255F\u2500\u2518").reset());

        } else if(card.getCorner(0) == null){line = String.valueOf(ansi().fg(color).a("\u2551  ").reset());}

        //CENTER
        if(card instanceof StarterCard && card.getFaceSide()) line += buildStarterBackThirdRow(card);
        else if(card instanceof StarterCard && !card.getFaceSide()) line += "         ";
        else if(card.getFaceSide()) line += "         ";
        else line += "   " + backFaceSymbolBuilder(card.getBlockedElement(), 0) + "   ";

        //RIGHT CORNER
        if(card.getCorner(1) != null && card.getCorner(1).getIsAvailable()){
            line += String.valueOf(ansi().fg(color).a("\u2514\u2500\u2562").reset());

        }else if(card.getCorner(1) == null){line += String.valueOf(ansi().fg(color).a("  \u2551").reset());}

        return line;
    }


    /**
     * builds the fourth row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildFourthRow(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = "";
        //LEFT CORNER
        if(card.getCorner(2) != null && card.getCorner(2).getIsAvailable()){
            line = String.valueOf(ansi().fg(color).a("\u255F\u2500\u2510").reset());

        } else if(card.getCorner(2) == null){line = String.valueOf(ansi().fg(color).a("\u2551  ").reset());}

        //CENTER
        if(card instanceof StarterCard && card.getFaceSide()) line += buildStarterBackFourthRow(card);
        else if(card instanceof StarterCard && !card.getFaceSide()) line += "         ";
        else if(card.getFaceSide()) line += "         ";
        else line += "   " + backFaceSymbolBuilder(card.getBlockedElement(), 1) + "   ";

        //RIGHT CORNER
        if(card.getCorner(3) != null && card.getCorner(3).getIsAvailable()){
            line += String.valueOf(ansi().fg(color).a("\u250C\u2500\u2562").reset());

        }else if(card.getCorner(3) == null){line += String.valueOf(ansi().fg(color).a("  \u2551").reset());}

        return line;
    }


    /**
     * builds the fifth row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildFifthRow(PlayableCard card){
        String line;
        //LEFT CORNER
        line = leftCornersBuilder(2, card);

        //CENTER
        if(card.getFaceSide()) line += constraintBuilder(card);
        else line += "         ";

        //RIGHT CORNER
        line += rightCornersBuilder(3, card);

        return line;
    }


    /**
     * builds the sixth row of the card.
     * @param card the corresponding PlayableCard
     * @return a line containing the desired characters
     */
    public String buildBottom(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = "";
        //LEFT CORNER
        if(card.getCorner(2) != null && card.getCorner(2).getIsAvailable()){
            line = String.valueOf(ansi().fg(color).a("\u255A\u2550\u2567").reset());

        } else if(card.getCorner(2) == null){line = String.valueOf(ansi().fg(color).a("\u255A\u2550\u2550").reset());}

        //CENTER
        line += String.valueOf(ansi().fg(color).a("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550").reset());

        //RIGHT CORNER
        if(card.getCorner(3) != null && card.getCorner(3).getIsAvailable()){
            line += String.valueOf(ansi().fg(color).a("\u2567\u2550\u255D").reset());

        }else if(card.getCorner(3) == null){line += String.valueOf(ansi().fg(color).a("\u2550\u2550\u255D").reset());}


        return line;
    }

    /**
     * Method that returns a line containing the correct representation of the card's PointsPolicy.
     * Method called by the buildSecondRow method only if the card is facing up and if it's not a StarterCard.
     * @param card the corresponding PlayableCard
     * @return a line containing the PointsPolicy representation
     */
    private String pointsBuilder(PlayableCard card){
        String line = new String();
        if(card.getPoints() != null){
            if( card.getPoints() instanceof  SimplePoints) line = "    " + card.getPoints().getPoints()+"    ";
            else if(card.getPoints() instanceof CornersPoints) { line = "   2|C   ";}
            else {line = "   1|" + card.getPoints().getArtifact().getStringValue() + "   ";}
        }else{
            line = "         ";
        }
        return line;
    }

    /**
     * Method that returns a line containing the correct representation of the card's PlacementConstraint.
     * Method called by the buildFifthRow method only if the card is facing up.
     * @param card the corresponding PlayableCard
     * @return a line containing the PlacementConstraint representation
     */
    private String constraintBuilder(PlayableCard card) {
        String line = "";
        int i = 0;
        int counter = 0;
        if(card.getPlacementConstraint() != null){
            for(Element e : card.getPlacementConstraint().keySet()){
                if(card.getPlacementConstraint().get(e) > 0){
                    i = card.getPlacementConstraint().get(e);
                    while(i > 0){
                        line += e.getStringValue();
                        i --;
                        counter ++;
                    }
                }
            }
        }else{return line = "         ";}

        if(counter == 3){ line = " [ " + line + " ] ";}
        else if(counter == 4){ line = " [" + line + " ] ";}
        else if(counter == 5){ line = " [" + line + "] ";}
        return line;
    }

    /**
     * Returns a line containing the representation of the specified card's left Corner.
     * If the Corner is covered by another card, then the line returned will be empty
     * @param corner the index of the specific card's Corner
     * @param card the corresponding PlayableCard
     * @return line containing the Corner representation
     */
    private String leftCornersBuilder(int corner, PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = "";
        if(card.getCorner(corner) != null && card.getCorner(corner).getIsAvailable()){
            //if corner is not empty
            if(card.getCorner(corner).getElement() != null){
                line = String.valueOf(ansi().fg(color).a("\u2551").reset()) + card.getCorner(corner).getElement().getStringValue() +String.valueOf(ansi().fg(color).a("\u2502").reset());
            }else if(card.getCorner(corner).getArtifact() != null){
                line = String.valueOf(ansi().fg(color).a("\u2551").reset()) + card.getCorner(corner).getArtifact().getStringValue()  +String.valueOf(ansi().fg(color).a("\u2502").reset());
            }else{line = String.valueOf(ansi().fg(color).a("\u2551").reset()) + String.valueOf(ansi().fg(color).a(" \u2502").reset());}

        } else if(card.getCorner(corner) == null){line = String.valueOf(ansi().fg(color).a("\u2551").reset()) + "  ";}
        return line;
    }


    /**
     * Returns a line containing the representation of the specified card's right Corner.
     * If the Corner is covered by another card, then the line returned will be empty
     * @param corner the index of the specific card's Corner
     * @param card the corresponding PlayableCard
     * @return line containing the Corner representation
     */
    private String rightCornersBuilder(int corner, PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = "";
        if(card.getCorner(corner) != null && card.getCorner(corner).getIsAvailable()){
            //if corner is not empty
            if(card.getCorner(corner).getElement() != null){
                line = String.valueOf(ansi().fg(color).a("\u2502").reset())+ card.getCorner(corner).getElement().getStringValue() + String.valueOf(ansi().fg(color).a("\u2551").reset());
            }else if(card.getCorner(corner).getArtifact() != null){
                line = String.valueOf(ansi().fg(color).a("\u2502").reset())+ card.getCorner(corner).getArtifact().getStringValue()  + String.valueOf(ansi().fg(color).a("\u2551").reset());
            }else{line = String.valueOf(ansi().fg(color).a("\u2502 ").reset()) + String.valueOf(ansi().fg(color).a("\u2551").reset());}

        } else if(card.getCorner(corner) == null){line = "  " + String.valueOf(ansi().fg(color).a("\u2551").reset());}
        return line;
    }


    /**
     * calls the Element's method that returns the specified row of the element's TUI representation
     * @param element
     * @param row
     * @return
     */
    private String backFaceSymbolBuilder(Element element, int row){
        return element.getBackFaceSymbol(row);
    }


    /**
     * Method called when a StarterCard is displayed face down and its second row is being printed.
     * Depending on how many and which blocked elements the StarterCard has, this method returns a different line
     * @param card corresponding StarterCard
     * @return
     */
    private String buildStarterBackSecondRow(PlayableCard card){
        String line = "";
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[0].getStringValue() + "    ";
        }
        return "         ";
    }

    /**
     * Method called when a StarterCard is displayed face down and its third row is being printed.
     * Depending on how many and which blocked elements the StarterCard has, this method returns a different line
     * @param card corresponding StarterCard
     * @return
     */
    private String buildStarterBackThirdRow(PlayableCard card){
        String line = "";
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[1].getStringValue() + "    ";
        }
        return "    " + card.getBlockedElements()[0].getStringValue() + "    ";
    }

    /**
     * Method called when a StarterCard is displayed face down and its fourth row is being printed.
     * Depending on how many and which blocked elements the StarterCard has, this method returns a different line
     * @param card corresponding StarterCard
     * @return
     */
    private String buildStarterBackFourthRow(PlayableCard card){
        String line = "";
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[2].getStringValue() + "    ";
        }
        else if(card.getBlockedElements().length == 2){
            return "    " + card.getBlockedElements()[1].getStringValue() + "    ";
        }
        return "         ";
    }
}
