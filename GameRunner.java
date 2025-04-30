import javax.swing.*;

public class GameRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashFrame splashFrame = new SplashFrame();
            splashFrame.setVisible(true);

            Timer timer = new Timer(5000, e -> {
                splashFrame.dispose();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}