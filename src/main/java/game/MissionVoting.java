package game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

final class MissionVoting implements Voting {

  @Override
  public Optional<Vote> vote(boolean success, List<Player> players, UUID uuid) {
    return getVotingPlayer(players, uuid)
        .map(player -> {
          final boolean result = player.isAgent() || success;
          return new Vote(result, player.getUuid());
        });
  }

}
