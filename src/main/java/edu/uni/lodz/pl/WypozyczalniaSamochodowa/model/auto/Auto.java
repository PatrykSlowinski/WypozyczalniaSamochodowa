package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String marka;
    private String model;
    private Nadwozie nadwozie;
    private Paliwo paliwo;
    private Skrzynia skrzynia;
    private int rokProdukcji;
    private int cenaZaGodzine;

    public Auto(String marka, String model, Nadwozie nadwozie, Paliwo paliwo, Skrzynia skrzynia, int rokProdukcji, int cenaZaGodzine) {
        this.marka = marka;
        this.model = model;
        this.nadwozie = nadwozie;
        this.paliwo = paliwo;
        this.skrzynia = skrzynia;
        this.rokProdukcji = rokProdukcji;
        this.cenaZaGodzine = cenaZaGodzine;
    }

    @Override
    public String toString() {
        return  marka + " " +
                model + ", cena za godzinę: " +
                cenaZaGodzine;
    }
}
