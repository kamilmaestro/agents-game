package game;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

interface GameStrategy {

  List<Player> drawSides(Collection<Player> players);

  List<Player> selectTeam(List<Player> players, Set<UUID> chosenPlayerUuids);

  int getTeamSize();

}
