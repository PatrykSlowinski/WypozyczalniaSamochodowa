package edu.uni.lodz.pl.WypozyczalniaSamochodowa.utils;

public class Validators {
    public static boolean peselValidator(String pesel) {
        if (pesel.length() != 11) {
            return false;
        } else return pesel.matches("[+-]?\\d*(\\.\\d+)?");
    }

    public static boolean hasloValidator(String haslo){
        return haslo.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    }
}
