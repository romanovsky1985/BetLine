package betline.core;

import java.util.HashMap;
import java.util.Map;

/*
 * Любую игру между двумя командами рассматриваем как наборы
 * игровых событий фиксируемых от времени, подсчитывая
 * интересующие события (например, забитые шайбы) можно
 * оценивать вероятности (например, определенное количество шайб)
 */

public abstract class HomeGuestEventsGame<T, E> {
    protected HashMap<T, E> homeEvents = new HashMap<>();
    protected HashMap<T, E> guestEvents = new HashMap<>();

    public void putHomeEvent(T time, E event) {
        homeEvents.put(time, event);
    }

    public Map<T, E> getHomeEvents() {
        return homeEvents;
    }

    public void putGuestEvent(T time, E event) {
        guestEvents.put(time, event);
    }

    public Map<T, E> getGuestEvents() {
        return guestEvents;
    }
}