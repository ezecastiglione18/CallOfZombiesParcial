package ControlDelJuego;

import Jugadores.Equipo;
import Jugadores.Jugador;
import Mail.EnviarMail;
import Objetos.Objeto;
import Objetos.ObjetosFactory;
import Zombies.Zombie;
import Zombies.ZombieComunFactory;
import Zombies.ZombieEspecialFactory;

import java.util.ArrayList;

public class Partida {
    private static Partida instancia = null;
    private double ratioZombies = 1.0;
    private double ratioObjetos = 1.0;
    private Equipo equipo;
    private int numeroDeRonda = 0;
    public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    private ArrayList<Objeto> objetos = new ArrayList<Objeto>();

    private Partida() {};

    public static Partida GetInstance() {
        if (instancia == null) {
            instancia = new Partida();
        }
        return instancia;
    }

    public Equipo equipo() {
        return equipo;
    }

    public void iniciarPartida(String nombreEquipo, String correoEquipo) throws Exception {
        this.equipo = new Equipo(nombreEquipo, correoEquipo);
        this.iniciarRonda();
    }

    public void finalizarPartida() {
        Integer puntaje = this.equipo.puntos();
        RepositorioPuntajes.GetInstance().agregarPartida(this.equipo, puntaje);
        EnviarMail.enviarPuntaje(this.equipo.correo(), puntaje);
        equipo = null;
    }

    private void iniciarRonda() {
        this.numeroDeRonda++;
        crearZombies();
        crearObjetos();
        this.equipo.malhumor();
        this.equipo.revitalizar();
    }

    public void crearZombies() {
        if (this.esRondaPar()) {
            // crea zombies
            for ( int i = 0; i < ratioZombies * numeroDeRonda; i++) {
                zombies.add(ZombieEspecialFactory.crearZombie());
            }
        } else {
            // crea zombies
            for ( int i = 0; i < ratioZombies * numeroDeRonda; i++) {
                zombies.add(ZombieComunFactory.crearZombie());
            }
        }
    }

    private boolean esRondaPar() {
        return numeroDeRonda % 2 == 0;
    }

    private void finalizarRonda() {
        int premio = 10 * numeroDeRonda;
        this.equipo.premiarSupervivencia(premio);
        this.iniciarRonda();
    }

    public void unJugadorMenos(Jugador jugador) throws NullPointerException{
        try{
            //equipo.unJugadorMenos(jugador);
            if(equipo.cantidadDeJugadoresVivos() == 0) {
                this.finalizarPartida();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void unZombieMenos(Zombie zombie) {
        zombies.remove(zombie);
        if(zombies.size() == 0) {
            this.finalizarRonda();
        }
    }

    private void crearObjetos() {
        // crea objetos
        for ( int i = 0; i < ratioObjetos * numeroDeRonda; i++) {
            this.objetos.add(ObjetosFactory.crearObjeto());
        }
    }
}