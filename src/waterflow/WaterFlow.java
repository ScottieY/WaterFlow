/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waterflow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ScottieYan
 */
public class WaterFlow {

    final int EMPTY = 0;
    final int WATER = 1;
    final int WATERN = 3;
    final int WALL = 2;
    final int SIZE = 200;
    final int SPEED = 1;
    final int WALLNUM = (int) (SIZE * SIZE * 0.4);

    int counter = 0;//debug last grid
    int scrW, scrH;
    int map[][] = new int[SIZE][SIZE];
    Random r = new Random();
    //int wx, wy;//water cooridnate

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        new WaterFlow();
    }

    WaterFlow() {
        JFrame window = new JFrame("WaterFlow");
        window.setSize(1000, 1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateRandomMap();

        window.add(gP);
        window.validate();
        window.setVisible(true);

        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                fillWater();
                gP.repaint();
                if (!notfilled()) {
                    counter++;
                }
                if (counter == 2) {
                    counter = 0;
                    System.out.println(counting()/(SIZE*SIZE*.6)*100);
                    generateRandomMap();
                }
            }
        };
        Timer timer = new Timer(SPEED, taskPerformer);
        timer.start();
    }
    int counting(){
        int c=0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j]==WATER) {
                    c++;
                }
            }
        }
        return c;
    }
    void fillWater() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (map[x][y] == WATER) {
                    try {
                        if (map[x + 1][y] == EMPTY) {
                            map[x + 1][y] = WATERN;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x][y + 1] == EMPTY) {
                            map[x][y + 1] = WATERN;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x - 1][y] == EMPTY) {
                            map[x - 1][y] = WATERN;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x][y - 1] == EMPTY) {
                            map[x][y - 1] = WATERN;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    boolean notfilled() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (map[x][y] == WATER || map[x][y] == WATERN) {
                    try {
                        if (map[x + 1][y] == EMPTY) {
                            return true;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x][y + 1] == EMPTY) {
                            return true;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x - 1][y] == EMPTY) {
                            return true;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (map[x][y - 1] == EMPTY) {
                            return true;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return false;
    }
    GraphicsPanel gP = new GraphicsPanel();

    public class GraphicsPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            this.setSize(1000, 1000);
            
            scrW = gP.getWidth();
            scrH = gP.getHeight();

            int gridW = scrW / SIZE;
            int gridH = scrH / SIZE;
            //draw lines
            /*
                    for (int i = 1; i < SIZE; i++) {
                g.drawLine(i * gridW, 0, i * gridW, scrH);
                g.drawLine(0, i * gridH, scrW, i * gridH);
            }
            */
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    switch (map[i][j]) {
                        case EMPTY:
                            break;
                        case WALL:
                            g.setColor(Color.GRAY);
                            g.fillRect(i * gridW, j * gridH, gridW, gridH);
                            break;
                        case WATER:
                        case WATERN:
                            map[i][j] = WATER;
                            g.setColor(Color.BLUE);
                            g.fillRect(i * gridW, j * gridH, gridW, gridH);
                            break;
                    }
                }
            }
        }

    }

    void generateRandomMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = EMPTY;
            }
        }

        int x, y;

        for (int i = 0; i < WALLNUM; i++) {
            boolean checked = false;
            do {
                x = r.nextInt(SIZE);
                y = r.nextInt(SIZE);
                if (map[x][y] == EMPTY) {
                    map[x][y] = WALL;
                    checked = true;
                }
            } while (!checked);

        }
        boolean checkedw = false;
        int wx,wy;
        do {
            wx = r.nextInt(SIZE);
            wy = r.nextInt(SIZE);
            if (map[wx][wy] == EMPTY) {
                map[wx][wy] = WATER;
                checkedw = true;
            }
        } while (!checkedw);
    }
}
