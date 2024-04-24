import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatDarkLaf;

public class MainGUI extends JFrame {
    private JPanel jpContenuto;
    private JPanel jpCategorie;
    private JComboBox<String> jcbCategorie;
    private JPanel jpBtnControlli;
    private ArrayList<JButton> btnControlli;
    private final String[] categorie = new String[] {
        "Scegli una categoria...", "Aggiornamenti",
        "Installazioni", "Configurazioni"
    };
    private final String[] txtBtnAggiornamenti = new String[] {
        "Abilita unattended upgrades", "Aggiorna i programmi installati"
    };
    private final String[] txtBtnInstallazioni = new String[] {
        "Installa i programmi essenziali", "Installa Apache (Server Web)",
        "Installa ed autoconfigura SSH"
    };
    private final String[] txtBtnConfigurazioni = new String[] {
        "Configura Apache (Server Web)"
    };

    public MainGUI() {
        // Frame principale
        this.setTitle("Dashboard Server AirStats");
        this.setSize(600, 600);
        // Location "di riserva": this.setLocation(150, 150);
        // Centra il Frame
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Icona programma
        this.setIconImage(new ImageIcon("assets/img/logo.png").getImage());
        // Col JAR: this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("assets/img/logo.png")).getImage());
        // Titolo nella finestra
        JLabel jlTitolo = new JLabel("Gestione server");
        jlTitolo.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitolo.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        this.add(jlTitolo, BorderLayout.NORTH);
        // Pannello contenuto
        this.jpContenuto = new JPanel(new GridBagLayout());
        this.add(this.jpContenuto, BorderLayout.CENTER);
        GridBagConstraints gbcContenuto = new GridBagConstraints();
        // Pannello JComboBox e JComboBox con le categorie di azioni
        this.jpCategorie = new JPanel();
        this.jcbCategorie = new JComboBox<>(this.categorie);
        this.jcbCategorie.addActionListener(new AscoltaCategoria());
        this.jpCategorie.add(this.jcbCategorie);
        gbcContenuto.gridx = 0;
        gbcContenuto.gridy = 0;
        gbcContenuto.fill = GridBagConstraints.VERTICAL;
        gbcContenuto.anchor = GridBagConstraints.NORTH;
        gbcContenuto.insets = new Insets(0, 0, 24, 0);
        this.jpContenuto.add(this.jpCategorie, gbcContenuto);
        // Creazione bottoni in base alla categoria scelta
        this.jpBtnControlli = new JPanel(new GridBagLayout());
        this.btnControlli = new ArrayList<>(0);
        int i = 0;
        // Bottoni sezione aggiornamenti
        for (String txt : this.txtBtnAggiornamenti) {
            JButton btn = new JButton(txt);
            btn.addActionListener(new AscoltaBtnAzioni());
            if (i == 1) {
                btn.setBackground(new Color(2, 110, 193, 150));
            }
            btn.setActionCommand("" + i);
            this.btnControlli.add(btn);
            i++;
        }
        // Bottoni sezione installazioni
        for (String txt : this.txtBtnInstallazioni) {
            JButton btn = new JButton(txt);
            btn.addActionListener(new AscoltaBtnAzioni());
            btn.setActionCommand("" + i);
            this.btnControlli.add(btn);
            i++;
        }
        // Bottoni sezione configurazioni
        for (String txt : this.txtBtnConfigurazioni) {
            JButton btn = new JButton(txt);
            btn.addActionListener(new AscoltaBtnAzioni());
            btn.setActionCommand("" + i);
            this.btnControlli.add(btn);
            i++;
        }
        gbcContenuto.gridy++;
        this.jpContenuto.add(this.jpBtnControlli, gbcContenuto);
        // Rendi visibile il frame
        this.setVisible(true);
    }

    class AscoltaCategoria implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            @SuppressWarnings("unchecked")
            // Fare riferimento all'array di stringhe "categorie", tra gli attributi di classe
            final int numCategoriaScelta = ((JComboBox<String>) e.getSource()).getSelectedIndex();

            // Preparazione constraints
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            int offset = 0;

