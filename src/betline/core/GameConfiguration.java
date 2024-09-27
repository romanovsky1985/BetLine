package betline.core;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/*
 * Набор параметров для произвольного матча, на основе
 * которого осуществляется моделирование в генераторе.
*/
public abstract class GameConfiguration {

  private final Map<String, Number> configuration;

  public abstract Map<String, Number> getDefault();

  public GameConfiguration(Map<String, Number> currentConfig) {
    HashMap<String, Number> config = new HashMap<>();
    Map<String, Number> defaultConfig = getDefault();
    if (defaultConfig != null) {
      config.putAll(defaultConfig);
    }
    if (currentConfig != null) {
      config.putAll(currentConfig);
    }
    this.configuration = Collections.unmodifiableMap(config);
  }

  public Number get(String name) {
    return configuration.get(name);
  }
}



