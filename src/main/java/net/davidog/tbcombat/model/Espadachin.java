package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.util.Scanner;

/**
 * La especialidad de Espadachin.
 * SEMI-ESTABLE(WIP IA)
 * @author David Olmos
 * @version 0.1
 */
public class Espadachin extends Jugador
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7787854423003223385L;

	public Espadachin(String nombre)
    {
        super(nombre, 3000, 900, 70, 100, 20);
        this.clase = "espadachin";
    }

	public Espadachin(Scanner S)
	{
		this(Util.leerTeclado("¿Cómo te llamas?", S));
	}
    
    public void atq1(Jugador target)
    {
    	if (this.mp + 10 <= 900) {
			this.mp += 10;
		}else{
			this.mp = 900;
		}
		target.setHp(target.getHp() - this.totalAtq);
    }
    
    public void atq2(Jugador target)
    {
    	if (this.mp - 75 >= 0) {
			this.mp -= 75;
			target.setHp(target.getHp() - this.totalAtq*3);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq3(Jugador target)
    {
    	if (this.mp - 150 >= 0) {
			this.mp -= 150;
			target.setContadorParalisis(2);
			target.setHp(target.getHp() - this.totalAtq/2);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq4(Jugador target)
    {
        if (this.mp - 150 >= 0) {
			this.extraAtaque = 20;
			this.extraVelocidad = 15;
			this.mp -= 150;
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq5(Jugador target)
    {
    	if (this.mp - 300 >= 0) {
			this.extraDefensa = 100;
			this.mp -= 300;
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
	
	@Override
	public int seleccionAtaque(Jugador target) {
		int ataqueElegido = Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:" 
				+ "\n1. Ataque B�sico. Ataca con " + this.totalAtq + " puntos de da�o. Recupera 10 de MP."
				+ "\n2. Triple Golpe. Ataca con " + this.totalAtq*3 + " puntos de da�o. Consume 75 de MP."
				+ "\n3. Fisura de Hueso. Inmoviliza al adversario durante 2 turnos. Consume 150 de MP."
				+ "\n4. Moralizacion. Aumenta el ataque en 20 y la velocidad en 15 durante 3 turnos. Consume 150 de MP."
				+ "\n5. Escudo F�rreo. Aumenta en 100 la defensa durante 4 turnos. Consume 300 de MP.", 5);
    	return ataqueElegido;
	}

	@SuppressWarnings("unused")
	@Override
	public int seleccionAtaqueIA(Jugador player) { //WORK IN PROGRESS
		int ataqueSeleccionado = 1;
		if(true)
		{
			
		}
		else if(true)
		{
			
		}
		else if(true)
		{
			
		}
		else if(true)
		{
			
		}else{
			
		}
		return ataqueSeleccionado;
	}
    
}
