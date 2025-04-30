import javax.swing.*;
import java.awt.*;


public class WinningFrame extends JFrame {

    public WinningFrame(String color) {
        setTitle("\" Congratulations! \"");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        
        // Set the icon image
        ImageIcon icon = new ImageIcon("CheckersGame\\images\\CheckersIcon.jpeg"); // Replace with the path to your icon image
        setIconImage(icon.getImage());

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        // Load the image based on the color
        ImageIcon image;
        if (color.equals("red")) {
            image = new ImageIcon("CheckersGame\\images\\RedWin.jpeg");
        } else if (color.equals("black")) {
            image = new ImageIcon("CheckersGame/images/BlackWin.jpeg");
        } else {
            image = new ImageIcon("CheckersGame/images/Tie.jpeg");
        }

        // Scale the image to fit within 800x800
        double aspectRatio = image.getIconWidth() / (double) image.getIconHeight();
        int maxWidth = Math.min(image.getIconWidth(), 800);
        int maxHeight = (int) (maxWidth / aspectRatio);
        Image scaledImage = image.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(image);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(imageLabel);

        // Display the winning message in metallic golden color
        JLabel messageLabel = new JLabel();
        String message = (color.equals("red") ? "\"Red Wins!\"" : (color.equals("black") ? "\"Black Wins!\"" : "It's a Tie!"));
        messageLabel.setText(message);
        messageLabel.setFont(new Font("Times New Romanl", Font.BOLD, 40));
        messageLabel.setForeground(new Color(211, 175, 55)); // Metallic golden color
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBounds(0, 70, 800, 40); // Adjust vertical position
        imageLabel.add(messageLabel); // Add message label to image label

        JButton closeButton = new JButton("Quit Game");
        closeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        closeButton.setHorizontalAlignment(JButton.CENTER);
        closeButton.setBounds(250, 125, 125, 25); // Adjust button position
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(211, 175, 55));
        closeButton.addActionListener(e -> System.exit(0));
        imageLabel.add(closeButton); // Add button to image label
        
        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
        restartButton.setHorizontalAlignment(JButton.CENTER);
        restartButton.setBounds(430, 125, 125, 25); // Adjust button position
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(211, 175, 55));
        restartButton.addActionListener(e -> {
            dispose(); // Close the winning frame
           new CheckersGUI();  // Restart the game
        });
        imageLabel.add(restartButton); // Add button to image label

        add(contentPanel);
        setVisible(true);
    }

}
