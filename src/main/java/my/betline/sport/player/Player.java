package my.betline.sport.player;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Player {
    private final String name;
    private double goalsByScore;
    private double assistsByScore;
}
