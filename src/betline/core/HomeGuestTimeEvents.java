package betline.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Базовый класс для игр, в которых играют хозяева и гости от времени.
 * Рассматриваем игру как два списка (для гостей и хозяев) игровых
 * событий, произошедших в определенные моменты времени.
 */

public abstract class HomeGuestTimeEvents<T, E> {
    protected List<TimeEvent<T, E>> homeEvents = new ArrayList<>();
    protected List<TimeEvent<T, E>> guestEvents = new ArrayList<>();

    public void addHomeEvent(T time, E event) {
        homeEvents.add(new TimeEvent<T, E>(time, event));
    }

    public List<TimeEvent<T, E>> getHomeEvents() {
        return homeEvents;
    }

    public void addGuestEvent(T time, E event) {
        guestEvents.add(new TimeEvent<T, E>(time, event));
    }

    public List<TimeEvent<T, E>> getGuestEvents() {
        return guestEvents;
    }
}