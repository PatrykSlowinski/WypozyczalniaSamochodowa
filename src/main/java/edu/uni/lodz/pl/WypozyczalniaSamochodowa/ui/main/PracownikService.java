package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.Pracownik;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Optional;

@RequiredArgsConstructor
public class PracownikService {
    private final Repos repos;

    public DefaultTableModel tabelaPracownicy() {
        String[] columnNames = {"Id", "Imie", "Nazwisko", "Pesel", "Plec", "Login"};
        Object[][] data = repos.getPracownikRepository()
                .findAll()
                .stream()
                .map(a -> new Object[]{a.getId(), a.getImie(), a.getNazwisko(), a.getPesel(), a.getPlec(), a.getLogin()})
                .toArray(Object[][]::new);
        return new DefaultTableModel(data, columnNames);
}

    public Pracownik pobierzWybranegoPracownikaZTabeli(JTable tablePracownicy, JPanel panel) {
        if (tablePracownicy.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(panel, "Nie wybrano wiersza");
            return null;
        }
        Integer id = (int) tablePracownicy.getValueAt(tablePracownicy.getSelectedRow(), 0);
        Optional<Pracownik> p = repos.getPracownikRepository().findById(id);
        if (p.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Pracownika nie ma w bazie");
            return null;
        }
        return p.get();
    }

    public DefaultComboBoxModel<Pracownik> comboBoxPracownicy(){
        Pracownik[] pracownikArray = repos.getPracownikRepository()
                .findAll().toArray(Pracownik[]::new);
        return new DefaultComboBoxModel<Pracownik>(pracownikArray);
    }
}
