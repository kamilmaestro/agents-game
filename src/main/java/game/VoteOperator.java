package game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

abstract class VoteOperator {

  private final List<Player> players;
  private final List<Vote> votes;

  VoteOperator(List<Player> players, List<Vote> votes) {
    this.players = players;
    this.votes = votes;
  }

  enum VoteMode {
    ACCEPTING_TEAM,
    MISSION
  }

  VoteResult vote(boolean success, UUID uuid) {
    final Voting voting = createVoting();
    final Optional<Vote> vote = voting.vote(success, players, uuid);
    if (vote.isPresent() && !votes.contains(vote.get())) {
      votes.add(vote.get());
    }
    if (votes.size() == players.size()) {
      return cancelVoting();
    } else {
      return VoteResult.IN_PROGRESS;
    }
  }

  private VoteResult cancelVoting() {
    final long approvalVotes = votes.stream()
        .filter(Vote::isApproval)
        .count();

    return approvalVotes > (votes.size() / 2) ?
        VoteResult.APPROVED
        : VoteResult.REJECTED;
  }

  protected abstract Voting createVoting();

}
