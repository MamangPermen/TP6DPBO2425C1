import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class Logic implements ActionListener, KeyListener
{
    int frameWidth = 360; int frameHeight = 640;

    // komponen GUI
    View view;
    Player player;
    ArrayList<Pipe> pipes;
    private Font poppinsFont;

    // gambar
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player
    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    // pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -2;
    boolean gameOver = false;
    boolean gameStarted = false;
    int score = 0;
    ArrayList<Integer> passedPipePairs = new ArrayList<>();

    public Logic() // konstruktor
    {
        // load image
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<>();

        // timer spawn pipa
        pipesCooldown = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && gameStarted) {
                    placePipes();
                }
            }
        });
        pipesCooldown.start();

        // Timer utama
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    //  buat load fonts
    public Font loadPoppinsFont(float size) {
        try {
            if (poppinsFont == null) {
                poppinsFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("assets/Poppins-Regular.ttf"));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(poppinsFont);
            }
            return poppinsFont.deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    // Getter Setter
    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean GameOver() {
        return gameOver;
    }

    public boolean GameStarted() { 
        return gameStarted;
    }

    public Image getBackgroundImage() { 
        return backgroundImage; 
    }

    public int getScore() {
        return score;
    }

    // pasang pipa
    public void placePipes()
    {
        // buat posisi celah pipa
        int gapPosition = 150 + (int) (Math.random() * (frameHeight - 300));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, gapPosition - pipeHeight, pipeWidth, pipeHeight, upperPipeImage);
        Pipe lowerPipe = new Pipe(pipeStartPosX, gapPosition + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);

        pipes.add(upperPipe);
        pipes.add(lowerPipe);

        // Tambahkan penanda bahwa pasangan ini belum dilewati
        passedPipePairs.add(0);
    }

    public void move() 
    {
        if (gameOver) return; // Jika game over, hentikan semua logika

        // Jika game sudah dimulai, terapkan gravitasi pada pemain
        if (gameStarted) {
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            player.setPosY(Math.max(player.getPosY(), 0));
        }

        // jatuh ke tanah = game over
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver = true;
            return;
        }

        // Jika game sudah dimulai, proses pipa
        if (gameStarted) {
            for (int i = 0; i < pipes.size(); i += 2) {
                Pipe upperPipe = pipes.get(i);
                Pipe lowerPipe = pipes.get(i + 1);

                upperPipe.setPosX(upperPipe.getPosX() + pipeVelocityX);
                lowerPipe.setPosX(lowerPipe.getPosX() + pipeVelocityX);

                if (cekTabrakan(player, upperPipe) || cekTabrakan(player, lowerPipe)) {
                    gameOver = true;
                    return;
                }

                // skor saat lewat pipa
                int pairIndex = i / 2;
                if (upperPipe.getPosX() + upperPipe.getWidth() < player.getPosX()
                    && passedPipePairs.size() > pairIndex
                    && passedPipePairs.get(pairIndex) == 0) 
                {
                    score++;
                    passedPipePairs.set(pairIndex, 1);
                }
            }

            // hapus pipa off-screen
            for (int i = pipes.size() - 1; i >= 0; i--) {
                if (pipes.get(i).getPosX() + pipes.get(i).getWidth() < 0) {
                    pipes.remove(i);
                    if (i % 2 == 0 && i / 2 < passedPipePairs.size()) {
                        passedPipePairs.remove(i / 2);
                    }
                }
            }
        }
    }

    private boolean cekTabrakan(Player player, Pipe pipe) 
    {
        Rectangle playerBounds = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
        Rectangle pipeBounds = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
        return playerBounds.intersects(pipeBounds);
    }

    public void resetGame()
    {
        gameOver = false;
        gameStarted = false;
        score = 0;
        pipes.clear(); // Kosongkan daftar pipa
        passedPipePairs.clear(); // Kosongkan daftar pipa yang dilewati
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Pastikan timer berjalan ulang
        gameLoop.start();
        pipesCooldown.start();
    }

    public void returnToMenu() 
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        frame.getContentPane().removeAll();
        frame.add(new Menu(frame));
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    // Key listener
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        // space = flap
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            gameStarted = true;
            player.setVelocityY(-10);
        }

        // restart
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }

        // return to menu
        if (e.getKeyCode() == KeyEvent.VK_M && gameOver) {
            returnToMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}