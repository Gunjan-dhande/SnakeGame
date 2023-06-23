import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePad extends JPanel implements ActionListener {
    int Pad_Height = 800;
    int Pad_Width = 800;
    int Max_Dots = 256;
    int Dot_Size = 50;
    int Dots;
    int[] x = new int[Max_Dots];
    int[] y = new int[Max_Dots];

    //food position
    int Food_x;
    int Food_y;

    Random random;


    // images
    Image body, head, food, g_score, back, g_Over;

    //
    Timer timer;
    int DELAY = 200;
    boolean left_Side = true;
    boolean right_Side = false;
    boolean up_Side = false;
    boolean down_Side = false;
    boolean inGame = true;


    GamePad() {


        random = new Random();
        TAdapter tAdapter= new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(Pad_Width, Pad_Height));
        setBackground(Color.BLACK);
        initGame();
        loadImage();



    }

    //Initialization Game logic
    public void initGame() {
        Dots = 3;
        //Initialization Snake Position

        x[0] = 700;
        y[0] = 700;

        for (int i = 0; i < Dots; i++) {
            x[i] = x[0] + Dot_Size * i;
            y[i] = y[0];
        }

        randomFood();
        timer = new Timer(DELAY, this);
        timer.start();


    }

    // Load Images from folder
    public void loadImage() {
        ImageIcon bodyIcon = new ImageIcon("src/photos/body.png");
        body = bodyIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/photos/head.png");
        head = headIcon.getImage();

        ImageIcon foodIcon = new ImageIcon("src/photos/food.png");
        food = foodIcon.getImage();

        ImageIcon gameScore = new ImageIcon("src/photos/score.png");
        g_score = gameScore.getImage();

        ImageIcon backGround = new ImageIcon("src/photos/Background.png");
        back = backGround.getImage();

        ImageIcon gameO = new ImageIcon("src/photos/gameover.png");
        g_Over = gameO.getImage();


    }

    // Draw Images at position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }

    // method for draw image
    public void doDrawing(Graphics g) {



        if(inGame){
            //Background
            g.drawImage(back, 0,0,this);


            //Line
//            for (int i = 0; i < Pad_Height / Dot_Size; i++) {
//                g.drawLine(i * Dot_Size, 0, i * Dot_Size, Pad_Height);
//                g.drawLine(0, i * Dot_Size, Pad_Width, i * Dot_Size);
//            }



            //Snake food
            g.drawImage(food, Food_x, Food_y, this);

            //snake body
            for (int i = 0; i < Dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else
                    g.drawImage(body, x[i], y[i], this);
            }

        }
        else{
            timer.stop();
            g.drawImage(g_Over, 0,0,this);
            gameOver(g);
        }


    }

    // Randomize food
    public void randomFood() {
        Food_x = random.nextInt((int) (Pad_Width / Dot_Size)) * Dot_Size;
        Food_y = random.nextInt((int) (Pad_Height / Dot_Size)) * Dot_Size;
    }

    //Collision of snake and body
    public void collision(){

        //body
        for(int i=1; i<Dots; i++){
            if(i>2 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        // border

        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(x[0] >= Pad_Width){
            inGame = false;
        }
        if(y[0] >= Pad_Height){
            inGame = false;
        }
    }
    int Score = 0;
    //Game Over
    public void gameOver(Graphics g){
        Score = (Dots - 3);
        String Sc = "Score : " + Integer.toString(Score);
        String restart = "Press SPACE to Restart";
        Font  small = new Font("Helveetica", Font.BOLD, 40);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor( Color.WHITE);
        g.setFont(small);
        g.drawString(Sc, (Pad_Width-fontMetrics.stringWidth(Sc))/2, 4*(Pad_Height/5));
        g.drawString(restart,(Pad_Width-fontMetrics.stringWidth(restart))/2 , 7*(Pad_Height/8));

    }

    public void restart(){
        inGame = true;
        initGame();
        loadImage();
        Score = 0;
        Dots = 3;
        DELAY = 300;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame) {
            checkFood();
            collision();
            move();
        }

        repaint();
    }

    public void move() {
        for(int i=Dots-1; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left_Side){
            x[0] -= Dot_Size;
        }
        if(right_Side){
            x[0] += Dot_Size;
        }
        if(up_Side){
            y[0] -= Dot_Size;
        }
        if(down_Side){
            y[0] += Dot_Size;
        }
    }

    //snake eat food
    public void checkFood(){
        if(Food_x == x[0] && Food_y == y[0]){
            Dots++;
            randomFood();
        }
    }

    //Snake Control
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {//press Space Bar to Restart
                restart();
            }
            if(key == KeyEvent.VK_LEFT && !right_Side){
                left_Side = true;
                up_Side = false;
                down_Side = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left_Side){
                right_Side = true;
                up_Side = false;
                down_Side = false;
            }
            if(key == KeyEvent.VK_UP && !down_Side){
                left_Side = false;
                up_Side = true;
                right_Side = false;
            }
            if(key == KeyEvent.VK_DOWN && !up_Side){
                left_Side = false;
                right_Side = false;
                down_Side = true;
            }

        }
    }

}
