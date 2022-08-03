package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.rejestracja;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Plec;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static edu.uni.lodz.pl.WypozyczalniaSamochodowa.utils.Validators.hasloValidator;
import static edu.uni.lodz.pl.WypozyczalniaSamochodowa.utils.Validators.peselValidator;
import static javax.swing.JOptionPane.showMessageDialog;

@Component
public class Rejestracja extends JFrame {
    private final Repos repos;
    private JTextField textFieldImie;
    private JTextField textFieldNazwisko;
    private JTextField textFieldPesel;
    private JTextField textFieldLogin;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton buttonUtworzKonto;
    private JButton buttonAnuluj;
    private JPanel panel;
    private JRadioButton radioButtonKobieta;
    private JRadioButton radioButtonMezczyzna;

    public Rejestracja(Repos repos) {
        this.repos = repos;

        setTitle("Rejestracja");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        buttonUtworzKonto.addActionListener(e -> utworzKonto());
        buttonAnuluj.addActionListener(e -> anuluj());

    }

    private void anuluj() {
        dispose();
    }

    private void utworzKonto() {
        Plec plec;
        if(radioButtonKobieta.isSelected()) { plec = Plec.KOBIETA; }
        else { plec=Plec.MEZCZYZNA; }

        Optional<Klient> klientOptional = repos.getKlientRepository().findByLogin(textFieldLogin.getText());
        Optional<Klient> klientOptional1 = repos.getKlientRepository().findByPesel(textFieldPesel.getText());
        if (klientOptional.isPresent()) {
            showMessageDialog(null, "Podany login jest już zajęty!");
        }
        else if(klientOptional1.isPresent()) {
            showMessageDialog(null, "Podany pesel jest już użyty przez innego użytkownika!");
        }
        else if (textFieldImie.getText().isEmpty() || textFieldNazwisko.getText().isEmpty() || textFieldPesel.getText().isEmpty() || textFieldLogin.getText().isEmpty() || String.copyValueOf(passwordField1.getPassword()).isEmpty() || String.copyValueOf(passwordField2.getPassword()).isEmpty()) {
            showMessageDialog(null, "Wypełnij wszystkie pola!");
        }
        else if (!(String.copyValueOf(passwordField1.getPassword()).equals(String.copyValueOf(passwordField2.getPassword())))) {
            showMessageDialog(null, "Podane hasło i powtórzone hasło nie są takie same!");
        }
        else if(!peselValidator(textFieldPesel.getText())){
            showMessageDialog(null, "Błędny pesel!");
        }
        else if(!hasloValidator(String.copyValueOf(passwordField1.getPassword()))){
            showMessageDialog(null,"Hasło musi zawierać od 8 do 20 znaków, minimum jedną małą literę, dużą literę, cyfrę i symbol!" );
        }
        else {
            Klient k1 = new Klient(textFieldImie.getText(), textFieldNazwisko.getText(), textFieldPesel.getText(), textFieldLogin.getText(), String.copyValueOf(passwordField1.getPassword()), plec);
            showMessageDialog(null, "Konto utworzone!");
            repos.getKlientRepository().save(k1);
            dispose();
        }
    }
}




