package model;

import java.time.LocalDate;

public class TirocinioInterno extends Tirocinio{
    public TirocinioInterno(String nome, String descrizione, Docente relatore, LocalDate data) {
        super(nome, descrizione, relatore,data);
    }

}
