package game.gameStrategy;

import game.Player;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class NinePlayersStrategy implements GameStrategy {

  private static final int TEAM_SIZE = 5;

  @Override
  public List<Player> drawSides(Collection<Player> players) {
    return new SidesGenerator(players).drawSidesForNumberOfSpies(4);
  }

  @Override
  public List<Player> selectTeam(List<Player> players, Set<UUID> chosenPlayerUuids) {
    return new TeamCreator(players, TEAM_SIZE).selectTeamForUuids(chosenPlayerUuids);
  }

  @Override
  public int getTeamSize() {
    return TEAM_SIZE;
  }

}
