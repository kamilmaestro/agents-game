package game;

import game.gameStrategy.GameStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class Game {

  private GameStatus status;
  private List<Player> players;
  private List<Player> currentTeam;
  private int leaderIndex;

  private Game() {
    this.players = new ArrayList<>();
    this.currentTeam = new ArrayList<>();
    this.status = GameStatus.WAITING;
    this.leaderIndex = 0;
  }

  static Game createNew() {
    return new Game();
  }

  void addPlayer(Player player) {
    players.add(player);
  }

  Game start(GameStrategy strategy) {
    this.status = GameStatus.STARTED;
    players = strategy.drawSides(players);
    return this;
  }

  void markPlayerAsReady(UUID userUuid) {
    players.stream()
        .filter(player -> player.getUuid().equals(userUuid))
        .findAny()
        .ifPresent(Player::markAsReady);
  }

  UUID selectNewLeader() {
    if (leaderIndex >= players.size()) {
      leaderIndex = 0;
    }
    final UUID leaderUuid = players.get(leaderIndex).getUuid();
    leaderIndex++;

    return leaderUuid;
  }

  List<Player> selectTeam(Set<UUID> chosenPlayers, GameStrategy strategy) {
    currentTeam = strategy.selectTeam(players, chosenPlayers);
    return currentTeam;
  }

  List<Player> getPlayers() {
    return players;
  }

  List<Player> getCurrentTeam() {
    return currentTeam;
  }

  public boolean hasStarted() {
    return GameStatus.STARTED.equals(status);
  }

}
