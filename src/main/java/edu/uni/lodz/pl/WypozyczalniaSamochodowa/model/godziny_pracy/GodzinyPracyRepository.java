package edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.godziny_pracy;

import edu.uni.lodz.pl.WypozyczalniaSamochodowa.model.pracownik.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface GodzinyPracyRepository extends JpaRepository<GodzinyPracy, Integer> {



   // @Query("SELECT g FROM godziny_pracy g WHERE(g.id=:id)")
    Optional<GodzinyPracy> findById(    Integer id);

//    @Query("SELECT g FROM GodzinyPracy g WHERE (g.pracownik=:pracownikId)")
    Optional<GodzinyPracy> findByPracownik (Pracownik pracownik);

//    @Query("DELETE FROM GodzinyPracy g WHERE (g.pracownik.id=:pracownikId)")
//    void deleteByPracownikId(Integer pracownikId);
}

