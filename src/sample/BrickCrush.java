package sample;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class BrickCrush extends Canvas implements Runnable {

        private int width = 606;
        private int height = 606;

        int x= 90;
        int y= 90;

        private Thread thread;
        int fps = 30;
        private boolean isRunning;

        private BufferStrategy bs;

        private int playerX, playerY, playerVX, playerVY;

        public BrickCrush() {
            JFrame frame = new JFrame("Brick Crush!");
            this.setSize(606, 606);
            frame.add(this);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.addKeyListener(new KL());
            frame.setVisible(true);
            isRunning = false;

            playerX = x;
            playerY = y;
            playerVX = 0;
            playerVY = 0;
        }

        public void update() {
            playerX += playerVX;
            playerY += playerVY;
        }

        public void draw() {
            bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }

            Graphics g = bs.getDrawGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0,0,width,height);
            drawBrickdot(g, 250,150);
            drawBrickdot(g, 150,350);
            drawBrickdot(g, 350,250);
            drawBrickdot(g, 250,450);
            drawBrickdot(g, 350,350);
            drawBrickdot(g, 450,450);
            drawBrickdot(g, 550,550);
            drawBrickdot(g, 86,286);
            drawBrickdot(g, 86,486);
            drawBrickdot(g, 186,186);
            drawBrickdot(g, 286,286);
            drawBrickdot(g, 286,386);
            drawBrickdot(g, 486,186);
            drawBrickdot(g, 486,486);
            drawPlayerdot(g, playerX,playerY);
            g.dispose();
            bs.show();
        }

        public static void main(String[] args) {
            BrickCrush painting = new BrickCrush();
            painting.start();
        }

        public synchronized void start() {
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }

        public synchronized void stop() {
            isRunning = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            double deltaT = 1000.0/fps;
            long lastTime = System.currentTimeMillis();
            while (isRunning) {
                long now = System.currentTimeMillis();
                if (now-lastTime > deltaT) {
                    update();
                    draw();
                    lastTime = now;
                }

            }
            stop();
        }

        private class KL implements KeyListener {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == 'a') {
                    playerVX = -2;
                }
                if (keyEvent.getKeyChar() == 'd') {
                    playerVX = 2;
                }
                if (keyEvent.getKeyChar() == 'w') {
                    playerVY = -2;
                }
                if (keyEvent.getKeyChar() == 's') {
                    playerVY = 2;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == 'a') {
                    playerVX = 0;
                }
                if (keyEvent.getKeyChar() == 'd') {
                    playerVX = 0;
                }
                if (keyEvent.getKeyChar() == 'w') {
                    playerVY = 0;
                }
                if (keyEvent.getKeyChar() == 's') {
                    playerVY = 0;
                }
            }
        }

        private void drawBrickdot(Graphics g, int x, int y) {
            g.setColor(new Color(255, 0, 0));
            g.fillRect(x, y, 18, 12);
        }

        private void drawPlayerdot(Graphics g, int x, int y) {
            g.setColor(new Color(20, 37, 180));
            g.fillRect(x, y, 24, 24);
        }
    }
