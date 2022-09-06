import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREENWIDTH = 600;
    static final int SCREENHEIGHT = 600;
    static final int UNITCELL = 50;
    static final int GAMEEBLOCK = (SCREENWIDTH * SCREENHEIGHT)/(UNITCELL * UNITCELL);
    static final int DELAY = 275;
    final int x[] = new int[GAMEEBLOCK];
    final int y[] = new int[GAMEEBLOCK];
    int snekBody = 2;
    int appieEat;
    int appiex;
    int appiey;
    char dir = 'R';
    boolean runnin = false;
    Timer timer;
    Random rand;

    GamePanel(){
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        this.setBackground(Color.decode("#C1E1C1"));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        runnin = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(runnin) {

            g.setColor(Color.decode("#D2042D"));
            g.fillOval(appiex, appiey, UNITCELL, UNITCELL);

            for(int i = 0; i< snekBody; i++) {
                if(i == 0) {
                    g.setColor(Color.decode("#FFFFF"));
                    g.fillRect(x[i], y[i], UNITCELL, UNITCELL);
                }
                else {
                    g.setColor(Color.decode("#A7C7E7"));

                    g.fillRect(x[i], y[i], UNITCELL, UNITCELL);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("SansSerif",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ appieEat, (SCREENWIDTH - metrics.stringWidth("Score: "+ appieEat))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void newApple(){
        appiex = rand.nextInt((int)(SCREENWIDTH / UNITCELL))* UNITCELL;
        appiey = rand.nextInt((int)(SCREENHEIGHT / UNITCELL))* UNITCELL;
    }
    public void move(){
        for(int i = snekBody; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(dir) {
            case 'U':
                y[0] = y[0] - UNITCELL;
                break;
            case 'D':
                y[0] = y[0] + UNITCELL;
                break;
            case 'L':
                x[0] = x[0] - UNITCELL;
                break;
            case 'R':
                x[0] = x[0] + UNITCELL;
                break;
        }

    }
    public void checkApple() {
        if((x[0] == appiex) && (y[0] == appiey)) {
            snekBody++;
            appieEat++;
            newApple();
        }
    }
    public void checkCollisions() {
        for(int i = snekBody; i>0; i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                runnin = false;
            }
        }
        if(x[0] < 0) {
            runnin = false;
        }
        if(x[0] > SCREENWIDTH) {
            runnin = false;
        }
        if(y[0] < 0) {
            runnin = false;
        }
        if(y[0] > SCREENHEIGHT) {
            runnin = false;
        }

        if(!runnin) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ appieEat, (SCREENWIDTH - metrics1.stringWidth("Score: "+ appieEat))/2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREENWIDTH - metrics2.stringWidth("Game Over"))/2, SCREENHEIGHT /2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(runnin) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U') {
                        dir = 'D';
                    }
                    break;
            }
        }
    }
}