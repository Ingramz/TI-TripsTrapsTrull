import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameUI extends JFrame {
    // Parent game object
    private Game game;
    // Input query related fields
    private boolean user_input_allowed = false;
    private int last_input = -1;
    // Check if UI is still open
    private boolean open = true;

    // Create UI
    GameUI(Game g) {
        super("Trips-traps-trull");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                open = false;
            }
        });
        setMinimumSize(new Dimension(400, 400));
        getContentPane().add(new BoardPanel(this));
        setLocationRelativeTo(null);
        setVisible(true);
        this.game = g;
    }

    // Wait for user input and then return it
    int getMove() {
        user_input_allowed = true;
        last_input = -1;
        while (open && last_input == -1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        user_input_allowed = false;
        return last_input;
    }

    // Display the outcome
    void announceOutcome(Game.Turn winner) {
        String winningMessage = "";
        if (winner == Game.Turn.AI) {
            winningMessage = "Arvuti võitis!";
        } else if (winner == Game.Turn.PLAYER) {
            winningMessage = "Mängija võitis!";
        } else if (winner == Game.Turn.NOONE) {
            winningMessage = "Mäng jäi viiki!";
        }

        if (!winningMessage.isEmpty()) {
            setTitle(getTitle() + " [" + "Mäng läbi: " + winningMessage + "]");
            JOptionPane
                .showMessageDialog(this, winningMessage + "\n\nUue mängu alustamiseks sulge aken.");
        }
    }

    boolean isOpen() {
        return open;
    }

    public Game getGame() {
        return game;
    }

    public boolean isInputAllowed() {
        return user_input_allowed;
    }

    public void setInput(int input) {
        last_input = input;
    }
}
