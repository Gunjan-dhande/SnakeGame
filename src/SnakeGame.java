import javax.swing.*;
public class SnakeGame extends JFrame {
    GamePad gamePad;


    SnakeGame(){  // Constructor
        gamePad = new GamePad();
        setTitle(" Snake Game ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePad);
        pack();
        setResizable(false);
        ImageIcon logo = new ImageIcon("src/photos/logo.png");
        setIconImage(logo.getImage());



        setVisible(true);
        setLocationRelativeTo(null);



    }
    public static void main(String[] args) {
        // Initialization of Game
        new SnakeGame();

    }
}