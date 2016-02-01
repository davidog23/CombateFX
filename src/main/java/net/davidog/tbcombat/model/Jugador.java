package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase abstracta que define la entidad del jugador.
 * SEMI-ESTABLE
 * @author David Olmos
 * Date: 13/08/2015
 */
public abstract class Jugador implements Serializable //Quiero ser capaz de enviar este objeto por IP
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2843109286241379938L;
	protected int oro, hp, mp, sp, atq, def, vel;
    protected String nombre;
    protected int extraAtaque, extraDefensa, extraVelocidad;
    protected int contadorParalisis, contadorCuracion, contadorSangrado, contadorExtraAtaque, contadorExtraDefensa, contadorExtraVelocidad;
    protected String clase;
	protected boolean willAttack;
	protected int totalAtq = 0, totalDef = 0, totalVel = 0;

    public Jugador(String nombre, int hp, int mp, int atq, int def, int vel)
    {
        this.nombre = nombre;
        this.oro = 0;
        this.hp = hp;
        this.mp = mp;
        this.sp = 0;
        this.atq = atq;
        this.def = def;
        this.vel = vel;
        this.extraAtaque = 0;
        this.extraDefensa = 0;
    }
    
    
    public void checkState()
    {
    	if(this.contadorParalisis > 0)
    	{
    		this.willAttack = false;
    		this.contadorParalisis--;
    	}else{
    		this.willAttack = true;
    	}
    	
    	if(this.contadorCuracion > 0)
    	{
    		this.hp += 200;
    		this.contadorCuracion--;
    	}
    	
    	if(this.contadorSangrado > 0)
    	{
    		this.hp -= 75;
    		this.contadorSangrado--;
    	}
    	
    	if(this.contadorExtraAtaque > 0)
    	{
    		this.contadorExtraAtaque--;
    	}else{
    		this.extraAtaque = 0;
    	}
    	
    	if(this.contadorExtraDefensa > 0)
    	{
    		this.contadorExtraDefensa--;
    	}else{
    		this.extraDefensa = 0;
    	}
    	
    	if(this.contadorExtraVelocidad > 0)
    	{
    		this.contadorExtraVelocidad--;
    	}else{
    		this.extraVelocidad = 0;
    	}
    	this.totalAtq = this.atq + this.extraAtaque;
    	this.totalDef = this.def + this.extraDefensa;
    	this.totalVel = this.vel + this.extraVelocidad;
    }
    
    public void attack(int ataque, Jugador target)
    {
    	if (willAttack) {
			switch (ataque) {
			case 1:
				this.atq1(target);
				break;
			case 2:
				this.atq2(target);
				break;
			case 3:
				this.atq3(target);
				break;
			case 4:
				this.atq4(target);
				break;
			case 5:
				this.atq5(target);
				break;
			}
		}else{
			System.out.println("¡"+this.getNombre()+" no puede atacar porque ha sido paralizado!");
		}
    }
    
    public abstract void atq1(Jugador target);
    public abstract void atq2(Jugador target);
    public abstract void atq3(Jugador target);
    public abstract void atq4(Jugador target);
    public abstract void atq5(Jugador target);

    public abstract int seleccionAtaque(Jugador target);
    public abstract int seleccionAtaqueIA(Jugador player);
    
    @SuppressWarnings("resource")
	public static Jugador init()
	{
        Jugador jugador;
		Scanner S = new Scanner(System.in);

        int clase = Util.menu("Elige clase:\n" +
                "1. Mago\n" +
                "2. Espadachin\n" +
                "3. Arquero\n" +
                "4. Salir", 4);
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

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public boolean isParalizado() {
		return contadorParalisis > 0;
	}

	public boolean isCurandose() {
		return contadorCuracion > 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setContadorParalisis(int contadorParalisis) {
		this.contadorParalisis = contadorParalisis;
	}

	public void setContadorSangrado(int contadorSangrado) {
		this.contadorSangrado = contadorSangrado;
	}

}
