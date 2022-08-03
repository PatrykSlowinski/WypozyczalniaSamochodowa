package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Plec;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Auto;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Nadwozie;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Paliwo;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Skrzynia;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.godziny_pracy.GodzinyPracy;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.Pracownik;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static edu.uni.lodz.pl.WypozyczalniaSamochodowa.utils.Validators.hasloValidator;
import static edu.uni.lodz.pl.WypozyczalniaSamochodowa.utils.Validators.peselValidator;
import static javax.swing.JOptionPane.showMessageDialog;


public class Main extends JFrame {
    private final Repos repos;
    private final AutoService autoService;
    private final PracownikService pracownikService;
    private final KlientService klientService;
    private final Pracownik zalogowanyPracownik;
    private final WypozyczenieService wypozyczenieService;
    private final GodzinyService godzinyService;

    private Integer idWybranegoAuta;
    private Integer idWybranegoPracownika;

    //<editor-fold desc="UI">
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JTable tablePracownicy;
    private JScrollPane scrollPane;
    private JButton buttonPracownicyUsun;
    private JButton buttonPracownicyDodaj;
    private JButton buttonPracownicyEdytuj;
    private JTable tableAuta;
    private JButton buttonAutaDodaj;
    private JButton buttonAutaEdytuj;
    private JButton buttonAutaUsun;
    private JSpinner spinnerAutoCenaZaGodzine;
    private JTextField textFieldAutoMarka;
    private JTextField textFieldAutoModel;
    private JSpinner spinnerAutoRokProdukcji;
    private JComboBox<Nadwozie> comboBoxAutoNadwozie;
    private JComboBox<Paliwo> comboBoxAutoPaliwo;
    private JComboBox<Skrzynia> comboBoxAutoSkrzynia;
    private JTextField textFieldPracownikImie;
    private JTextField textFieldPracownikNazwisko;
    private JTextField textFieldPracownikPesel;
    private JTextField textFieldPracownikLogin;
    private JComboBox<Plec> comboBoxPracownikPlec;
    private JTable tableKlienci;
    private JTextField textFieldSzukajKlienta;
    private JButton buttonSzukajKlienta;
    private JButton button10_18;
    private JButton button9_17;
    private JButton button8_16;
    private JButton buttonDodajGodzinyPracy;
    private JButton buttonEdytujGodzinyPracy;
    private JButton buttonUsuńGodzinyPracy;
    private JButton button7_15;
    private JTextField textFieldSzukajGodziny;
    private JComboBox comboBoxGodzinyPracyPracownicy;
    private JTable tableRezerwacje;
    private TimePicker timePickerPoniedzialekOdGodzinyPracy;
    private TimePicker timePickerPoniedzialekDoGodzinyPracy;
    private TimePicker timePickerWtorekOdGodzinyPracy;
    private TimePicker timePickerWtorekDoGodzinyPracy;
    private TimePicker timePickerSrodaOdGodzinyPracy;
    private TimePicker timePickerSrodaDoGodzinyPracy;
    private TimePicker timePickerCzwartekOdGodzinyPracy;
    private TimePicker timePickerCzwartekDoGodzinyPracy;
    private TimePicker timePickerPiatekOdGodzinyPracy;
    private TimePicker timePickerPiatekDoGodzinyPracy;
    private TimePicker timePickerSobotaOdGodzinyPracy;
    private TimePicker timePickerSobotaDoGodzinyPracy;
    private JPasswordField passwordFieldPracownikHaslo;
    private DefaultTableColumnModel model;
    private JLabel jlabel;
    private java.sql.Time Time;
    //</editor-fold>

