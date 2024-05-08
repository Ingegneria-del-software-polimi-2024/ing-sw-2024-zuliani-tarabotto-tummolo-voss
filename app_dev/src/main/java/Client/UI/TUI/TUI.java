package Client.UI.TUI;

import Client.UI.UI;
import Client.View.ViewAPI;

import javax.swing.text.View;

public class TUI implements UI {

    private ViewAPI view;
    private DispositionPrinter dispositionPrinter;

    public TUI(ViewAPI view) {
        this.view = view;
        dispositionPrinter = new DispositionPrinter();
    }


    @Override
    public void displayInitialization() {

    }

    @Override
    public void displayStarterCardSelection() {

    }

    @Override
    public void displayObjectiveSelection() {

    }

    @Override
    public void displayPlacingCard() {
        //dispositionPrinter.print(view.getDisposition)
    }

    @Override
    public void displayCardDrawing() {

    }

    @Override
    public void displayCalculateObjectives() {

    }

    @Override
    public void displayLobby() {

    }

    @Override
    public void displayLogin() {

    }
}
