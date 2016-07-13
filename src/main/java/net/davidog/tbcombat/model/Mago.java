package net.davidog.tbcombat.model;

import net.davidog.tbcombat.utils.Util;

import java.util.Scanner;
import java.util.function.Consumer;

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

		attacks[0] = target -> {
			if (this.mp + 30 <= 3000) {
				this.mp += 30;
			}else{
				this.mp = 3000;
			}
			target.takeDamage(atq);
		};

		attacks[1] = target -> {
			if (this.mp - 100 > 0) {
				this.mp -= 100;
				target.takeDamage(atq*2);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[2] = target -> {
			if (this.mp - 400 >= 0) {
				this.mp -= 400;
				Consumer<Jugador> applicator = player -> player.willAttack = false;
				Consumer<Jugador> removal = player -> player.willAttack = true;
				target.addState(new Estado(applicator, 1, removal, 2, Estado.Estados.Paralizado), false);
				target.takeDamage(atq/3);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[3] = target -> {
			if (this.mp - 300 >= 0) {
				this.mp -= 300;
                Consumer<Jugador> applicator = player -> { player.atq += 50; player.def += 30; };
                Consumer<Jugador> removal = player -> { player.atq -= 50; player.def -= 30; };
                addState(new Estado(applicator, 1, removal, 2, Estado.Estados.StatsUp), false);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};

		attacks[4] = target -> {
			if (this.mp - 500 >= 0) {
				this.mp -= 500;
                Consumer<Jugador> applicator = player -> player.hp += 200;
                Consumer<Jugador> removal = player -> { };
                addState(new Estado(applicator, 3, removal, 3, Estado.Estados.Curandose), false);
			}else{
				System.out.println(this.nombre + "no pudo atacar por falta de MP.");
			}
		};
    }

	public Mago(Scanner S)
	{
		this(Util.leerTeclado("¿Cómo te llamas?", S));
	}
    
    @Override
	public int seleccionAtaque(Jugador target, Scanner S) {
		return Util.menu(this.nombre + " es " + this.clase +"                     "+ target.nombre + " es " + target.clase
				+ "\nHP: " + this.hp + "  MP: " + this.mp + "                     HP: " + target.hp
				+ "\nTus ataques:" 
				+ "\n1. Disparo Mágico. Ataca con " + this.atq +" puntos de daño. Recupera 30 de MP."
				+ "\n2. Cañonazo Rúnico. Ataca con " + this.atq*2 + " puntos de daño. Consume 100 de MP."
				+ "\n3. Sellado. Inmoviliza al adversario durante 2 turnos. Consume 400 de MP."
				+ "\n4. Bendición Arcana. Aumenta el ataque en 50 y la defensa en 30 durante 2 turnos. Consume 300 de MP."
				+ "\n5. Curación Sagrada. Cura 200 de HP al principio de cada turno durante 4 turnos. Consume 500 de MP.", 5, S);
	}

	@Override
	public int seleccionAtaqueIA(Jugador player) {
		int ataqueSeleccionado;
		if(this.hp <= 300 && this.mp >= 500 && !isTaggedAs(Estado.Estados.Curandose))
		{
			ataqueSeleccionado = 5;
		}
		else if(isTaggedAs(Estado.Estados.Curandose) && this.mp >= 400 && !player.isTaggedAs(Estado.Estados.Paralizado))
		{
			ataqueSeleccionado = 3;
		}
		else if(isTaggedAs(Estado.Estados.Curandose) && player.isTaggedAs(Estado.Estados.Paralizado) && this.mp >= 300)
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
