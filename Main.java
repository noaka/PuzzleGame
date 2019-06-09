import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setVisible(true);
            f.setTitle("Noa Kafry: Fifteen Puzzle Puzzle");
            f.setResizable(false);
            f.add(new FifteenPuzzle(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
