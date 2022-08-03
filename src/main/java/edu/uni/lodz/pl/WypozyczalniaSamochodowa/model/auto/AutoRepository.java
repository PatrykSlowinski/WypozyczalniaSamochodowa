package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoRepository extends JpaRepository<Auto, Integer> {
}