package Perks;

import Jugadores.Jugador;

public class Plata extends Perk {
    private int costo = 1500;

    public void recompensarKill(Jugador jugador) {
        jugador.sumarPuntos(20);
    }
}
