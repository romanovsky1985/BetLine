package betline.sport.icehockey;

import betline.core.GameConfiguration;
import java.util.Map;

/*
 * Набор параметров для хоккейного матча, на основе
 * которого осуществляется моделирование в генераторе.
*/
public class IceHockeyConfiguration extends GameConfiguration {

  public static Map<String, Number> DEFAULT = Map.ofEntries(
    Map.entry("homeExpected", 2.5),
    Map.entry("guestExpected", 2.5),
    Map.entry("currentSecond", 0),
    Map.entry("emptyDifference", 2),
    Map.entry("emptyDuration", 70),
    Map.entry("gameDuration", 3600),
    Map.entry("otDuration", 300),
    Map.entry("correction3x3", 2.5),
    Map.entry("correction5x6", 7.5),
    Map.entry("correction6x5", 1.5),
    Map.entry("shootoutLength", 5),
    Map.entry("shootoutProbability", 0.35)
  );

  @Override
  public Map<String, Number> getDefault() {
    return DEFAULT;
  }

  public IceHockeyConfiguration() {
    this(null);
  }

  public IceHockeyConfiguration(Map<String, Number> currentConfig) {
    super(currentConfig);
  }
}



