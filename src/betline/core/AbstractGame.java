package betline.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Абстрактный класс некоторого спортивного матча, в
 * основе которого ассоциативный массив с набором значений
 * (например, мат. ожидания, забитые шайб, текущее время и т.д.).
 * С каждым конкретным классом работает свой генератор, который
 * "знает" какие значения есть у конкретного матча и что с ними делать
 */

public abstract class AbstractGame {
    protected final Map<String, Number> game = new HashMap<>();

    public Number get(String param) {
        return game.get(param);
    }

    public void set(String param, Number value) {
        game.put(param, value);
    }

    public Set<String> params() {
        return game.keySet();
    }
}
