package model;

import java.time.*;

public class Seduta {
    private LocalDate data;
    private LocalTime ora;
    private int voto;
    private Tesi tesi;
    private Docente docente;
    public Seduta(LocalDate data, LocalTime ora,Docente docente) {
        this.data = data;
        this.voto = -1;
        this.tesi = null;
        this.docente = docente;
    }
    public LocalDate getData() {
        return data;
    }
    public LocalTime getOra(){ return ora; }
    public void setVoto(int voto) {
        this.voto = voto;
    }
    public int getVoto() {
        return voto;
    }
    public Tesi getTesi(){
        return tesi;
    }
    public Docente getDocente(){
        return this.docente;
    }
    public void setTesi(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public String toString() {
        String s = "Seduta del "+data.toString()+" alle "+ora.toString()+" del docente "+docente.getCognome()+" "+docente.getNome()+"\n"+"";
        if(tesi!=null)
            s+="Libera";
        else if (voto>=-1)
            s+="Conclusa con voto"+voto+", tesi allegata:"+tesi.getContenuto();
        else
            s+="Prenotata, tesi allegata:"+tesi.getContenuto();
        return s;
    }
}

