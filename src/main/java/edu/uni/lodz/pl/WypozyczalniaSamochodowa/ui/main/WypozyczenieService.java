package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import lombok.RequiredArgsConstructor;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class WypozyczenieService {
    private final Repos repos;
    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yy, HH:mm");

    public DefaultTableModel wypozyczenieKlientTabela(Integer id) {
        String[] columnNames = {"Id","Data początkowa", "Data końcowa", "Marka Auta", "Model Auta", "Koszt"};
        Object[][] data = repos.getWypozyczenieRepository()
                .findByKlientId(id)
                .stream()
                .map(wypozyczenie -> new Object[]{wypozyczenie.getId(),wypozyczenie.getDataPoczatkowa().format(formatter), wypozyczenie.getDataKoncowa().format(formatter), wypozyczenie.getAuto().getMarka(), wypozyczenie.getAuto().getModel(), wypozyczenie.getKoszt()+" zł"})
                .toArray(Object[][]::new);
        return new DefaultTableModel(data, columnNames);
    }

    public DefaultTableModel allWypozyczeniaTabela() {
        String[] columnNames = {"Id","Data początkowa", "Data końcowa", "Marka Auta", "Model Auta", "Koszt", "Imię klienta", "Nazwisko klienta"};
        Object[][] data = repos.getWypozyczenieRepository()
                .findAll()
                .stream()
                .map(wypozyczenie -> new Object[]{wypozyczenie.getId(),wypozyczenie.getDataPoczatkowa().format(formatter), wypozyczenie.getDataKoncowa().format(formatter), wypozyczenie.getAuto().getMarka(), wypozyczenie.getAuto().getModel(), wypozyczenie.getKoszt()+" zł", wypozyczenie.getKlient().getImie(), wypozyczenie.getKlient().getNazwisko()})
                .toArray(Object[][]::new);
        return new DefaultTableModel(data, columnNames);
    }
}
