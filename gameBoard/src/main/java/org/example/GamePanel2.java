package org.example;

import org.example.PieceDropTargetListener;
import org.piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel2 extends JPanel implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 100;
    final int FPS = 40;
    Thread gameThread;
    Board2 board = new Board2();
    Mouse2 mouse = new Mouse2();

    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();

    Piece activeP; // piece that you are holding

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    public GamePanel2() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);

        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);


    }




    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces() {
        pieces.add(new ResourceCard(WHITE, 1, 2));
        pieces.add(new ResourceCard(WHITE, 7, 2));
        pieces.add(new ResourceCard(WHITE, 14, 2));


    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
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
                for (Piece piece : simPieces) {
                    // if it's your color pick up piece
                    if (piece.color == currentColor &&
                            piece.row <= mouse.y / Board.SQUARE_SIZE && piece.row > (mouse.y / Board.SQUARE_SIZE) - 3 &&
                            piece.col <= mouse.x / Board.SQUARE_SIZE && piece.col > (mouse.x / Board.SQUARE_SIZE) - 5) {
                        activeP = piece;
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
        return new Dimension(800, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        board.draw(g2);

        for (Piece p : simPieces) {
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

    public void addPiece(org.example.APP2.Piece piece) {
        pieces.add(new Pawn(WHITE, 0, 6));
    }
}
