package net.davidog.tbcombat.model;

import java.util.Scanner;

/**
 * La especialidad de Arquero.
 * SEMI-ESTABLE(WIP IA)
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arquero extends Jugador
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2627354092979906055L;

	public Arquero(String nombre)
    {
        super(nombre, 2000, 1700, 200, 30, 30);
        this.clase = "arquero";
    }

	public Arquero(Scanner S)
    {
        this(Util.leerTeclado("¿Cómo te llamas?", S));
    }
    
    public void atq1(Jugador target)
    {
    	if (this.mp + 20 <= 1700) {
			this.mp += 20;
		}else{
			this.mp = 1700;
		}
    	target.setHp(target.getHp() - this.totalAtq);
    }
    
    public void atq2(Jugador target)
    {
        if (this.mp - 300 >= 0) {
        	this.mp -= 300;
        	target.setHp(target.getHp() - this.totalAtq*2);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq3(Jugador target)
    {
    	if (this.mp + 300 <= 1700) {
			this.mp += 300;
			target.setMp(target.getMp() - 300);
			if(target.getMp() < 0) {target.setMp(0);}
		}else{
			this.mp = 1700;
		}
    	target.setHp(target.getHp() - this.totalAtq/3);
    }
    
    public void atq4(Jugador target)
    {
        if (this.mp - 350 >= 0) {
        	this.mp -= 350;
			target.setContadorSangrado(4);//-75
			target.setHp(target.getHp() - this.totalAtq/4);
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    public void atq5(Jugador target)
    {
    	if (this.mp - 300 >= 0) {
			this.extraAtaque = 50;
			this.mp -= 300;
			this.contadorExtraAtaque = 3;
		}else{
			System.out.println(this.nombre + "no pudo atacar por falta de MP.");
		}
    }
    
    @Override
	public int seleccionAtaque(Jugador target) {
		return Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:" 
				+ "\n1. Flechazo. Ataca con " + this.totalAtq + " puntos de da�o. Recupera 20 de MP."
				+ "\n2. Disparo Certero. Ataca con " + this.totalAtq*2 + " puntos de da�o. Consume 300 de MP."
				+ "\n3. Cambiazo. Roba 300 de MP al rival y causa " + this.totalAtq/3 + " de da�o. Recupera 300 de MP."
				+ "\n4. Corte Profundo. Causa sangrado al enemigo que pierde 75 de HP durante 4 turnos y realiza un da�o de " + this.totalAtq/4 + ". Consume 350 de MP."
				+ "\n5. Precisi�n Aumentada. Aumenta en 50 el ataque durante 3 turnos. Consume 300 de MP.", 5);
	}
    
	@Override
	public int seleccionAtaqueIA(Jugador player) { //WORK IN PROGRESS
		return 1;
		/*if(true)
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
			
		}*/
	}
}
