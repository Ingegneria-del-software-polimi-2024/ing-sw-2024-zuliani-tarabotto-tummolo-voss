package Client.UI.GUI.playableArea.working;

import Client.UI.GUI.playableArea.working.DraggableCards.Card;
import Client.UI.GUI.playableArea.working.DraggableCards.ResourceCard;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    final int FPS = 40;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    public static ArrayList<Card> cards = new ArrayList<>();
    public static ArrayList<Card> simCards = new ArrayList<>();

    Card activeP; // piece that you are holding

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);

        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(cards, simCards);

       // new DropTarget(this, DnDConstants.ACTION_MOVE, new PieceDropTargetListener(this), true);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces() {
        cards.add(new ResourceCard(1, 6));
        cards.add(new ResourceCard( 7, 6));
        cards.add(new ResourceCard( 14, 6));
        cards.add(new ResourceCard( 21, 6));

    }

    private void copyPieces(ArrayList<Card> source, ArrayList<Card> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {
        //GAMELOOP
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (mouse.pressed) {
            if (activeP == null) {
                //check if you can pick up a piece
                for (Card card : simCards) {
                    // if it's your color pick up piece
                    if (
                            card.row <= mouse.y / Board.SQUARE_SIZE && card.row > (mouse.y / Board.SQUARE_SIZE) - 3 &&
                            card.col <= mouse.x / Board.SQUARE_SIZE && card.col > (mouse.x / Board.SQUARE_SIZE) - 5) {
                        activeP = card;
                    }
                }
            } else {
                simulate();
            }
        }


        //MOUSE BUTTON RELEASED

        if(!mouse.pressed)
        {
            if(activeP != null){

                activeP.updatePosition();
                activeP = null;


            }    }
    }


    private void simulate() {
        //if a piece is being held update its position
        activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row= activeP.getRow(activeP.y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        board.draw(g2);

        for (Card p : simCards) {
            p.draw(g2);
        }

        if(activeP != null){
            g2.setColor(Color.white);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect(activeP.col*Board.SQUARE_SIZE, activeP.row*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            activeP.draw(g2);
        }
    }

    //public void addPiece(org.example.APP2.Piece piece) {
     //   pieces.add(new Pawn(WHITE, 0, 6));
   // }
}
