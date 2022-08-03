package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.klient;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Auto;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.wypozyczenie.Wypozyczenie;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main.AutoService;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main.WypozyczenieService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class KlientForm extends JFrame {

    private final Repos repos;
    private final AutoService autoService;
    private final WypozyczenieService wypozyczenieService;


    private Klient zalogowanyKlient;
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane;
    private JTable tableWypozyczenie;
    private JButton buttonRezerwuj;
    private DateTimePicker dateTimePickerOd;
    private DateTimePicker dateTimePickerDo;
    private JComboBox comboBoxSamochod;
    private JLabel labelCenaValue;
    private JTextField textFieldImie;
    private JTextField textFieldNazwisko;
    private JTextField textFieldPesel;
    private JTextField textFieldLogin;
    private JPasswordField passwordField1;
    private JButton buttonZmienLogin;
    private JButton buttonZmienHaslo;
    private JButton buttonAnuluj;

    public KlientForm(Repos repos, Klient zalogowanyKlient) {
        this.repos = repos;
        this.zalogowanyKlient = zalogowanyKlient;
        this.autoService = new AutoService(repos);
        this.wypozyczenieService= new WypozyczenieService(repos);

        setTitle("Wypożyczalnia samochodowa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500, 800));
        setResizable(false);
        add(panel);
        zaladujAuta();
        zaladujWypozyczeniaKlienta();

        dateTimePickerOd.timePicker.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(0,0,0),LocalTime.of(23,59,59));
        dateTimePickerDo.timePicker.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(0,0,0),LocalTime.of(23,59,59));

        pack();
        repaint();
        setLocationRelativeTo(null);
        this.setVisible(true);
        uzupelnijDaneKontaKlienta(zalogowanyKlient);

        buttonZmienLogin.addActionListener(e -> zmianaLoginu());
        buttonZmienHaslo.addActionListener(e-> zmianaHasla());

        dateTimePickerOd.timePicker.addTimeChangeListener(e-> policzKoszt());
        dateTimePickerOd.datePicker.addDateChangeListener(e -> policzKoszt());

        dateTimePickerDo.timePicker.addTimeChangeListener(e ->policzKoszt());
        dateTimePickerDo.datePicker.addDateChangeListener(e ->policzKoszt());

        buttonRezerwuj.addActionListener(e->rezerwuj());
        buttonAnuluj.addActionListener(e->anuluj());

        comboBoxSamochod.addActionListener(e -> policzKoszt());
    }
    private void uzupelnijDaneKontaKlienta(Klient klient) {
        textFieldImie.setText(klient.getImie());
        textFieldNazwisko.setText(klient.getNazwisko());
        textFieldPesel.setText(klient.getPesel());
        textFieldLogin.setText(klient.getLogin());
        passwordField1.setText(klient.getHaslo());
    }
    private void zmianaLoginu() {
            KlientZmianaLoginu klientZmianaLoginu= new KlientZmianaLoginu(repos, zalogowanyKlient, this);
            klientZmianaLoginu.setVisible(true);
    }
    private void zmianaHasla() {
        KlientZmianaHasla klientZmianaHasla= new KlientZmianaHasla(repos, zalogowanyKlient, this);
        klientZmianaHasla.setVisible(true);
    }
    void zaladujDanePonownie(){
        textFieldLogin.setText(zalogowanyKlient.getLogin());
        passwordField1.setText(zalogowanyKlient.getHaslo());
    }


    private void zaladujAuta(){
      comboBoxSamochod.setModel(autoService.comboBoxAuta());
    }

    private void zaladujWypozyczeniaKlienta()
    {
        tableWypozyczenie.setModel(wypozyczenieService.wypozyczenieKlientTabela(zalogowanyKlient.getId()));
    }

    private boolean wszystkieDatyWypelnione()
    {
         return !Objects.isNull(dateTimePickerDo.datePicker.getDate())
                && !Objects.isNull(dateTimePickerOd.datePicker.getDate())
                && !Objects.isNull(dateTimePickerOd.timePicker.getTime())
                && !Objects.isNull(dateTimePickerDo.timePicker.getTime());
    }

    private void policzKoszt(){
        int koszt=0;

        if(wszystkieDatyWypelnione())
        {
            LocalDateTime dataOd = dateTimePickerOd.getDateTimeStrict();
            LocalDateTime dataDo = dateTimePickerDo.getDateTimeStrict();

           int hourDifference = (int) ChronoUnit.HOURS.between(dataOd,dataDo);
           if(comboBoxSamochod.getSelectedIndex() !=-1 && hourDifference > 0) {
               var wybraneAuto = (Auto) comboBoxSamochod.getSelectedItem();
               koszt = hourDifference * wybraneAuto.getCenaZaGodzine();
           }
        }
        labelCenaValue.setText(koszt +" zł");
     }


     private void rezerwuj()
     {
        if (!wszystkieDatyWypelnione() && comboBoxSamochod.getSelectedIndex()!=-1)
        {
           JOptionPane.showMessageDialog(this, "Proszę uzupełnić wszystkie pola");
           return;
        }
        LocalDateTime dataOd = dateTimePickerOd.getDateTimeStrict();
        LocalDateTime dataDo = dateTimePickerDo.getDateTimeStrict();

        if (dataOd.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(this, "Data początku wypożyczenia musi być datą przyszłą");
            return;
        }
        if (!dataOd.isBefore(dataDo)) {
            JOptionPane.showMessageDialog(this, "Data początku wypożyczenia musi być datą wcześniejszą od daty końca wypożyczenia");
            return;
        }
        if(wybranaDataJestZajęta(dataOd, dataDo))
        {
            JOptionPane.showMessageDialog(this, "Samochód jest niedostepny w wybranym okresie czasowym");
            return;
        }

        Wypozyczenie wypozyczenie = stworzWypozyczenie(dataOd,dataDo,(Auto)(comboBoxSamochod.getSelectedItem()));
        repos.getWypozyczenieRepository().save(wypozyczenie);
        zaladujWypozyczeniaKlienta();
     }

     private boolean wybranaDataJestZajęta(LocalDateTime dataOd, LocalDateTime dataDo)
     {
        List<Wypozyczenie> wypozyczenia =  repos.getWypozyczenieRepository().findByAutoId(((Auto)comboBoxSamochod.getSelectedItem()).getId());

        Optional<Wypozyczenie> wypozyczenieZajete = wypozyczenia.stream()
                 .filter(wypozyczenie -> dataWczesniejszaLubTakaSama(dataOd,wypozyczenie.getDataKoncowa()) && dataWczesniejszaLubTakaSama(wypozyczenie.getDataPoczatkowa(),dataDo))
                 .findFirst();
         return wypozyczenieZajete.isPresent();
     }

     private Wypozyczenie stworzWypozyczenie( LocalDateTime dataOd, LocalDateTime dataDo, Auto auto)
     {
         return new Wypozyczenie(dataOd,dataDo,zalogowanyKlient,auto,Integer.parseInt(labelCenaValue.getText().substring(0,labelCenaValue.getText().length() - 3)));
     }

    private boolean dataWczesniejszaLubTakaSama(LocalDateTime data1, LocalDateTime data2){
        return data1.isBefore(data2) || data1.isEqual(data2);
    }

    public Wypozyczenie pobierzWypozyczenieZTabeli(JTable tableWypozyczenie) {
        if (tableWypozyczenie.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Nie wybrano wiersza");
            return null;
        }
        Integer id = (int) tableWypozyczenie.getValueAt(tableWypozyczenie.getSelectedRow(), 0);
        Optional<Wypozyczenie> wypozyczenie = repos.getWypozyczenieRepository().findById(id);
        if (wypozyczenie.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Brak wypozyczenia o takim Id");
            return null;
        }
        return wypozyczenie.get();
    }

    private void anuluj() {
        Wypozyczenie wypozyczenie = pobierzWypozyczenieZTabeli(tableWypozyczenie);
        if (wypozyczenie == null) {
            return;
        }
        repos.getWypozyczenieRepository().deleteById(wypozyczenie.getId());
        zaladujWypozyczeniaKlienta();
    }
}
