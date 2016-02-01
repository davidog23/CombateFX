package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.util.Scanner;

/**
 * La especialidad de mago.
 * SEMI-ESTABLE(WIP IA)
 * @author David Olmos
 * @version 0.1
 */
public class Mago extends Jugador
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2420840800686448732L;

	public Mago(String nombre)
    {
        super(nombre, 1200, 3000, 100, 50, 10);
        this.clase = "mago";
    }

	public Mago(Scanner S)
	{
		this(Util.leerTeclado("¿Cómo te llamas?", S));
	}
    
    public void atq1(Jugador target)
    {
    	if (this.mp + 30 <= 3000) {
			this.mp += 30;
		}else{
			this.mp = 3000;
		}
    	target.setHp(target.getHp() - this.totalAtq);
    }
    
    public void atq2(Jugador target)
    {
        if (this.mp - 100 > 0) {
        	this.mp -= 100;
        	target.setHp(target.getHp() - this.totalAtq*2);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq3(Jugador target)
    {
    	if (this.mp - 400 >= 0) {
			this.mp -= 400;
			target.setContadorParalisis(2);
			target.setHp(target.getHp() - this.totalAtq/3);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq4(Jugador target)
    {
    	if (this.mp - 300 >= 0) {
			this.mp -= 300;
			this.extraAtaque = 50;
			this.extraDefensa = 30;
			this.contadorExtraAtaque = 2;
			this.contadorExtraDefensa = 2;
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq5(Jugador target)
    {
    	if (this.mp - 500 >= 0) {
			this.mp -= 500;
			this.contadorCuracion = 3;
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    @Override
	public int seleccionAtaque(Jugador target) {
		return Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:" 
				+ "\n1. Disparo M�gico. Ataca con " + this.totalAtq +" puntos de da�o. Recupera 30 de MP."
				+ "\n2. Ca�onazo R�nico. Ataca con " + this.totalAtq*2 + " puntos de da�o. Consume 100 de MP."
				+ "\n3. Sellado. Inmoviliza al adversario durante 2 turnos. Consume 400 de MP."
				+ "\n4. Bendici�n Arcana. Aumenta el ataque en 50 y la defensa en 30 durante 2 turnos. Consume 300 de MP."
				+ "\n5. Curaci�n Sagrada. Cura 200 de HP al principio de cada turno durante 4 turnos. Consume 500 de MP.", 5);
	}

	@Override
	public int seleccionAtaqueIA(Jugador player) {
		int ataqueSeleccionado;
		if(this.hp <= 300 && this.mp >= 500 && !this.isCurandose())
		{
			ataqueSeleccionado = 5;
		}
		else if(this.isCurandose() && this.mp >= 400 && !player.isParalizado())
		{
			ataqueSeleccionado = 3;
		}
		else if(this.isCurandose() && player.isParalizado() && this.mp >= 300)
		{
			ataqueSeleccionado = 4;
		}
		else if(this.mp >= 100)
		{
			ataqueSeleccionado = 2;
		}else{
			ataqueSeleccionado = 1;
		}
		return ataqueSeleccionado;
	}
}
