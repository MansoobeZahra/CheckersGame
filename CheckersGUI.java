import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckersGUI extends JFrame {

    SoundEffect selectSound;
    SoundEffect placeSound;
    SoundEffect captureSound;

    private JPanel boardPanel;
    private JPanel borderPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel[][] squares;
    private CheckerGame game;
    private int squareSize;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Color brownColor = new Color(77, 25, 0);
    private Color almondColor = new Color(239, 222, 205);
    private Color selectedColor = new Color(121, 255, 255);
    private int borderThickness = 25;

    private static CheckersGUI instance;

    public static CheckersGUI getInstance() {
        if (instance == null) {
            instance = new CheckersGUI();
        }
        return instance;
    }

    public CheckersGUI() {
        game = new CheckerGame();
        squares = new JLabel[8][8];
        boardPanel = new JPanel(new GridLayout(8, 8));
        borderPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridLayout(1, 8));
        bottomPanel = new JPanel(new GridLayout(1, 8));
        leftPanel = new JPanel(new GridLayout(8, 1));
        rightPanel = new JPanel(new GridLayout(8, 1));

        borderPanel.setBorder(new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
        borderPanel.setBackground(brownColor); // Set the background color of the border panel to brown

        squareSize = 75;

        // Load sound effects
         selectSound = new SoundEffect("CheckersGame\\soundeffects\\select.wav"); // Replace with your select sound path
         placeSound = new SoundEffect("CheckersGame\\soundeffects\\placed01.wav"); // Replace with your place sound path
         captureSound = new SoundEffect("CheckersGame\\soundeffects\\capture.wav"); // Replace with your capture sound path

        // Create squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JLabel();
                squares[i][j].setOpaque(true);
                squares[i][j].setPreferredSize(new Dimension(squareSize, squareSize));
                squares[i][j].setBackground((i % 2 == j % 2) ? almondColor : brownColor);
                squares[i][j].addMouseListener(new SquareClickListener(i, j));
                boardPanel.add(squares[i][j]);
            }
        }

        // Add numbering (1-8) to the side squares
        for (int i = 0; i < 8; i++) {
            JLabel leftLabel = new JLabel(String.valueOf(8 - i), SwingConstants.CENTER);
            leftLabel.setForeground(brownColor);
            leftPanel.add(leftLabel);
            leftPanel.add(new JLabel(" ", SwingConstants.CENTER));

            JLabel rightLabel = new JLabel(String.valueOf(8 - i), SwingConstants.CENTER);
            rightLabel.setForeground(brownColor);
            rightPanel.add(rightLabel);
            rightPanel.add(new JLabel(" ", SwingConstants.CENTER));
        }


        // Add lettering (A-H) to the top and bottom squares
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        for (int j = 0; j < 8; j++) {
            JLabel topLabel = new JLabel(String.valueOf(letters[j]), SwingConstants.CENTER);
            topLabel.setForeground(brownColor);
            topPanel.add(topLabel);

            JLabel bottomLabel = new JLabel(String.valueOf(letters[j]), SwingConstants.CENTER);
            bottomLabel.setForeground(brownColor);
            bottomPanel.add(bottomLabel);
        }

        updateBoard();

        borderPanel.add(boardPanel, BorderLayout.CENTER);
        borderPanel.add(topPanel, BorderLayout.NORTH);
        borderPanel.add(bottomPanel, BorderLayout.SOUTH);
        borderPanel.add(leftPanel, BorderLayout.WEST);
        borderPanel.add(rightPanel, BorderLayout.EAST);
        add(borderPanel, BorderLayout.CENTER);

        setTitle("Checkers By Legends");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        // Set the icon image
        ImageIcon icon = new ImageIcon("CheckersGame\\images\\CheckersIcon.jpeg"); // Replace with the path to your icon image
        setIconImage(icon.getImage());
    }
   
   
    public CheckersGUI(CheckerGame checkerGame) {
        game = new CheckerGame();
        squares = new JLabel[8][8];
        boardPanel = new JPanel(new GridLayout(8, 8));
        borderPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridLayout(1, 8));
        bottomPanel = new JPanel(new GridLayout(1, 8));
        leftPanel = new JPanel(new GridLayout(8, 1));
        rightPanel = new JPanel(new GridLayout(8, 1));

        borderPanel.setBorder(new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness));
        borderPanel.setBackground(brownColor); // Set the background color of the border panel to brown

        squareSize = 75;

        // Load sound effects
        selectSound = new SoundEffect("CheckersGame\\soundeffects\\select.wav"); // Replace with your select sound path
        placeSound = new SoundEffect("CheckersGame\\soundeffects\\placed01.wav"); // Replace with your place sound path
        captureSound = new SoundEffect("CheckersGame\\soundeffects\\capture.wav"); // Replace with your capture sound path

        // Create squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JLabel();
                squares[i][j].setOpaque(true);
                squares[i][j].setPreferredSize(new Dimension(squareSize, squareSize));
                squares[i][j].setBackground((i % 2 == j % 2) ? almondColor : brownColor);
                squares[i][j].addMouseListener(new SquareClickListener(i, j));
                boardPanel.add(squares[i][j]);
            }
        }

        // Add numbering (1-8) to the side squares
        for (int i = 0; i < 8; i++) {
            JLabel leftLabel = new JLabel(String.valueOf(8 - i), SwingConstants.CENTER);
            leftLabel.setForeground(brownColor);
            leftPanel.add(leftLabel);
            leftPanel.add(new JLabel(" ", SwingConstants.CENTER));

            JLabel rightLabel = new JLabel(String.valueOf(8 - i), SwingConstants.CENTER);
            rightLabel.setForeground(brownColor);
            rightPanel.add(rightLabel);
            rightPanel.add(new JLabel(" ", SwingConstants.CENTER));
        }

        // Add lettering (A-H) to the top and bottom squares
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        for (int j = 0; j < 8; j++) {
            JLabel topLabel = new JLabel(String.valueOf(letters[j]), SwingConstants.CENTER);
            topLabel.setForeground(brownColor);
            topPanel.add(topLabel);

            JLabel bottomLabel = new JLabel(String.valueOf(letters[j]), SwingConstants.CENTER);
            bottomLabel.setForeground(brownColor);
            bottomPanel.add(bottomLabel);
        }

        updateBoard();

        borderPanel.add(boardPanel, BorderLayout.CENTER);
        borderPanel.add(topPanel, BorderLayout.NORTH);
        borderPanel.add(bottomPanel, BorderLayout.SOUTH);
        borderPanel.add(leftPanel, BorderLayout.WEST);
        borderPanel.add(rightPanel, BorderLayout.EAST);
        add(borderPanel, BorderLayout.CENTER);

        setTitle("Checkers By Legends");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        // Set the icon image
        ImageIcon icon = new ImageIcon("CheckersGame\\images\\CheckersIcon.jpeg"); // Replace with the path to your icon image
        setIconImage(icon.getImage());
    }

    void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CheckerPiece piece = game.getPieceAt(i, j);
                if (piece != null) {
                    ImageIcon icon = new ImageIcon(piece.getImagePath());
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    squares[i][j].setIcon(scaledIcon);
                    squares[i][j].setHorizontalAlignment(JLabel.CENTER);
                    squares[i][j].setVerticalAlignment(JLabel.CENTER);

                } else {
                    squares[i][j].setIcon(null);
                }
            }
        }
    }

    private class SquareClickListener extends MouseAdapter {
        private int row;
        private int col;

        public SquareClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedRow == -1 && selectedCol == -1) {
                CheckerPiece piece = game.getPieceAt(row, col);
                if (piece != null && game.isRedTurn() == (piece.getColor() == Color.RED)) {
                    selectedRow = row;
                    selectedCol = col;
                    squares[row][col].setBackground(selectedColor);
                    selectSound.play(); // Play select sound
                }
            } else {
                if (game.movePiece(selectedRow, selectedCol, row, col)) {
                   
                    if (Math.abs(selectedRow - row) == 2) {
                        captureSound.play(); // Play capture sound if a piece was captured
                    } else {
                        placeSound.play(); // Play place sound
                    }
                    
                    selectedRow = -1;
                    selectedCol = -1;
                    updateBoard();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            squares[i][j].setBackground((i % 2 == j % 2) ? almondColor : brownColor);
                        }
                    }
                } else {
                    selectedRow = -1;
                    selectedCol = -1;
                    squares[row][col].setBackground((row % 2 == col % 2) ? almondColor : brownColor);
                }
            }
        }
    }
}
