package org.example.test2;
import org.example.Board;
import org.example.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    final int FPS = 40;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();

    Piece activeP; // piece that you are holding

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);

        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);

        new DropTarget(this, DnDConstants.ACTION_MOVE, new PieceDropTargetListener(this), true);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces() {
        // Add initial pieces to the board
        pieces.add(new org.example.test2.Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        // Add more pieces as needed
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {
        // GAME LOOP
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
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
        // Update game state
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw the board
        board.draw(g2);

        // Draw pieces
        for (Piece p : simPieces) {
            p.draw(g2);
        }

        // Draw active piece
        if (activeP != null) {
            activeP.draw(g2);
        }
    }

    public void addPiece(Piece piece) {
        // Calculate drop location and update piece position
        Point dropLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(dropLocation, this);
        int col = dropLocation.x / Board.SQUARE_SIZE;
        int row = dropLocation.y / Board.SQUARE_SIZE;
        piece.col = col;
        piece.row = row;

        // Add the piece to the board
        pieces.add(piece);
        simPieces.add(piece);
    }
}
