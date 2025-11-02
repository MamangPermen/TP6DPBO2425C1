import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Menu extends JPanel 
{
    private JButton startButton;
    private JButton quitButton;
    private JFrame parentFrame;
    private Image backgroundImage;
    private Font poppinsFont;

    public Menu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        // Background
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();

        // Font Poppins
        poppinsFont = loadPoppinsFont("assets/Poppins-Regular.ttf", 36f);

        // Judul
        JLabel title = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        title.setFont(poppinsFont.deriveFont(Font.BOLD, 48f));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 150, 360, 60);
        add(title);

        // Tombol Start
        startButton = createStyledButton("START");
        startButton.setBounds(80, 300, 200, 50);
        startButton.addActionListener(e -> startGame());
        add(startButton);

        // Tombol Quit
        quitButton = createStyledButton("EXIT GAME");
        quitButton.setBounds(80, 370, 200, 50);
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
    }

    // load font
    private Font loadPoppinsFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                getClass().getResourceAsStream(path));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font.deriveFont(size);
        } catch (Exception e) {
            System.err.println("Gagal memuat Poppins font, gunakan fallback.");
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    //  load tombol
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Tambahkan efek hover: ubah warna saat mouse masuk/keluar
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(50, 160, 50));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(34, 139, 34));
            }
        });
        return button;
    }

    private void startGame() {
        parentFrame.getContentPane().removeAll();

        Logic logic = new Logic();
        View view = new View(logic);
        logic.setView(view);

        parentFrame.add(view);
        view.requestFocusInWindow();
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}