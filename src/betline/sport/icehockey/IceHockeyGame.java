package betline.sport.icehockey;

import betline.core.HomeGuestTimeEvents;

import java.util.ArrayList;

/*
 * В хоккейном матче фиксируем события посекундно, поэтому
 * временной параметр базового класса Integer
 */

public class IceHockeyGame extends HomeGuestTimeEvents<Integer, IceHockeyEvent> {
    private final IceHockeyConfiguration config;

    public IceHockeyGame(IceHockeyConfiguration config) {
        super();
        this.config = config;
    }

    public IceHockeyGame() {
        this(new IceHockeyConfiguration());
    }

    public IceHockeyGame(IceHockeyGame game) {
        homeEvents = new ArrayList<>(game.homeEvents);
        guestEvents = new ArrayList<>(game.guestEvents);
        config = game.config;
    }

    public IceHockeyConfiguration getConfiguration() {
        return config;
    }

}
