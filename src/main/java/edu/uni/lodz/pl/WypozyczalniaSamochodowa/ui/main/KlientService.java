package edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.main;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import lombok.RequiredArgsConstructor;

import javax.swing.table.DefaultTableModel;

@RequiredArgsConstructor
public class KlientService {
    private final Repos repos;

    public DefaultTableModel tabelaKlienci(){
        String[] columnNames = {"Id", "Imie", "Nazwisko", "Login", "Pesel", "Plec"};
        Object[][] data = repos.getKlientRepository()
                .findAll()
                .stream()
                .map(a -> new Object[]{a.getId(), a.getImie(), a.getNazwisko(), a.getLogin(), a.getPesel(), a.getPlec()})
                .toArray(Object[][]::new);
        return new DefaultTableModel(data, columnNames);
    }


    public DefaultTableModel tabelaSzukanychKlientów(String szukane){
        String[] columnNames = {"Id", "Imie", "Nazwisko", "Login", "Pesel", "Plec"};
        Object[][] data = repos.getKlientRepository()
                .findByImieLikeOrNazwiskoLikeOrLoginLike(szukane)
                .stream()
                .map(a -> new Object[]{a.getId(), a.getImie(), a.getNazwisko(), a.getLogin(), a.getPesel(), a.getPlec()})
                .toArray(Object[][]::new);

        return new DefaultTableModel(data, columnNames);
    }

    //public Klient wyswietlDane

}


