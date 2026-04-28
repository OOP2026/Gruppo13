package PrimoHomework;

import java.util.Date;

public class Seduta {
    private Date data;
    private int voto;
    public Seduta(Date data, int voto) {
        this.data = data;
        this.voto = voto;
    }
    public Date getData() {
        return data;
    }

}
