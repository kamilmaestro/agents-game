package game.voting;

import game.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class AcceptingTeamVoting implements Voting {

  @Override
  public Optional<Vote> vote(boolean success, List<Player> players, UUID uuid) {
    return getVotingPlayer(players, uuid)
        .map(player -> new Vote(success, player.getUuid()));
  }

}
