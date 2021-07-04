package game.gameStrategy;

import game.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class SidesGenerator {

  private final List<Player> players;

  SidesGenerator(Collection<Player> players) {
    this.players = new ArrayList<>(players);
  }

  List<Player> drawSidesForNumberOfSpies(int numberOfSpies) {
    Collections.shuffle(players);
    return IntStream.range(0, players.size())
        .mapToObj(index -> assignRole(numberOfSpies, index))
        .collect(Collectors.toList());
  }

  private Player assignRole(int numberOfSpies, int index) {
    final Player player = players.get(index);
    return index < numberOfSpies ?
        player.assignRole(Player.Character.SPY)
        : player.assignRole(Player.Character.AGENT);
  }

}