            switch (numCategoriaScelta){
                case 0:
                    jpBtnControlli.removeAll();
                    jpBtnControlli.revalidate();
                    jpBtnControlli.repaint();
                    break;
                case 1: // Aggiornamenti
                    jpBtnControlli.removeAll();
                    jpBtnControlli.repaint();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    for (int i = 0; i < txtBtnAggiornamenti.length; i++) {
                        jpBtnControlli.add(btnControlli.get(i), gbc);
                        if (i % 2 == 0 && i != 0){
                            gbc.gridx = 0;
                            gbc.gridy++;
                        } else {
                            gbc.gridx++;
                        }
                    }
                    gbc.gridwidth = 2;
                    jpBtnControlli.revalidate();
                    jpBtnControlli.repaint();
                    break;
                case 2: // Installazioni
                    jpBtnControlli.removeAll();
                    jpBtnControlli.repaint();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    offset = txtBtnAggiornamenti.length;
                    for (int i = offset; i < btnControlli.size() - txtBtnConfigurazioni.length; i++) {
                        jpBtnControlli.add(btnControlli.get(i), gbc);
                        if ((i % 2 == 1 ? (i+1) % 2 == 0 : i % 2 == 0) && i != offset){
                            gbc.gridx = 0;
                            gbc.gridy++;
                        } else {
                            gbc.gridx++;
                        }
                    }
                    gbc.gridwidth = 2;
                    jpBtnControlli.revalidate();
                    jpBtnControlli.repaint();
                    break;
                case 3: // Configurazioni
                    jpBtnControlli.removeAll();
                    jpBtnControlli.repaint();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    offset = txtBtnAggiornamenti.length + txtBtnInstallazioni.length;
                    for (int i = offset; i < btnControlli.size(); i++) {
                        jpBtnControlli.add(btnControlli.get(i), gbc);
                        if ((i % 2 == 1 ? (i+1) % 2 == 0 : i % 2 == 0) && i != offset){
                            gbc.gridx = 0;
                            gbc.gridy++;
                        } else {
                            gbc.gridx++;
                        }
                    }
                    gbc.gridwidth = 2;
                    jpBtnControlli.revalidate();
                    jpBtnControlli.repaint();
                    break;
                default:
                    break;
            }
        }
    }

    class AscoltaBtnAzioni implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final int opCode = Integer.parseInt(e.getActionCommand());

            Runtime r = Runtime.getRuntime();

            switch (opCode) {
                case 0: // Abilita unattended upgrades
                /*
                    BufferedReader br;
                    try {
                        br = new BufferedReader(new FileReader("abilita_unattended_upgrades.sh"));
                    } catch (FileNotFoundException fnfex) {
                        System.err.println("ERRORE! File non trovato...\n\n");
                        fnfex.printStackTrace();
                        break;
                    }
                    String comando = "\n";
                    while (comando != null){
                        try {
                            comando = br.readLine();
                        } catch (IOException ioex) {
                            System.err.println("ERRORE! Si è verificato un problema nell'esecuzione del comando...\n\n");
                            ioex.printStackTrace();
                            break;
                        }
                        Process p = 
                    }
                    */
                    String[] comandi = null;
                    try {
                        comandi = new String(Files.readAllBytes(Paths.get("abilita_unattended_upgrades.sh"))).split("\n");
                    } catch (IOException ioex) {
                        System.err.println("ERRORE! C'è stato un problema nell'apertura del file...\n\n");
                        ioex.printStackTrace();
                        break;
                    }

                    if (comandi != null) {
                        try {
                            Process p = r.exec(comandi);
                            p.waitFor();
                        } catch (IOException ioex) {
                            System.err.println("ERRORE! C'è stato un problema nell'esecuzione dello script...\n\n");
                            ioex.printStackTrace();
                            break;
                        } catch (InterruptedException interrex) {
                            System.err.println("ERRORE! Il processo è stato interrotto...\n\n");
                            interrex.printStackTrace();
                            break;
                        }
                    } else {
                        break;
                    }
                    break;
            
                default:
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
    	FlatDarkLaf.setup();
        new MainGUI();
    }
}