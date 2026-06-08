package greenloop.ui;

import greenloop.dao.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {

    private final ProductDAO pDAO       = new ProductDAO();
    private final ClientDAO cDAO        = new ClientDAO();
    private final DeliveryAgentDAO aDAO = new DeliveryAgentDAO();
    private final OrderDAO oDAO         = new OrderDAO();

    // PREMIUM COLOR SYSTEM (Public so teammate panels can use them)
    public static final Color C_BG          = new Color(7,   13,  10);
    public static final Color C_BG2         = new Color(11,  19,  15);
    public static final Color C_SIDEBAR     = new Color(9,   16,  12);
    public static final Color C_CARD        = new Color(15,  25,  19);
    public static final Color C_ACCENT      = new Color(68,  218, 116); 
    public static final Color C_ACCENT_DIM  = new Color(40,  140,  72);
    public static final Color C_GOLD        = new Color(255, 200,  65);
    public static final Color C_DANGER      = new Color(255,  80,  80); 
    public static final Color C_DANGER_DIM  = new Color(140,  25,  25);
    public static final Color C_TEXT        = new Color(228, 243, 233);
    public static final Color C_TEXT_MID    = new Color(155, 195, 170);
    public static final Color C_TEXT_MUTED  = new Color(90,  128, 105);
    public static final Color C_BORDER      = new Color(28,  50,  38);
    public static final Color C_GLASS_SHEEN = new Color(255, 255, 255, 9);

    // FONT SYSTEM
    public static final Font F_BRAND  = new Font("Segoe UI", Font.BOLD,   22);
    public static final Font F_NAV    = new Font("Segoe UI", Font.BOLD,   13);
    public static final Font F_TITLE  = new Font("Segoe UI", Font.BOLD,   16);
    public static final Font F_LABEL  = new Font("Segoe UI", Font.BOLD,   12);
    public static final Font F_INPUT  = new Font("Segoe UI", Font.PLAIN,  13);
    public static final Font F_CLOCK  = new Font("Consolas",  Font.BOLD,   12);

    private JPanel       contentArea;
    private CardLayout   cardLayout; 
    private NavButton[]  navButtons;
    private JLabel       statusLabel;

    private static final int PARTICLE_COUNT = 45;
    private final List<Particle> particles = new ArrayList<>();

    private static class Particle {
        float x, y, size, speed, alpha, wobble, wobbleSpeed;
        int kind;
        void reset(int w, int h, boolean init) {
            x = (float)(Math.random() * w);
            y = init ? (float)(Math.random() * h) : h + 12f;
            size  = (float)(Math.random() * 3.5 + 0.8);
            speed = (float)(Math.random() * 0.55 + 0.18);
            alpha = (float)(Math.random() * 0.22 + 0.04);
            wobble      = (float)(Math.random() * Math.PI * 2);
            wobbleSpeed = (float)(Math.random() * 0.022 + 0.008);
            kind  = (int)(Math.random() * 3);
        }
    }

    public Dashboard() {
        setTitle("GreenLoop — Eco-Packaging Operations Centre");
        setSize(1300, 820);
        setMinimumSize(new Dimension(1100, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Particle p = new Particle();
            p.reset(1300, 820, true);
            particles.add(p);
        }

        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, C_BG, getWidth(), getHeight(), C_BG2));
                g2.fillRect(0, 0, getWidth(), getHeight());
                renderParticles(g2);
                g2.dispose();
            }
        };
        root.setOpaque(false);
        add(root);

        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildMain(),    BorderLayout.CENTER);

        new Timer(33, e -> {
            int w = root.getWidth(), h = root.getHeight();
            for (Particle p : particles) {
                p.y -= p.speed;
                p.wobble += p.wobbleSpeed;
                p.x += (float) Math.sin(p.wobble) * 0.35f;
                if (p.y < -20) p.reset(w, h, false);
            }
            root.repaint();
        }).start();

        showCard("products"); // Default screen
    }

    private void renderParticles(Graphics2D g2) {
        for (Particle p : particles) {
            Composite prev = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p.alpha));
            g2.setColor(C_ACCENT);
            int px = (int) p.x, py = (int) p.y, ps = Math.max(1, (int) p.size);
            g2.fillOval(px, py, ps, ps);
            g2.setComposite(prev);
        }
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(248, 0));
        sidebar.setBackground(C_SIDEBAR);

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        JLabel lbName = new JLabel("GreenLoop");
        lbName.setFont(F_BRAND); lbName.setForeground(C_TEXT);
        brand.add(lbName);
        sidebar.add(brand, BorderLayout.NORTH);

        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setOpaque(false);

        String[][] defs = {
            {"products",  "Products"},
            {"clients",   "Clients"},
            {"agents",    "Agents"},
            {"orders",    "Logistics"}
        };
        
        navButtons = new NavButton[defs.length];
        for (int i = 0; i < defs.length; i++) {
            final String card = defs[i][0];
            final int idx = i;
            NavButton btn = new NavButton(defs[i][1]);
            navButtons[i] = btn;
            btn.addActionListener(e -> { setActive(navButtons[idx]); showCard(card); });
            nav.add(btn);
        }
        sidebar.add(nav, BorderLayout.CENTER);
        return sidebar;
    }

    private void setActive(NavButton active) {
        for (NavButton b : navButtons) b.setActive(b == active);
    }

    private void showCard(String key) {
        cardLayout.show(contentArea, key);
    }

    private JPanel buildMain() {
        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(C_BG2);
        statusLabel = new JLabel("  GreenLoop Operations Centre");
        statusLabel.setFont(F_TITLE); statusLabel.setForeground(C_ACCENT);
        topBar.add(statusLabel, BorderLayout.WEST);
        main.add(topBar, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout); 
        contentArea.setOpaque(false);

        // HERE IS WHERE EVERYONE ELSES CODE CONNECTS TO YOURS
        contentArea.add(new ProductPanel(pDAO), "products");
        contentArea.add(new ClientPanel(cDAO),  "clients");
        contentArea.add(new AgentPanel(aDAO),   "agents");
        contentArea.add(new OrderPanel(oDAO, pDAO, cDAO, aDAO), "orders");

        main.add(contentArea, BorderLayout.CENTER);
        return main;
    }

    // Shared UI Component Builders
    public static JTextField sField() {
        JTextField tf = new JTextField();
        tf.setFont(F_INPUT); tf.setBackground(C_BG2); tf.setForeground(C_TEXT);
        tf.setCaretColor(C_ACCENT); tf.setBorder(BorderFactory.createLineBorder(C_BORDER));
        return tf;
    }

    public static void toast(String msg, Color c) {
        JOptionPane.showMessageDialog(null, msg, "System Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void styleBtn(JButton btn, Color c) {
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFont(F_LABEL);
        btn.setFocusPainted(false);
    }

    class NavButton extends JButton {
        private boolean active = false;
        NavButton(String label) {
            super(label);
            setFont(F_NAV);
            setForeground(C_TEXT_MUTED);
            setContentAreaFilled(false);
        }
        void setActive(boolean a) { 
            this.active = a; 
            setForeground(a ? C_ACCENT : C_TEXT_MUTED);
        }
    }
}