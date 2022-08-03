package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.klient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KlientRepository extends JpaRepository<Klient, Integer> {
    @Query("SELECT k FROM Klient k WHERE k.imie like concat('%',:szukane,'%') OR k.nazwisko like concat('%',:szukane,'%') OR k.login like concat('%',:szukane,'%')")
    List<Klient> findByImieLikeOrNazwiskoLikeOrLoginLike(String szukane);
    Optional<Klient> findByLoginAndHaslo(String login, String haslo);
    Optional<Klient> findByLogin(String login);
    Optional<Klient> findByPesel(String pesel);


}