package game;

import java.util.List;

final class AcceptingTeamVoteOperator extends VoteOperator {

  AcceptingTeamVoteOperator(List<Player> players, List<Vote> previousVotes) {
    super(players, previousVotes);
  }

  @Override
  protected Voting createVoting() {
    return new AcceptingTeamVoting();
  }

}
