package game.gameStrategy;

import game.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class TeamCreator {

  private final List<Player> players;
  private final int teamSize;

  TeamCreator(List<Player> players, int teamSize) {
    this.players = players;
    this.teamSize = teamSize;
  }

  List<Player> selectTeamForUuids(Set<UUID> chosenPlayerUuids) {
    return players.stream()
        .filter(player -> chosenPlayerUuids.contains(player.getUuid()))
        .collect(Collectors.toList())
        .subList(0, teamSize);
  }

}
