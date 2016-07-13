package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Clase abstracta que define la entidad del jugador.
 * SEMI-ESTABLE
 * @author David Olmos
 * Date: 13/08/2015
 */
public abstract class Jugador implements Serializable
{
    public static final double DEFAULT_DEF_REDUCTION = 0.45;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2843109286241379938L;

	protected int hp, mp, atq, def, vel;
    protected String nombre;
    protected String clase;
	protected boolean willAttack;
	protected Consumer<Jugador>[] attacks;
	protected List<Estado> estados;

    protected Jugador(String nombre, int hp, int mp, int atq, int def, int vel)
    {
		this.attacks = (Consumer<Jugador>[]) (new Consumer[5]);
		this.estados = Collections.emptyList();
        this.nombre = nombre;
        this.hp = hp;
        this.mp = mp;
        this.atq = atq;
        this.def = def;
        this.vel = vel;
    }

    public void addState(Estado state, boolean stackable) {
        if (stackable) {
            estados.add(state);
        } else {
            state.putState(estados);
        }
    }

	public void checkState() {
		for (Estado e : estados) {
			if (e.contador > 0) { e.getApplicator().accept(this); }
			else { e.getRemoval().accept(this); }
			e.contador--;
		}
		estados.removeIf(estado -> estado.contador < 0);
	}

    public boolean isTaggedAs(Estado.Estados tag) {
        return estados.stream().anyMatch(estado -> estado.tag.contains(tag));
    }

    public List<Estado.Estados> getTags() {
        List<Estado.Estados> res = Collections.emptyList();
        estados.forEach(estado -> res.addAll(estado.tag.stream().filter(tag -> !res.contains(tag)).collect(Collectors.toList())));
        return res;
    }

    public int calculateRealDamage(double atqMultiplier, Jugador other) {
        int damage = (int) (atq * atqMultiplier) - (int) (other.def * DEFAULT_DEF_REDUCTION);
        if (damage > 0) return damage;
        return 0;
    }

    public void attack(int ataque, Jugador target)
    {
    	if (willAttack) {
			attacks[ataque].accept(target);
		}else{
			System.out.println("¡"+this.getNombre()+" no puede atacar porque ha sido paralizado!");
		}
    }

    public abstract int seleccionAtaque(Jugador target, Scanner S);
    public abstract int seleccionAtaqueIA(Jugador player);
    
	public static Jugador init(Scanner S)
	{
        Jugador jugador;

        int clase = Util.menu("Elige clase:\n" +
                "1. Mago\n" +
                "2. Espadachin\n" +
                "3. Arquero\n" +
                "4. Salir", 4, S);
        switch(clase){
            case 1:
                jugador = new Mago(S);
                break;
            case 2:
                jugador = new Espadachin(S);
                break;
            case 3:
                jugador = new Arquero(S);
                break;
            default:
                jugador = null;
                break;
        }
        return jugador;
	}

    public static Jugador initRandom()
	{
		//Creacion adversario
		int claseAdversario;
		Jugador jugador = null;
		Random rand = new Random(System.nanoTime());
		claseAdversario = rand.nextInt(3)+1;

		switch(claseAdversario)
		{
		case 1:
			jugador = new Mago("IA");
			break;
		case 2:
			jugador = new Espadachin("IA");
			break;
		case 3:
			jugador = new Arquero("IA");
			break;
		}
		return jugador;
	}

	public int getHp() {
		return hp;
	}
	public void takeDamage(int damage) {
        double realDamage = damage - (int)(def * DEFAULT_DEF_REDUCTION);
        if(realDamage > 0)
        {
            hp -= realDamage;
        }
	}

	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}

	public String getNombre() {
		return nombre;
	}
}
