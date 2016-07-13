package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * La especialidad de Arquero.
 * SEMI-ESTABLE(WIP IA)
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arquero extends Jugador
{
	public static final int HP_MAX = 2000, MP_MAX = 1700;
    /**
	 * 
	 */
	private static final long serialVersionUID = 2627354092979906055L;

	public Arquero(String nombre)
    {
        super(nombre, 2000, 1700, 200, 30, 30);
        this.clase = "arquero";

		attacks[0] = target -> {
			if (this.mp + 20 <= 1700) {
				this.mp += 20;
			}else{
				this.mp = 1700;
			}
			target.takeDamage(atq);
		};

		attacks[1] = target -> {
			if (this.mp - 300 >= 0) {
				this.mp -= 300;
				target.takeDamage(atq*2);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[2] = target -> {
			if (target.mp >= 300) {
				target.mp -= 300;
				if (mp <= MP_MAX - 300) {
					mp += 300;
				} else {
					mp = MP_MAX;
				}
			} else {
				mp += target.mp;
				target.mp = 0;
			}
			target.takeDamage(atq/3);
		};

		attacks[3] = target -> {
			if (this.mp >= 350) {
				this.mp -= 350;
				Consumer<Jugador> applicator = player -> player.hp -= 75;
				Consumer<Jugador> removal = player -> {};
				target.addState(new Estado(applicator, 4, removal, 4, Estado.Estados.Sangrado), false);
				target.takeDamage(atq/4);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[4] = target -> {
			if (this.mp >= 300) {
                Consumer<Jugador> applicator = player -> player.atq += 50;
                Consumer<Jugador> removal = player -> player.atq -= 50;
                addState(new Estado(applicator, 1, removal, 3, Estado.Estados.StatsUp), false);
				this.mp -= 300;
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};
    }

	public Arquero(Scanner S)
    {
        this(Util.leerTeclado("¿Cómo te llamas?", S));
    }
    
    @Override
	public int seleccionAtaque(Jugador target, Scanner S) {
		return Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:" 
				+ "\n1. Flechazo. Ataca con " + this.atq + " puntos de da�o. Recupera 20 de MP."
				+ "\n2. Disparo Certero. Ataca con " + this.atq*2 + " puntos de da�o. Consume 300 de MP."
				+ "\n3. Cambiazo. Roba 300 de MP al rival y causa " + this.atq/3 + " de da�o. Recupera 300 de MP."
				+ "\n4. Corte Profundo. Causa sangrado al enemigo que pierde 75 de HP durante 4 turnos y realiza un da�o de " + this.atq/4 + ". Consume 350 de MP."
				+ "\n5. Precisi�n Aumentada. Aumenta en 50 el ataque durante 3 turnos. Consume 300 de MP.", 5, S);
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
