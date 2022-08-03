package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.logowanie;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.Pracownik;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.klient.KlientForm;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main.Main;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.rejestracja.Rejestracja;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Component
public class Logowanie extends JFrame {
    private final Repos repos;
    private JPanel panel;
    private JButton buttonZaloguj;
    private JButton buttonAnuluj;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JButton buttonNoweKonto;


    public Logowanie(Repos repos) {
        this.repos = repos;

        setTitle("Logowanie");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 250));
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        buttonZaloguj.addActionListener(e -> zaloguj());
        buttonAnuluj.addActionListener(e -> anuluj());
        buttonNoweKonto.addActionListener(e-> utworzKonto(repos));
    }

    private void zaloguj() {
        Optional<Pracownik> pracownikOptional = repos.getPracownikRepository().findByLoginAndHaslo(textFieldLogin.getText(), String.copyValueOf(passwordField.getPassword()));
        if (pracownikOptional.isPresent()) {
            new Main(repos, pracownikOptional.get());
            dispose();
            return;
        }

        Optional<Klient> klientOptional = repos.getKlientRepository().findByLoginAndHaslo(textFieldLogin.getText(), String.copyValueOf(passwordField.getPassword()));
        if (klientOptional.isPresent()) {
            new KlientForm(repos, klientOptional.get());
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(panel, "Zła nazwa użytkownika lub hasło");
    }

    private void anuluj() {
        System.exit(0);
    }
    private void utworzKonto(Repos repos) {
        Rejestracja rejestracja = new Rejestracja(repos);
        rejestracja.setVisible(true);
        //dispose();
    }
}
