import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Panel component for displaying the game
public class BoardPanel extends JPanel {
    private GameUI gui;
    // Clickable regions
    private Point[] regions = new Point[9];
    // Clickable region length (both horizontal and vertical)
    private int squareLength = 0;

    BoardPanel(GameUI ui) {
        super();
        gui = ui;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON1 && gui.isInputAllowed()) {
                    int x = e.getX();
                    int y = e.getY();

                    for (int i = 0; i < regions.length; i++) {
                        // If mouse click event happened inside one of our determined regions, send
                        // it to the Game UI window.
                        if (x >= regions[i].getX() && x <= regions[i].getX() + squareLength
                            && y >= regions[i].getY() && y <= regions[i].getY() + squareLength) {
                            gui.setInput(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        // Use antialiasing for smoother circles and lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(new Color(234, 234, 234)); // #EAEAEA

        // Make sure the game field is always a square that will fit inside the window.
        int fieldLength = Math.min(this.getHeight(), this.getWidth());
        int startX = (this.getWidth() - fieldLength) / 2;
        int startY = (this.getHeight() - fieldLength) / 2;
        int border_thickness = 5;
        int halfBorder = border_thickness / 2;
        squareLength = (fieldLength - 2 * border_thickness) / 3;
        int square5pct = (int) (squareLength * 0.05);
        int square10pct = (int) (squareLength * 0.1);
        int square80pct = (int) (squareLength * 0.8);
        int square90pct = (int) (squareLength * 0.9);

        // Draw the field
        Color fieldColor = new Color(250, 250, 250);
        g2.setColor(fieldColor);
        g2.fillRect(startX, startY, fieldLength, fieldLength);

        // Draw the lines
        g2.setColor(Color.BLACK);
        int offset1 = squareLength + halfBorder; // Offset for first line
        int offset2 = 2 * squareLength + border_thickness + halfBorder; // second line
        g2.drawLine(startX + offset1, startY, startX + offset1, startY + fieldLength);
        g2.drawLine(startX + offset2, startY, startX + offset2, startY + fieldLength);
        g2.drawLine(startX, startY + offset1, startX + fieldLength, startY + offset1);
        g2.drawLine(startX, startY + offset2, startX + fieldLength, startY + offset2);

        Game.Marking[] game_field = gui.getGame().getField();
        for (int i = 0; i < game_field.length; i++) {
            int colOffset = startX + (squareLength + border_thickness) * (i % 3);
            int rowOffset = startY + (squareLength + border_thickness) * (i / 3);

            if (game_field[i] == Game.Marking.CIRCLE) {
                // Draw a large blue oval and then a smaller one inside it, which is the color of
                // the background
                g2.setColor(Color.BLUE);
                g2.fillOval(colOffset + square5pct + halfBorder,
                    rowOffset + square5pct + halfBorder, square90pct, square90pct);
                g2.setColor(fieldColor);
                g2.fillOval(colOffset + square10pct + halfBorder,
                    rowOffset + square10pct + halfBorder, square80pct, square80pct);
            } else if (game_field[i] == Game.Marking.CROSS) {
                // Draw two lines to make a cross
                g2.setColor(Color.RED);
                int x = colOffset + square5pct + halfBorder;
                int y = rowOffset + square5pct + halfBorder;
                g2.drawLine(x, y, x + square90pct, y + square90pct);
                g2.drawLine(x + square90pct, y, x, y + square90pct);
            }

            // Update the region
            regions[i] = new Point(colOffset, rowOffset);
        }
    }
}
