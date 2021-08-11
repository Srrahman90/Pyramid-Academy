package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

    //declaring attributes and fields
    public static FlappyBird flappyBird;
    public final int WIDTH = 1200, HEIGHT = 800;
    public Rectangle bird;
    public Renderer renderer;
    public ArrayList<Rectangle> columns;
    public Random rand;
    public boolean gameOver, started;
    public int ticks, yMotion, score;

    //constructor: initializing values to fields
    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);
        renderer = new Renderer();
        jFrame.add(renderer);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.addMouseListener(this);
        jFrame.addKeyListener(this);

        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        columns = new ArrayList<Rectangle>();
        rand = new Random();
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        timer.start();

    }

    //building a new rectangular column and adding it to jPanel
    public void addColumn(boolean start) {
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);
        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    //painting the rectangular column created
    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    //jump function on mouse click to make the bird jump:
    public void jump() {

        //when game is over:
        if (gameOver) {

            //creating the new bird and columns to begin a new game:
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver = false;
        }

        //before the game has started:
        if (!started) {
            started = true;
        }

        //when the game has been started and is running:
        else if (!gameOver) {

            //making the bid go up by decreasing the value of its y-coordinate:
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }

    //action listener function to perform the action:
    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;

        //when the game has started
        if (started) {

            //moving the columns/bars by decreasing the value of their x coordinate(depicted by speed) by 10 units:
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {

                //making the bird go down by increasing the value of its y-coordinate:
                yMotion += 2;
            }
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                //removing the column that just passed through the left edge:
                if (column.x + column.width < 0) {
                    columns.remove(column);
                    if (column.y == 0) {

                        //preventing the columns from adding twice:
                        addColumn(false);
                    }
                }
            }

            //adding the value yMotion to the y-coordinate of bird to bring it down:
            bird.y += yMotion;
            for (Rectangle column : columns) {
                if (column.y == 0 && bird.x == column.x + column.width) {

                    //increasing the score
                    score++;
                }

                if (column.intersects(bird)) {

                    //game is over if bird collides with the bar
                    gameOver = true;
                    if (bird.x <= column.x) {

                        //bird goes away with the same column it collides with
                        bird.x = column.x - bird.width;
                    } else {

                        //when bird enters the gap between two bars:
                        if (column.y != 0) {

                            //if bird hits the bottom bar, then it would fall on the top of that bar
                            bird.y = column.y - bird.height;
                        }
                    }
                }
            }

            //if bird hits the ground or the top edge of the game, the game is over
            if (bird.y > HEIGHT - 120 || bird.y <= 0) {
                gameOver = true;
            }
            if (bird.y + yMotion >= HEIGHT - 120) {

                //bird settles on ground once it hits the ground
                bird.y = HEIGHT - 120 - bird.height;
                gameOver = true;
            }
        }

        //applying the paint using repaint function
        renderer.repaint();

    }

    //repaint method to paint and display all the components of jPanel
    public void repaint(Graphics g) {

        //blue background to depict sky:
        g.setColor(Color.cyan.brighter());
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //orange bottom rectangle for the ground:
        g.setColor(Color.orange.darker());
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        //green strip to show grass above the ground layer:
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 140, WIDTH, 20);

        //red block to show the bird:
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        //painting the rectangular bars/ columns using the paintColumn function:
        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        /*
        drawing the text to appear before starting of the game, while the game is running (score)
         and after the game is over:
         */
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (!started) {
            g.drawString("Click to Start!", 275, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over!", 300, HEIGHT / 2 - 50);
        }
        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2, 100);
        }
    }

    public static void main(String[] args) {

        //creating an object of the class
        flappyBird = new FlappyBird();
    }

    //Overridden methods from MouseListener and KeyListener interfaces
    @Override
    public void mouseClicked(MouseEvent e) {

        //when mouse is clicked, jump function is called
        jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            //when space bar is hit, jump function is called
            jump();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}