    public Main(Repos repos, Pracownik zalogowanyPracownik) {
        this.repos = repos;
        this.zalogowanyPracownik = zalogowanyPracownik;
        this.pracownikService = new PracownikService(repos);
        this.autoService = new AutoService(repos);
        this.klientService = new KlientService(repos);
        this.wypozyczenieService = new WypozyczenieService(repos);

        this.godzinyService = new GodzinyService(repos);
        setTitle("Wypożyczalnia samochodowa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500, 600));
        setResizable(false);
        add(panel);
        zaladujDane();
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);


        //<editor-fold desc="ComboBoxes">
        comboBoxPracownikPlec.setModel(new DefaultComboBoxModel<>(Plec.values()));
        comboBoxAutoNadwozie.setModel(new DefaultComboBoxModel<Nadwozie>(Nadwozie.values()));
        comboBoxAutoPaliwo.setModel(new DefaultComboBoxModel<Paliwo>(Paliwo.values()));
        comboBoxAutoSkrzynia.setModel(new DefaultComboBoxModel<Skrzynia>(Skrzynia.values()));
        comboBoxGodzinyPracyPracownicy.addActionListener(e -> zaladujDaneGodzinPracy());
        //</editor-fold>

        //<editor-fold desc="Buttons">
        buttonPracownicyDodaj.addActionListener(e -> dodajPracownika());
        buttonPracownicyEdytuj.addActionListener(e -> edytujPracownika());
        buttonPracownicyUsun.addActionListener(e -> usunPracownika());
        tablePracownicy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zaladujDaneWybranegoPracownika(); odblokujDodajEdytujPracownika();
            }
        });

        buttonAutaDodaj.addActionListener(e -> dodajAuto());
        buttonAutaEdytuj.addActionListener(e -> edytujAuto());
        buttonAutaUsun.addActionListener(e -> usunAuto());
        tableAuta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zaladujDaneWybranegoAuta();
            }
        });
        buttonSzukajKlienta.addActionListener(e -> zaladujDaneSzukanegoKlienta());
        buttonDodajGodzinyPracy.addActionListener(e -> dodajGodzinyPracy());
        buttonEdytujGodzinyPracy.addActionListener(e -> edytujGodzinyPracy());
        buttonUsuńGodzinyPracy.addActionListener(e -> usunGodzinyPracy());
        button7_15.addActionListener(e -> czasPracy(7));
        button8_16.addActionListener(e -> czasPracy(8));
        button9_17.addActionListener(e -> czasPracy(9));
        button10_18.addActionListener(e -> czasPracy(10));
        //</editor-fold>

        //<editor-fold desc="Spinners">
        spinnerAutoCenaZaGodzine.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        spinnerAutoRokProdukcji.setModel(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR) - 5, 1900, Calendar.getInstance().get(Calendar.YEAR), 1));

        JSpinner.DefaultEditor spinnerEditorAutoCenaZaGodzine = (JSpinner.DefaultEditor) (spinnerAutoCenaZaGodzine.getEditor());
        spinnerEditorAutoCenaZaGodzine.getTextField().setHorizontalAlignment(JTextField.LEFT);

        spinnerAutoRokProdukcji.setEditor(new JSpinner.NumberEditor(spinnerAutoRokProdukcji, "#"));
        JSpinner.NumberEditor spinnerEditorAutoRokProdukcji = (JSpinner.NumberEditor) (spinnerAutoRokProdukcji.getEditor());
        spinnerEditorAutoRokProdukcji.getTextField().setHorizontalAlignment(JTextField.LEFT);
        //</editor-fold>

        //<editor-fold desc="Time pickers">
        timePickerPoniedzialekOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerPoniedzialekDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerWtorekOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerWtorekDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerSrodaOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerSrodaDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerCzwartekOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerCzwartekDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerPiatekOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerPiatekDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerSobotaOdGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        timePickerSobotaDoGodzinyPracy.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour, LocalTime.of(6,0,0),LocalTime.of(20,0,0));;
        //</editor-fold>
    }

    private void zaladujDane() {
        zaladujDanePracownikow();
        zaladujDaneDlaAut();
        zaladujDaneKlientow();
        zaladujRezerwacje();
        zaladujDaneGodzinPracy();
    }

    //<editor-fold desc="Pracownicy">
    public void zaladujDanePracownikow() {
        String[] columnNames = {"Imie", "Nazwisko"};
        Object[][] data = repos.getPracownikRepository()
                .findAll()
                .stream()
                .map(p -> new Object[]{p.getImie(), p.getNazwisko()})
                .toArray(Object[][]::new);
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        tablePracownicy.setModel(pracownikService.tabelaPracownicy());
        comboBoxGodzinyPracyPracownicy.setModel(pracownikService.comboBoxPracownicy());
    }

    private void zaladujDaneWybranegoPracownika() {
        Pracownik p = pracownikService.pobierzWybranegoPracownikaZTabeli(tablePracownicy, panel);
        if (p == null) {
            return;
        }
        idWybranegoPracownika = p.getId();
        uzupelnijInputDlaPracownika(p);
    }

    private void uzupelnijInputDlaPracownika() {
        textFieldPracownikImie.setText("");
        textFieldPracownikNazwisko.setText("");
        textFieldPracownikPesel.setText("");
        comboBoxPracownikPlec.setSelectedIndex(0);
        textFieldPracownikLogin.setText("");
        passwordFieldPracownikHaslo.setText("");
        idWybranegoAuta = null;
    }

    private void uzupelnijInputDlaPracownika(Pracownik p) {
        textFieldPracownikImie.setText(String.valueOf(p.getImie()));
        textFieldPracownikNazwisko.setText(String.valueOf(p.getNazwisko()));
        textFieldPracownikPesel.setText(String.valueOf(p.getPesel()));
        comboBoxPracownikPlec.setSelectedItem(p.getPlec());
        textFieldPracownikLogin.setText(String.valueOf(p.getLogin()));
        passwordFieldPracownikHaslo.setText(String.valueOf(p.getHaslo()));
    }

    private void dodajPracownika() {
        Pracownik pracownik = edytujDanePracownikaNaPodstawieInputu(new Pracownik());
        if (pracownik == null) {
            return;
        }
        try {
            Optional<Pracownik> a = repos.getPracownikRepository().findByLogin(textFieldPracownikLogin.getText());
            Optional<Pracownik> b = repos.getPracownikRepository().findByPesel(textFieldPracownikPesel.getText());
            if(a.isPresent()) {
                JOptionPane.showMessageDialog(panel, "Podany login jest już zajęty!");
                return;
            }
            else if(b.isPresent()){
                JOptionPane.showMessageDialog(panel, "Podany pesel jest już zajęty!");
                return;
            }
            repos.getPracownikRepository().save(pracownik);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
        }
        zablokujDodajEdytujPracownika();
        zaladujDanePracownikow();
        uzupelnijInputDlaPracownika();
    }
    private void odblokujDodajEdytujPracownika(){
        buttonPracownicyEdytuj.setEnabled(true);
        buttonPracownicyUsun.setEnabled(true);
    }
    private void zablokujDodajEdytujPracownika(){
        buttonPracownicyEdytuj.setEnabled(false);
        buttonPracownicyUsun.setEnabled(false);
    }

    private boolean sprawdzInputPracownika() {

        if (textFieldPracownikImie.getText().length() < 2) {
            JOptionPane.showMessageDialog(panel, "Wprowadzone imie ma zbyt malo znakow!");
            return false;
        }
        if (textFieldPracownikNazwisko.getText().length() < 2) {
            JOptionPane.showMessageDialog(panel, "Wprowadzone nazwisko ma zbyt malo znakow!");
            return false;
        }
        if (!peselValidator(textFieldPracownikPesel.getText())) {
            JOptionPane.showMessageDialog(panel, "Pesel musi byc ciagiem 11 cyfr!");
            return false;
        }
        if (textFieldPracownikLogin.getText().length() < 2) {
            JOptionPane.showMessageDialog(panel, "Wprowadzony login ma zbyt malo znakow!");
            return false;
        }
        if(!hasloValidator(String.copyValueOf(passwordFieldPracownikHaslo.getPassword()))){
            JOptionPane.showMessageDialog(panel, "Haslo powinno skladac sie z 8 znakow, w tym po jednej malej i duzej literze oraz cyfry i znaku specjalnego!");
            return false;
        }
        return true;

    }

    private Pracownik edytujDanePracownikaNaPodstawieInputu(Pracownik pracownik) {
        if (!sprawdzInputPracownika()) {
            return null;
        }
        pracownik.setImie(textFieldPracownikImie.getText());
        pracownik.setNazwisko(textFieldPracownikNazwisko.getText());
        pracownik.setPesel(textFieldPracownikPesel.getText());
        pracownik.setPlec((Plec) comboBoxPracownikPlec.getSelectedItem());
        pracownik.setLogin(textFieldPracownikLogin.getText());
        pracownik.setHaslo(String.copyValueOf(passwordFieldPracownikHaslo.getPassword()));
        return pracownik;
    }

    private void edytujPracownika() {
        Optional<Pracownik> p = repos.getPracownikRepository().findById(idWybranegoPracownika);
        if (p.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Pracownika nie ma w bazie");
            return;
        }

        Pracownik pracownik = p.get();
        String login = pracownik.getLogin();
        String pesel = pracownik.getPesel();
        pracownik = edytujDanePracownikaNaPodstawieInputu(pracownik);
        if (pracownik == null) {
            return;
        }
        try {
            Optional<Pracownik> a = repos.getPracownikRepository().findByLogin(textFieldPracownikLogin.getText());
            Optional<Pracownik> b = repos.getPracownikRepository().findByPesel(textFieldPracownikPesel.getText());
            if(a.isPresent() && !login.equals(textFieldPracownikLogin.getText())) {
                JOptionPane.showMessageDialog(panel, "Podany login jest już zajęty!");
                return;
            }
            else if(b.isPresent() && !pesel.equals(textFieldPracownikPesel.getText())){
                JOptionPane.showMessageDialog(panel, "Podany pesel jest już zajęty!");
                return;
            }
            repos.getPracownikRepository().save(pracownik);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
        }
        zablokujDodajEdytujPracownika();
        zaladujDanePracownikow();
        uzupelnijInputDlaPracownika();
    }

    private void usunPracownika() {
        Optional<GodzinyPracy> g = repos.getGodzinyPracyRepository().findById(idWybranegoPracownika);
        g.ifPresent(godziny -> repos.getGodzinyPracyRepository().deleteById(godziny.getId()));
        repos.getPracownikRepository().deleteById(idWybranegoPracownika);
        zablokujDodajEdytujPracownika();
        zaladujDanePracownikow();
        uzupelnijInputDlaPracownika();
    }


    //</editor-fold>

    //<editor-fold desc="Auta">
    private void zaladujDaneDlaAut() {
        tableAuta.setModel(autoService.tabelaAuta());
    }

    private void zaladujDaneWybranegoAuta() {
        Auto auto = autoService.pobierzWybraneAutoZTabeli(tableAuta, panel);
        if (auto == null) {
            return;
        }
        idWybranegoAuta = auto.getId();
        uzupelnijInputDlaAut(auto);
    }

    private void uzupelnijInputDlaAut() {
        spinnerAutoCenaZaGodzine.setValue(1);
        textFieldAutoMarka.setText("");
        textFieldAutoModel.setText("");
        spinnerAutoRokProdukcji.setValue(Calendar.getInstance().get(Calendar.YEAR) - 5);
        comboBoxAutoNadwozie.setSelectedIndex(0);
        comboBoxAutoPaliwo.setSelectedIndex(0);
        comboBoxAutoSkrzynia.setSelectedIndex(0);
        idWybranegoAuta = null;
        buttonAutaEdytuj.setEnabled(false);
        buttonAutaUsun.setEnabled(false);
    }

    private void uzupelnijInputDlaAut(Auto auto) {
        spinnerAutoCenaZaGodzine.setValue(auto.getCenaZaGodzine());
        textFieldAutoMarka.setText(auto.getMarka());
        textFieldAutoModel.setText(auto.getModel());
        spinnerAutoRokProdukcji.setValue(auto.getRokProdukcji());
        comboBoxAutoNadwozie.setSelectedItem(auto.getNadwozie());
        comboBoxAutoPaliwo.setSelectedItem(auto.getPaliwo());
        comboBoxAutoSkrzynia.setSelectedItem(auto.getSkrzynia());
        buttonAutaEdytuj.setEnabled(true);
        buttonAutaUsun.setEnabled(true);
    }

    private boolean sprawdzInputUzytkownika() {


        if (textFieldAutoMarka.getText().length() < 3) {
            JOptionPane.showMessageDialog(panel, "Marka ma zbyt mało znaków");
            return false;
        }
        if (textFieldAutoModel.getText().length() < 2) {
            JOptionPane.showMessageDialog(panel, "Model ma zbyt mało znaków");
            return false;
        }


        return true;
    }

    private Auto edytujDaneAutaNaPodstawieInputu(Auto auto) {
        if (!sprawdzInputUzytkownika()) {
            return null;
        }
        auto.setCenaZaGodzine((int) spinnerAutoCenaZaGodzine.getValue());
        auto.setMarka(textFieldAutoMarka.getText());
        auto.setModel(textFieldAutoModel.getText());
        auto.setRokProdukcji((int) spinnerAutoRokProdukcji.getValue());
        auto.setNadwozie((Nadwozie) comboBoxAutoNadwozie.getSelectedItem());
        auto.setPaliwo((Paliwo) comboBoxAutoPaliwo.getSelectedItem());
        auto.setSkrzynia((Skrzynia) comboBoxAutoSkrzynia.getSelectedItem());
        return auto;
    }

    private void dodajAuto() {
        Auto auto = edytujDaneAutaNaPodstawieInputu(new Auto());
        if (auto == null) {
            return;
        }
        repos.getAutoRepository().save(auto);
        zaladujDaneDlaAut();
        uzupelnijInputDlaAut();
    }

    private void edytujAuto() {
        if(idWybranegoAuta==null){
            JOptionPane.showMessageDialog(panel, "Nie wybrano auta");
            return;
        }
        Optional<Auto> a = repos.getAutoRepository().findById(idWybranegoAuta);
        if (a.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Auta nie ma w bazie");
            return;
        }
        Auto auto = a.get();
        auto = edytujDaneAutaNaPodstawieInputu(auto);
        if (auto == null) {
            return;
        }
        repos.getAutoRepository().save(auto);
        zaladujDaneDlaAut();
        uzupelnijInputDlaAut();
    }

    private void usunAuto() {
        if(idWybranegoAuta==null){
            JOptionPane.showMessageDialog(panel, "Nie wybrano auta");
            return;
        }
        if(!repos.getWypozyczenieRepository().findByAutoId(idWybranegoAuta).isEmpty()){
            JOptionPane.showMessageDialog(panel, "Nie można usunąc auta, które ma wypozyczenie");
            return;
        }
        try {
            repos.getAutoRepository().deleteById(idWybranegoAuta);
            zaladujDaneDlaAut();
            uzupelnijInputDlaAut();
        } catch (Exception ignored) {
        }
    }
    //</editor-fold>

    //<editor-fold desc="Klienci">
    private void zaladujDaneKlientow(){ tableKlienci.setModel(klientService.tabelaKlienci()); }

    private void zaladujDaneSzukanegoKlienta(){
        String szukane = textFieldSzukajKlienta.getText();
        if(szukane.isEmpty()){
            zaladujDaneKlientow();
        }
        else{
            List<Klient> klientOptional = repos.getKlientRepository().findByImieLikeOrNazwiskoLikeOrLoginLike(szukane);
            if (!(klientOptional.isEmpty())) {
                tableKlienci.setModel(klientService.tabelaSzukanychKlientów(szukane));
            }
            else {
                showMessageDialog(null, "Brak takiego klienta!");
            }
        }
    }
    //</editor-fold>

    private void zaladujRezerwacje(){ tableRezerwacje.setModel(wypozyczenieService.allWypozyczeniaTabela());}

    //<editor-fold desc="Godziny pracy">
    private void zeorwanieGodzinPracy() {
        timePickerPoniedzialekOdGodzinyPracy.setText("");
        timePickerPoniedzialekDoGodzinyPracy.setText("");
        timePickerWtorekOdGodzinyPracy.setText("");
        timePickerWtorekDoGodzinyPracy.setText("");
        timePickerSrodaOdGodzinyPracy.setText("");
        timePickerSrodaDoGodzinyPracy.setText("");
        timePickerCzwartekOdGodzinyPracy.setText("");
        timePickerCzwartekDoGodzinyPracy.setText("");
        timePickerPiatekOdGodzinyPracy.setText("");
        timePickerPiatekDoGodzinyPracy.setText("");
        timePickerSobotaOdGodzinyPracy.setText("");
        timePickerSobotaDoGodzinyPracy.setText("");
    }

    private void zaladujDaneGodzinPracy() {
        Optional<GodzinyPracy> godzinyPracyOptional = repos.getGodzinyPracyRepository().findByPracownik((Pracownik) comboBoxGodzinyPracyPracownicy.getSelectedItem());

        godzinyPracyOptional.ifPresentOrElse((g) -> {
            uzupelnijInputDlaGodzin(g);
            buttonDodajGodzinyPracy.setEnabled(false);
        }, () -> {
            zeorwanieGodzinPracy();
            buttonDodajGodzinyPracy.setEnabled(true);
        });
    }

    private void uzupelnijInputDlaGodzin(GodzinyPracy godzinyPracy) {
        timePickerPoniedzialekOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getPoniedzialekOd()));
        timePickerPoniedzialekDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getPoniedzialekDo()));
        timePickerWtorekOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getWtorekOd()));
        timePickerWtorekDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getWtorekDo()));
        timePickerSrodaOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getSrodaOd()));
        timePickerSrodaDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getSrodaDo()));
        timePickerCzwartekOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getCzwartekOd()));
        timePickerCzwartekDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getCzwartekDo()));
        timePickerPiatekOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getPiatekOd()));
        timePickerPiatekDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getPiatekDo()));
        timePickerSobotaOdGodzinyPracy.setText(String.valueOf(godzinyPracy.getSobotaOd()));
        timePickerSobotaDoGodzinyPracy.setText(String.valueOf(godzinyPracy.getSobotaDo()));
    }

    private GodzinyPracy edytujGodzinyPracyNaPodstawieInputu(GodzinyPracy godzPracy) {
        godzPracy.setPracownik((Pracownik) comboBoxGodzinyPracyPracownicy.getSelectedItem());
        godzPracy.setPoniedzialekOd(timePickerPoniedzialekOdGodzinyPracy.getTime());
        godzPracy.setPoniedzialekDo(timePickerPoniedzialekDoGodzinyPracy.getTime());
        godzPracy.setWtorekOd(timePickerWtorekOdGodzinyPracy.getTime());
        godzPracy.setWtorekDo(timePickerWtorekDoGodzinyPracy.getTime());
        godzPracy.setSrodaOd(timePickerSrodaOdGodzinyPracy.getTime());
        godzPracy.setSrodaDo(timePickerSrodaDoGodzinyPracy.getTime());
        godzPracy.setCzwartekOd(timePickerCzwartekOdGodzinyPracy.getTime());
        godzPracy.setCzwartekDo(timePickerCzwartekDoGodzinyPracy.getTime());
        godzPracy.setPiatekOd(timePickerPiatekOdGodzinyPracy.getTime());
        godzPracy.setPiatekDo(timePickerPiatekDoGodzinyPracy.getTime());
        godzPracy.setSobotaOd(timePickerSobotaOdGodzinyPracy.getTime());
        godzPracy.setSobotaDo(timePickerSobotaDoGodzinyPracy.getTime());
        return godzPracy;
    }

    private boolean godzinyWprowadzonePrawidlowo(GodzinyPracy godzinyPracy){
        try{
            return  godzinyPracy.getPoniedzialekDo().isAfter(godzinyPracy.getPoniedzialekOd()) &&
                    godzinyPracy.getWtorekDo().isAfter(godzinyPracy.getWtorekOd()) &&
                    godzinyPracy.getSrodaDo().isAfter(godzinyPracy.getSrodaOd()) &&
                    godzinyPracy.getCzwartekDo().isAfter(godzinyPracy.getCzwartekOd()) &&
                    godzinyPracy.getPiatekDo().isAfter(godzinyPracy.getPiatekOd()) &&
                    godzinyPracy.getSobotaDo().isAfter(godzinyPracy.getSobotaOd());
        } catch(Exception ex){
            return false;
        }
    }

    private void dodajGodzinyPracy() {
        if(comboBoxGodzinyPracyPracownicy.getSelectedItem()==null){
            showMessageDialog(null, "Nie wybrano pracownika");
            return;
        }
        GodzinyPracy godzinyPracy = edytujGodzinyPracyNaPodstawieInputu(new GodzinyPracy());
        if(!godzinyWprowadzonePrawidlowo(godzinyPracy)){
            showMessageDialog(null, "Godziny muszą być uzypełnone oraz godziny rozpoczęcia muszą być przed godzinami zakończenia");
            return;
        }
        repos.getGodzinyPracyRepository().save(godzinyPracy);
        zaladujDaneGodzinPracy();
    }

    private void edytujGodzinyPracy() {
        Optional<GodzinyPracy> g = repos.getGodzinyPracyRepository().findByPracownik((Pracownik) comboBoxGodzinyPracyPracownicy.getSelectedItem());
        if (g.isEmpty()) {
            showMessageDialog(null, "Podany pracownik nie ma godzin pracy");
            return;
        }
        GodzinyPracy godzinyPracy = g.get();
        edytujGodzinyPracyNaPodstawieInputu(godzinyPracy);
        if(!godzinyWprowadzonePrawidlowo(godzinyPracy)){
            showMessageDialog(null, "Godziny rozpoczęcia muszą być przed godzinami zakończenia");
            return;
        }
        repos.getGodzinyPracyRepository().save(godzinyPracy);
        showMessageDialog(null, "Zedytowano godziny pracy pracownika:"+comboBoxGodzinyPracyPracownicy.getSelectedItem());
        zaladujDaneGodzinPracy();
    }

    private void usunGodzinyPracy() {
        Optional<GodzinyPracy> godzinyPracyOptional = repos.getGodzinyPracyRepository().findByPracownik((Pracownik) comboBoxGodzinyPracyPracownicy.getSelectedItem());
        if(godzinyPracyOptional.isEmpty()){
            showMessageDialog(null, "Podany pracownik nie ma godzin pracy");
            return;
        }
        repos.getGodzinyPracyRepository().deleteById(godzinyPracyOptional.get().getId());
        zeorwanieGodzinPracy();
        showMessageDialog(null, "Usunięto godziny pracy Pracownika o id:"+ comboBoxGodzinyPracyPracownicy.getSelectedItem());
        zaladujDaneGodzinPracy();
    }

    private void czasPracy(int czasStart) {
        int czasKoncowy = czasStart + 8;

        timePickerPoniedzialekOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));
        timePickerWtorekOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));
        timePickerSrodaOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));
        timePickerCzwartekOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));
        timePickerPiatekOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));
        timePickerSobotaOdGodzinyPracy.setTime(LocalTime.of(czasStart, 0, 0));

        timePickerPoniedzialekDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
        timePickerWtorekDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
        timePickerSrodaDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
        timePickerCzwartekDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
        timePickerPiatekDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
        timePickerSobotaDoGodzinyPracy.setTime(LocalTime.of(czasKoncowy, 0, 0));
    }
    //</editor-fold>
}