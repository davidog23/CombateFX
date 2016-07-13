package net.davidog.tbcombat.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by David on 11/07/2016.
 */
public class Estado {
    public int contador;
    public List<Estados> tag;
    private int applicationCount;
    private Consumer<Jugador> applicator;
    private Consumer<Jugador> removal;

    public Estado(Consumer<Jugador> applicator, int timesToApplyApplicator, Consumer<Jugador> removal, int turnsUntilRemoval) {
        this.applicator = applicator;
        this.removal = removal;
        applicationCount = timesToApplyApplicator;
        contador = turnsUntilRemoval;
        tag = Collections.emptyList();
    }

    public Estado(Consumer<Jugador> applicator, int timesToApplyApplicator, Consumer<Jugador> removal, int turnsUntilRemoval, Estados... tags) {
        this(applicator, timesToApplyApplicator, removal, turnsUntilRemoval);
        tag.addAll(Arrays.asList(tags));
    }

    public void putState(List<Estado> states)
    {
        if (states.contains(this))
        {
            Estado appliedState = states.stream().filter(applied -> applied.equals(this)).findAny().orElse(null);
            if (appliedState != null && appliedState.contador < contador) {
                applicationCount = 0;
                states.remove(appliedState);
            }
        }
        states.add(this);
    }

    public Consumer<Jugador> getApplicator() {
        if (applicationCount > 0) {
            applicationCount--;
            return applicator;
        }
        return jugador -> {};
    }

    public Consumer<Jugador> getRemoval() {
        return removal;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Estado) {
            Estado other = (Estado) o;
            return other.applicator == applicator && other.removal == removal;
        } else {
            return false;
        }
    }

    public enum Estados {
        Paralizado,
        Curandose,
        Sangrado,
        StatsUp,
        StatsDown
    }

    public static String tagToString(Estados stateTag) {
        switch (stateTag)
        {
            case StatsDown:
                return "Estadisticas bajadas";
            case StatsUp:
                return "Estadisticas aumentadas";
            default:
                return stateTag.toString();
        }
    }
}
