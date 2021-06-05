package game;

import java.util.List;

final class MissionVoteOperator extends VoteOperator {

  MissionVoteOperator(List<Player> players, List<Vote> previousVotes) {
    super(players, previousVotes);
  }

  @Override
  protected Voting createVoting() {
    return new MissionVoting();
  }

}
