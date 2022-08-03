package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.wypozyczenie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WypozyczenieRepository extends JpaRepository<Wypozyczenie, Integer> {

    List<Wypozyczenie> findByKlientId(Integer klientId);

    List<Wypozyczenie> findByAutoId(Integer autoId);
}