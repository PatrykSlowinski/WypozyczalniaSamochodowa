package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.klient;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.showMessageDialog;


public class KlientZmianaLoginu extends JFrame {
    private final Repos repos;
    private final Klient zalogowanyKlient;
    private final KlientForm klientForm;
    private JPanel panel1;
    private JPanel panel;
    private JTextField textFieldLogin;
    private JButton buttonZapisz;
    private JButton buttonAnuluj;

    public KlientZmianaLoginu(Repos repos, Klient zalogowanyKlient, KlientForm klientForm) {
        this.repos = repos;
        this.zalogowanyKlient = zalogowanyKlient;
        this.klientForm = klientForm;

        setTitle("Zmiana loginu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));
        setResizable(false);
        add(panel1);
        pack();
        setLocationRelativeTo(null);
        buttonZapisz.addActionListener(e -> zmienLogin());
        buttonAnuluj.addActionListener(e -> anuluj());
    }
    private void zmienLogin(){
        Optional<Klient> klientOptional = repos.getKlientRepository().findByLogin(textFieldLogin.getText());
        if (textFieldLogin.getText().isEmpty()) {
            showMessageDialog(null, "Login nie może być pusty!!");
        }
        else if(textFieldLogin.getText().equals(zalogowanyKlient.getLogin())){
            showMessageDialog(null, "Podany przez Ciebie login jest taki sam jak aktualny login!");
        }
        else if (klientOptional.isPresent()) {
            showMessageDialog(null, "Podany login jest już zajęty!");
        }
        else {
            showMessageDialog(null, "Login zmieniony!");
            zalogowanyKlient.setLogin(textFieldLogin.getText());
            repos.getKlientRepository().save(zalogowanyKlient);
            klientForm.zaladujDanePonownie();
            dispose();
        }
    }
    private void anuluj() {
        dispose();
    }

}

