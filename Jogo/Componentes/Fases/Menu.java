package Jogo.Componentes.Fases;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.Timer;

import Jogo.Container;

public class Menu extends JPanel implements ActionListener {

    private Clip clip;
    private Image fundoMenu;
    private Image fundo2;
    private Image cadeado;
    private Image esc;
    private Image space;
    private Image caixa;
    private Image caixaSelecionada;

    private Timer timer;

    private int opcaoSelecionada;
    private int opcaoStart;
    private int opcaoFase;

    public static boolean doisJogadores;
    private boolean selecionarFase = false;
    private boolean startGame = false;
    private boolean inicializaControles = false;
    private boolean faseSelecionada = false;

    public static String nomeJogador1 = "Jogador 1";
    public static String nomeJogador2 = "Jogador 2";

    TecladoAdapter teclado = new TecladoAdapter();
    private Container container;

    public Menu(Container container) {
        this.container = container;
        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon referencia = new ImageIcon("planosFundo//fundomenu.png");
        fundoMenu = referencia.getImage();

        referencia = new ImageIcon("planosFundo//blackground2.0.png");
        fundo2 = referencia.getImage();
        referencia = new ImageIcon("assets//cadeado.png");
        cadeado = referencia.getImage();
        referencia = new ImageIcon("assets//esc.png");
        esc = referencia.getImage();
        referencia = new ImageIcon("assets//space.png");
        space = referencia.getImage();
        referencia = new ImageIcon("assets//caixa.png");
        caixa = referencia.getImage();
        referencia = new ImageIcon("assets//caixaSelecionada.png");
        caixaSelecionada = referencia.getImage();

        addKeyListener(teclado);

        timer = new Timer(5, this);
        timer.start();

        try {
            File audioFile2 = new File("assets//interstellar.wav");
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);

            clip = AudioSystem.getClip();
            clip.open(audioStream2);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        startSound();
    }

    public void startSound() {
        if (clip != null) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }

    private static Font loadFont(String path, float size) {
        try {
            File fontFile = new File(path);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            return font.deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    public void paint(Graphics g) {
        Graphics2D graficos = (Graphics2D) g;

        Font fonte = loadFont("assets//PressStart2P.ttf", 16);
        Font fonte2 = loadFont("assets//PressStart2P.ttf", 14);
        Font fonte3 = loadFont("assets//PressStart2P.ttf", 10);

        g.setFont(fonte);
        g.setColor(Color.WHITE);

        graficos.drawImage(fundoMenu, 0, 0, getWidth(), getHeight(), this);

        if (!inicializaControles && !startGame) {
            graficos.drawString("S T A R T", 690, 700);
            graficos.drawString("E X I T", 700, 750);

            graficos.drawString(">", 660 + (opcaoStart * 25) - 20, 700 + opcaoStart * 50);
        }

        if (inicializaControles || startGame) {
            graficos.drawString("Levels", 695, 600);
            graficos.drawString("Single-Player", 650, 650);
            graficos.drawString("Two Players", 660, 700);
            graficos.drawString("B A C K", 690, 750);

            if (!inicializaControles) {
                graficos.drawString(">", 600 + (opcaoSelecionada * 25) - 20, 600 + opcaoSelecionada * 50);
            }
        }

        if (selecionarFase) {
            graficos.drawImage(fundo2, 470, 260, this);

            graficos.drawString("Selecione a Fase", 630, 300);

            g.setFont(fonte2);
            g.setColor(Color.WHITE);

            graficos.drawString("Level 1: Terra", 660, 365);
            graficos.drawImage(caixa, 890, 350, this);
            graficos.drawString("Level 2: Marte", 660, 440);
            if (Fase1.faseCompleta1){
                graficos.drawImage(caixa, 890, 425, this);
            }
            graficos.drawString("Level 3: Europa", 660, 515);
            if (Fase2.faseCompleta2){
                graficos.drawImage(caixa, 890, 500, this);
            }

            if (faseSelecionada) {
                if (container.getFaseAtual() == 1) {
                    graficos.drawImage(caixaSelecionada, 890, 350, container);
                }
                if (container.getFaseAtual() == 2){
                    graficos.drawImage(caixaSelecionada, 890, 425, container);
                }
                if (container.getFaseAtual() == 3){
                    graficos.drawImage(caixaSelecionada, 890, 500, container);
                }
            }

            if (!Fase1.faseCompleta1){
                graficos.drawImage(cadeado, 875, 415, this);
            }
            if (!Fase2.faseCompleta2){
                graficos.drawImage(cadeado, 890, 490, this);
            }

            graficos.drawString(">", 630, 365 + opcaoFase * 75);

            graficos.drawImage(esc, 500, 300, this);
            graficos.drawImage(space, 970, 300, this);

            g.setFont(fonte3);
            g.setColor(Color.WHITE);

            graficos.drawString("Return", 490, 345);
            graficos.drawString("Select", 980, 345);
        }
    }

    public void capturarNomes() {
        JDialog dialog = new JDialog();

        dialog.setSize(600, 300);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon("planosFundo//blackground2.0.png");

        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());
        dialog.setContentPane(backgroundLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        dialog.add(panel);

        JTextField textField1 = new JTextField(10);
        JTextField textField2 = new JTextField(10);

        Font pressStartFont = loadFont("assets//PressStart2P.ttf", 16);
        textField1.setFont(pressStartFont);
        textField2.setFont(pressStartFont);

        textField1.setOpaque(false);
        textField1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        textField1.setForeground(Color.WHITE);

        JLabel label1 = new JLabel("Player 1:");
        label1.setForeground(Color.WHITE);
        label1.setFont(pressStartFont);

        panel.add(label1, gbc);
        gbc.gridy++;
        panel.add(textField1, gbc);
        gbc.gridy++;

        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField1.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            }
        });

