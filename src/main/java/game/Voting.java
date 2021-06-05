package game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface Voting {

  Optional<Vote> vote(boolean success, List<Player> players, UUID uuid);

  default Optional<Player> getVotingPlayer(List<Player> players, UUID uuid) {
    return players.stream()
        .filter(player -> player.getUuid().equals(uuid))
        .findFirst();
  }

}
