package Client.UI.TUI;

import model.PointsStrategy.CornersPoints;
import model.PointsStrategy.SimplePoints;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.enums.Element;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class CardBuilder {

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

    public String buildSecondRow(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line;
        //LEFT CORNER
        line = leftCornersBuilder(0, card);


        //CENTER
        if(card instanceof StarterCard && card.getFaceSide()) line += buildStarterBackSecondRow(card);
        else if(card.getFaceSide()) line += pointsBuilder(card);
        else line += "         ";

        //RIGHT CORNER
        line += rightCornersBuilder(1, card);

        //System.out.println(line);
        return line;
    }

    public String buildThirdRow(PlayableCard card) {
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
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

        //System.out.println(line);
        return line;
    }


    public String buildFourthRow(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
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

    public String buildFifthRow(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
        //LEFT CORNER
        line = leftCornersBuilder(2, card);

        //CENTER
        if(card.getFaceSide()) line += constraintBuilder(card);
        else line += "         ";

        //RIGHT CORNER
        line += rightCornersBuilder(3, card);


        return line;
    }

    public String buildBottom(PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
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

    private String constraintBuilder(PlayableCard card) {
        String line = new String();
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

    private String leftCornersBuilder(int corner, PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
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

    private String rightCornersBuilder(int corner, PlayableCard card){
        int color;
        if(card instanceof StarterCard){
            color = 221;//yellow
        }else{color = card.getBlockedElement().getColor();}

        String line = new String();
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

    private String backFaceSymbolBuilder(Element element, int row){
        return element.getBackFaceSymbol(row);
    }

    private String buildStarterBackSecondRow(PlayableCard card){
        String line = new String();
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[0].getStringValue() + "    ";
        }
        return "         ";
    }

    private String buildStarterBackThirdRow(PlayableCard card){
        String line = new String();
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[1].getStringValue() + "    ";
        }
        return "    " + card.getBlockedElements()[0].getStringValue() + "    ";
    }

    private String buildStarterBackFourthRow(PlayableCard card){
        String line = new String();
        if(card.getBlockedElements().length == 3){
            return "    " + card.getBlockedElements()[2].getStringValue() + "    ";
        }
        else if(card.getBlockedElements().length == 2){
            return "    " + card.getBlockedElements()[1].getStringValue() + "    ";
        }
        return "         ";
    }
}
