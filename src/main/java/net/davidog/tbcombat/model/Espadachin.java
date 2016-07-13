package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.util.Scanner;
import java.util.function.Consumer;

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

		attacks[0] = target -> {
			if (this.mp + 10 <= 900) {
				this.mp += 10;
			}else{
				this.mp = 900;
			}
			target.takeDamage(atq);
		};

		attacks[1] = target -> {
			if (this.mp - 75 >= 0) {
				this.mp -= 75;
				target.takeDamage(atq*3);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[2] = target -> {
			if (this.mp - 150 >= 0) {
				this.mp -= 150;
                Consumer<Jugador> applicator = player -> player.willAttack = false;
                Consumer<Jugador> removal = player -> player.willAttack = true;
                target.addState(new Estado(applicator, 1, removal, 2, Estado.Estados.Paralizado), false);
				target.takeDamage(atq/2);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[3] = target -> {
			if (this.mp - 150 >= 0) {
                Consumer<Jugador> applicator = player -> { player.atq += 20; player.vel += 15; };
                Consumer<Jugador> removal = player -> { player.atq -= 20; player.vel -= 15; };
                addState(new Estado(applicator, 1, removal, 3, Estado.Estados.StatsUp), false);
				this.mp -= 150;
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[4] = target -> {
			if (this.mp - 300 >= 0) {
                Consumer<Jugador> applicator = player -> player.def += 100;
                Consumer<Jugador> removal = player -> player.def -= 100;
                addState(new Estado(applicator, 1, removal, 4, Estado.Estados.StatsUp), false);
                this.mp -= 300;
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};
    }

	public Espadachin(Scanner S)
	{
		this(Util.leerTeclado("¿Cómo te llamas?", S));
	}
	
	@Override
	public int seleccionAtaque(Jugador target, Scanner S) {
		return Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:"
				+ "\n1. Ataque B�sico. Ataca con " + this.atq + " puntos de da�o. Recupera 10 de MP."
				+ "\n2. Triple Golpe. Ataca con " + this.atq*3 + " puntos de da�o. Consume 75 de MP."
				+ "\n3. Fisura de Hueso. Inmoviliza al adversario durante 2 turnos. Consume 150 de MP."
				+ "\n4. Moralizacion. Aumenta el ataque en 20 y la velocidad en 15 durante 3 turnos. Consume 150 de MP."
				+ "\n5. Escudo F�rreo. Aumenta en 100 la defensa durante 4 turnos. Consume 300 de MP.", 5, S);
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
