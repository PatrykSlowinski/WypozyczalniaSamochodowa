package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.wypozyczenie;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Auto;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Wypozyczenie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataPoczatkowa;
    private LocalDateTime dataKoncowa;
    private Integer koszt;
    @ManyToOne
    private Klient klient;
    @OneToOne
    private Auto auto;

    public Wypozyczenie(LocalDateTime dataPoczatkowa, LocalDateTime dataKoncowa, Klient klient, Auto auto, Integer koszt) {
        this.dataPoczatkowa = dataPoczatkowa;
        this.dataKoncowa = dataKoncowa;
        this.klient = klient;
        this.auto = auto;
        this.koszt = koszt;
    }
}