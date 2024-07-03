import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//==================================================================================================

public class Panel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int UNIT_SIZE = 25;
    // The total number of units on the screen
    static final int WIDTH_UNITS = SCREEN_WIDTH / UNIT_SIZE;
    static final int HEIGHT_UNITS = SCREEN_HEIGHT / UNIT_SIZE;
    // The delay of the movement in milliseconds
    static final int DELAY = 50;

    // Initial position of the pen
    int x = SCREEN_WIDTH / 2;
    int y = SCREEN_HEIGHT / 2;
    // Grid that represents a canvas
    // each cell is a unit of the grid and can be either empty or filled
    boolean[][] grid = new boolean[WIDTH_UNITS][HEIGHT_UNITS];
    // The initial direction
    char direction = 'R';
    // The app is not running initially
    boolean running = false;
    // Timer object to control the speed
    Timer timer;


    // Constructor
    Panel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        // The panel is focusable meaning it can receive keyboard inputs
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startDrawing();
    }

    public void startDrawing() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            // Draw the cells depending on their state
            for (int i = 0; i < WIDTH_UNITS; i++) {
                for (int j = 0; j < HEIGHT_UNITS; j++) {
                    if (i == x / UNIT_SIZE && j == y / UNIT_SIZE) {
                        g.setColor(Color.white);
                        g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }
                    else {
                        if (grid[i][j]) {
                            g.setColor(Color.green);
                            g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        }
                        else {
                            g.setColor(Color.black);
                            g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        }
                    }
                }
            }
        }  
    }

    public void move() {

        switch (direction) {
            case 'U':
                y = y - UNIT_SIZE;
                break;
            case 'D':
                y = y + UNIT_SIZE;
                break;
            case 'L':
                x = x - UNIT_SIZE;
                break;
            case 'R':
                x = x + UNIT_SIZE;
                break;
        }
    }

    public void checkCollisions() {

        // Check if the pen touches the left border
        if (x == 0 - UNIT_SIZE) {
            x = SCREEN_WIDTH - UNIT_SIZE;
        }
        // Check if the pen touches the right border
        if (x == SCREEN_WIDTH) {
            x = 0;
        }
        // Check if the pen touches the top border
        if (y == 0 - UNIT_SIZE) {
            y = SCREEN_HEIGHT - UNIT_SIZE;
        }
        // Check if the pen touches the bottom border
        if (y == SCREEN_HEIGHT) {
            y = 0;
        }
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode() ) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }    
    }

    // change the state of the cell on contact with the pen
    public void changeCellState(int x, int y) {
        grid[x][y] = !grid[x][y];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (running) {
            move();
            checkCollisions();
            changeCellState(x / UNIT_SIZE, y / UNIT_SIZE);
        }
        repaint();  
    }
}
