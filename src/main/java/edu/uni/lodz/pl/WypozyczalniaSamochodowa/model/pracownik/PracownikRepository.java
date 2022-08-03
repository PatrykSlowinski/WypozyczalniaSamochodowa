package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PracownikRepository extends JpaRepository<Pracownik, Integer> {
    @Query("SELECT p FROM Pracownik p WHERE(p.login=:login AND p.haslo=:haslo)")
    Optional<Pracownik> findAllByLoginAndPassword(@Param("login") String login, @Param("haslo") String haslo);

    //Działa dokładnie tak samo jak findAllByLoginAndPassword
    Optional<Pracownik> findByLoginAndHaslo(String login, String haslo);

    Optional<Pracownik> findByLogin(String login);

    Optional<Pracownik> findByPesel(String pesel);

}
