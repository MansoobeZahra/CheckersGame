import javax.swing.*;
import java.awt.*;

public class SplashFrame extends JFrame {

    public SplashFrame() {
        
        SoundEffect splashSound = new SoundEffect("CheckersGame\\soundeffects\\splash.wav"); // Add your splash screen sound file here
        splashSound.play();

        // Set up the frame
        setTitle("Splash Frame");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);
        setUndecorated(true);

        // Set the icon image
        ImageIcon icon = new ImageIcon("CheckersGame\\images\\CheckersIcon.jpeg"); // Replace with the path to your icon image
        setIconImage(icon.getImage());

        // Create a panel with the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("CheckersGame\\images\\Starting . . ..png"); // Replace "splash_background.jpg" with the path to your image
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Add the panel to the frame
        add(backgroundPanel);

        // Display this frame for 5 seconds
       Timer timer = new Timer(5000, e -> {
        dispose(); // Close this frame
        SwingUtilities.invokeLater(() -> new CheckersGUI().setVisible(true)); // Open the main frame
         });
        timer.setRepeats(false); // Only fire once
        timer.start();

    }
}