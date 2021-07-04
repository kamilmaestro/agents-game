package game.gameStrategy;

import game.Player;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SevenPlayersStrategy implements GameStrategy {

  private static final int TEAM_SIZE = 4;

  @Override
  public List<Player> drawSides(Collection<Player> players) {
    return new SidesGenerator(players).drawSidesForNumberOfSpies(3);
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
