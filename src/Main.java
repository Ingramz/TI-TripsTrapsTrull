import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set to native look and feel for buttons and windows.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {/* Nothing bad will happen if this fails. */}
        Object[] options = {"Mängija alustab", "Arvuti alustab", "Välju"};
        while (true) {
            // Ask for input
            int n = JOptionPane.showOptionDialog(null,
                "<html><b>Trips-traps-trull</b><br>2014 Indrek Ardel<br><br>Vali tegevus</html>",
                "Trips-traps-trull",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

            // Create a new game or exit
            if (n == 0) {
                new Game(true);
            } else if (n == 1) {
                new Game(false);
            } else {
                break;
            }
        }
    }
}
