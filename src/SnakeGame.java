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
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Initialization of Game
        
        File file = new File("src/photos/Snake.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        new SnakeGame();
    }
}
