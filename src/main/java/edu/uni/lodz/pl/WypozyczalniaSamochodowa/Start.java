package edu.uni.lodz.pl.WypozyczalniaSamochodowa;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Plec;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.Repos;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Auto;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Nadwozie;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Paliwo;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.auto.Skrzynia;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.godziny_pracy.GodzinyPracy;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient.Klient;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.Pracownik;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.wypozyczenie.Wypozyczenie;
import edu.uni.lodz.pl.WypozyczalniaSamochodowa.ui.logowanie.Logowanie;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class Start implements CommandLineRunner {
    private final Repos repos;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Start.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) {
        Logowanie logowanie = new Logowanie(repos);
        logowanie.setVisible(true);
        dodajDane();
    }

    private void dodajDane() {
        if (!repos.getPracownikRepository().findAll().isEmpty()) {
            return;
        }
        dodajPracownikow();
        dodajKlientowAutaWypozyczenia();
    }

    private void dodajPracownikow() {
        LocalTime start = LocalTime.of(8, 0, 0);
        LocalTime end = LocalTime.of(16, 0, 0);
        Pracownik p1 = new Pracownik("Adam", "Nowak", "32454320034", "admin", "Pass123!", Plec.MEZCZYZNA);
        Pracownik p2 = new Pracownik("Tomasz", "Kowalski", "35653354198", "employee1", "Sdfg4!ms", Plec.MEZCZYZNA);
        Pracownik p3 = new Pracownik("Anna", "Nowakowska", "32565348453", "employee2", "Fjhth7!d", Plec.KOBIETA);
        GodzinyPracy g1 = new GodzinyPracy(p1, start, end, start, end, start, end, start, end, start, end, start, end);
        GodzinyPracy g2 = new GodzinyPracy(p2, start, end, start, end, start, end, start, end, start, end, start, end);
        GodzinyPracy g3 = new GodzinyPracy(p3, start, end, start, end, start, end, start, end, start, end, start, end);
        repos.getPracownikRepository().saveAll(List.of(p1, p2, p3));
        repos.getGodzinyPracyRepository().saveAll(List.of(g1, g2, g3));
    }

    private void dodajKlientowAutaWypozyczenia() {
        Klient k1 = new Klient("Filip", "Sadowski", "5728540954", "user1", "Passuser1!", Plec.MEZCZYZNA);
        Klient k2 = new Klient("Jan", "Pach", "27650193845", "user2", "Passuser2!", Plec.MEZCZYZNA);
        repos.getKlientRepository().saveAll(List.of(k1, k2));

        Auto a1 = new Auto("Audi", "A7", Nadwozie.SEDAN, Paliwo.BENZYNA, Skrzynia.AUTOMATYCZNA, 2020, 6);
        Auto a2 = new Auto("Volkswagen", "Passat", Nadwozie.SEDAN, Paliwo.DIESEL, Skrzynia.MANUALNA, 2015, 4);
        repos.getAutoRepository().saveAll(List.of(a1, a2));

        Wypozyczenie w1 = new Wypozyczenie(LocalDateTime.of(2022, 4, 14, 8, 0, 0), LocalDateTime.of(2022, 4, 21, 8, 0, 0), k1, a1, 7 * a1.getCenaZaGodzine() * 24);
        Wypozyczenie w2 = new Wypozyczenie(LocalDateTime.of(2022, 4, 2, 8, 0, 0), LocalDateTime.of(2022, 4, 9, 8, 0, 0), k2, a2, 7 * a2.getCenaZaGodzine() * 24);
        repos.getWypozyczenieRepository().saveAll(List.of(w1, w2));
    }
}
