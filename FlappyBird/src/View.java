import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel 
{
    private Logic logic;
    private final int width = 360;
    private final int height = 640;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Background
        Image bg = logic.getBackgroundImage();
        if (bg != null) {
            g.drawImage(bg, 0, 0, width, height, null);
        }

        // Player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                        player.getWidth(), player.getHeight(), null);
        }

        // Pipes
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                            pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Skor selama bermain (di tengah atas)
        if (!logic.GameOver()) {
            String scoreText = String.valueOf(logic.getScore());
            g.setFont(logic.loadPoppinsFont(24f));
            g.setColor(Color.WHITE);
            int textWidth = g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, (width - textWidth) / 2, 40);
        }

        // Teks instruksi atau game over
        if (!logic.GameStarted() && !logic.GameOver()) {
            drawCenteredText(g, "Press SPACE to Start", 24f, Color.WHITE, height / 2);
        } else if (logic.GameOver()) {
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics g) {
        // Final Score
        String finalScore = "Score: " + logic.getScore();
        drawCenteredText(g, finalScore, 32f, Color.WHITE, height / 2 - 80);

        // GAME OVER
        drawCenteredText(g, "GAME OVER", 48f, Color.RED, height / 2 - 20);

        // Restart
        drawCenteredText(g, "Press R to Restart", 20f, Color.WHITE, height / 2 + 30);

        // Return to Menu
        drawCenteredText(g, "Press M to Return to Menu", 20f, Color.WHITE, height / 2 + 60);
    }

    private void drawCenteredText(Graphics g, String text, float fontSize, Color color, int y) {
        g.setFont(logic.loadPoppinsFont(fontSize));
        g.setColor(color);
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (width - textWidth) / 2, y);
    }
}