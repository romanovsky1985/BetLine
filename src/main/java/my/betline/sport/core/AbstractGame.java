package my.betline.sport.core;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGame {
    protected final Map<String, Number> gameMap;

    public AbstractGame() {
        gameMap = new HashMap<>();
    }

    public AbstractGame(AbstractGame game) {
        this.gameMap = new HashMap<>(game.gameMap);
    }

    public Number get(String param) {
        return gameMap.get(param);
    }

    public void set(String param, Number value) {
        gameMap.put(param, value);
    }
}
