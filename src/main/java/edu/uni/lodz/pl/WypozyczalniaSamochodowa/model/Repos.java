package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.AutoRepository;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.godziny_pracy.GodzinyPracyRepository;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.KlientRepository;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.PracownikRepository;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.wypozyczenie.WypozyczenieRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class Repos {
    private final PracownikRepository pracownikRepository;
    private final KlientRepository klientRepository;
    private final AutoRepository autoRepository;
    private final WypozyczenieRepository wypozyczenieRepository;
    private final GodzinyPracyRepository godzinyPracyRepository;
}