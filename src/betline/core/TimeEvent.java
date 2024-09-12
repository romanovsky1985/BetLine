package betline.core;

/*
 * TimeEvent - привзяка событий некоторого типа (например, хоккейных)
 * к некоторым временным значениям (например, секундам)
 */

public class TimeEvent<T, E> {
    private final T time;
    private final E event;

    public TimeEvent(T time, E event) {
        this.time = time;
        this.event = event;
    }

    public T getTime() {
        return time;
    }

    public E getEvent() {
        return event;
    }
}