        if (doisJogadores) {

            textField2.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    textField2.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    textField2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                }
            });
        }

        if (doisJogadores) {
            textField2.setOpaque(false);
            textField2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            textField2.setForeground(Color.WHITE);

            JLabel label2 = new JLabel("Player 2:");
            label2.setForeground(Color.WHITE);
            label2.setFont(pressStartFont);
            panel.add(label2, gbc);
            gbc.gridy++;
            panel.add(textField2, gbc);
        }

        gbc.gridy++;

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Back");

        okButton.setFont(pressStartFont);
        cancelButton.setFont(pressStartFont);
        gbc.gridy++;
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(Color.BLACK);
        okButton.setOpaque(true);
        okButton.setContentAreaFilled(false);
        okButton.setBorderPainted(false);

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorderPainted(false);

        okButton.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                okButton.setForeground(Color.YELLOW);
            }

            @Override
            public void focusLost(FocusEvent e) {
                okButton.setForeground(Color.WHITE);
            }
        });

        cancelButton.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cancelButton.setForeground(Color.YELLOW);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cancelButton.setForeground(Color.WHITE);
            }
        });

        okButton.addActionListener(e -> {
            nomeJogador1 = textField1.getText().isEmpty() ? "Player 1" : textField1.getText();
            if (doisJogadores) {
                nomeJogador2 = textField2.getText().isEmpty() ? "Player 2" : textField2.getText();
            }
            stopSound();
            container.iniciarJogo();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        panel.add(okButton, gbc);
        gbc.gridy++;
        panel.add(cancelButton, gbc);

        textField1.setFocusable(true);
        textField1.requestFocusInWindow();
        textField1.setFocusTraversalKeysEnabled(false);
        textField2.setFocusTraversalKeysEnabled(false);

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusNextComponent");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusPreviousComponent");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");

        actionMap.put("focusNextComponent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
            }
        });

        actionMap.put("focusPreviousComponent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
            }
        });

        actionMap.put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (okButton.isFocusOwner()) {
                    okButton.doClick();
                } else if (cancelButton.isFocusOwner()) {
                    cancelButton.doClick();
                }
            }
        });

        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class TecladoAdapter implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            int codigo = e.getKeyCode();

            if (!startGame) {
                switch (codigo) {
                    case KeyEvent.VK_UP:
                        if (opcaoStart > 0) {
                            opcaoStart--;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (opcaoStart < 1) {
                            opcaoStart++;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        opcaoStart();
                        break;
                }
                if (inicializaControles) {
                    switch (codigo) {
                        case KeyEvent.VK_UP:
                            startGame = true;
                            inicializaControles = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            startGame = true;
                            inicializaControles = false;
                            break;
                    }
                }
            }
            if (startGame) {
                if (!selecionarFase) {
                    switch (codigo) {
                        case KeyEvent.VK_UP:
                            if (opcaoSelecionada > 0) {
                                opcaoSelecionada--;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (opcaoSelecionada < 3) {
                                opcaoSelecionada++;
                            }
                            break;
                        case KeyEvent.VK_ENTER:
                            selecionarOpcao();
                            break;
                    }
                }

                if (selecionarFase) {
                    switch (codigo) {
                        case KeyEvent.VK_UP:
                            if (opcaoFase > 0) {
                                opcaoFase--;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (opcaoFase < 2) {
                                opcaoFase++;
                            }
                            break;
                        case KeyEvent.VK_SPACE:
                            selecionarFase();
                            faseSelecionada = true;
                            break;
                        case KeyEvent.VK_ESCAPE:
                            selecionarFase = false;
                            break;
                    }
                }
            }
            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private void opcaoStart() {
        switch (opcaoStart) {
            case 0:
                inicializaControles = true;
                break;
            case 1:
                System.exit(0);
                break;
        }

    }

    private void selecionarOpcao() {
        switch (opcaoSelecionada) {
            case 0:
                selecionarFase = true;
                break;
            case 1:
                doisJogadores = false;
                capturarNomes();
                break;
            case 2:
                doisJogadores = true;
                capturarNomes();
                break;
            case 3:
                startGame = false;
                break;
        }

    }

    private void selecionarFase() {
        switch (opcaoFase) {
            case 0:
                container.selecionarFase(opcaoFase);
                break;
            case 1:
                if (Fase1.faseCompleta1){
                    container.selecionarFase(opcaoFase);
                }
                break;
            case 2:
                if (Fase2.faseCompleta2){
                    container.selecionarFase(opcaoFase);
                }
                break;
        }
    }
}