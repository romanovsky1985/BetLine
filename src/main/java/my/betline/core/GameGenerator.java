package my.betline.core;

/*
 * Генератор - случайным образом "доигрывает" переданный матч,
 * реализуя моделирование методом Монте-Карло
 */
public interface GameGenerator<G> {
    G generate(G game);
}
