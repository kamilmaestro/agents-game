package game;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

final class FivePlayersStrategy implements GameStrategy {

  private static final int TEAM_SIZE = 3;

  @Override
  public List<Player> drawSides(Collection<Player> players) {
    return new SidesGenerator(players).drawSidesForNumberOfSpies(2);
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
