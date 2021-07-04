package game.gameStrategy;

import game.Player;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameStrategy {

  List<Player> drawSides(Collection<Player> players);

  List<Player> selectTeam(List<Player> players, Set<UUID> chosenPlayerUuids);

  int getTeamSize();

}
