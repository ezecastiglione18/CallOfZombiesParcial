package Perks;

import Jugadores.Jugador;

public class Bronce extends Perk {
    private int costo = 700;
    public String discriminante = "bronce";

    @Override
    public int costo() {
        return this.costo;
    }

    public void sumarPuntosPorMatar(Jugador jugador) {
        jugador.sumarPuntos(5);
    }
}