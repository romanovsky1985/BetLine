package betline.sport.icehockey;

import betline.core.HomeGuestEventsGame;

/*
 * В хоккейном матче фиксируем события посекундно, поэтому ключи в мапе базового класса
 * Integer, в качестве событий используем элементы соответствующего перечисления.
 *
 */

public class IceHockeyGame extends HomeGuestEventsGame<Integer, IceHockeyEvent> {

    public static void main(String[] args) {
        IceHockeyGame game = new IceHockeyGame();

        game.putGuestEvent(2000, IceHockeyEvent.SCORE);
        game.putHomeEvent(1000, IceHockeyEvent.SCORE);
        game.putGuestEvent(3000, IceHockeyEvent.SCORE);

        int homeScore = (int) game.getHomeEvents().values().stream().filter(e -> e == IceHockeyEvent.SCORE).count();
        int guestScore = (int) game.getGuestEvents().values().stream().filter(e -> e == IceHockeyEvent.SCORE).count();
        System.out.print(homeScore + " : " + guestScore);
    }
}
