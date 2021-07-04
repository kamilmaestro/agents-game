package game.voting;

import game.*;

import java.util.List;

public class AcceptingTeamVoteOperator extends VoteOperator {

  public AcceptingTeamVoteOperator(List<Player> players, List<Vote> previousVotes) {
    super(players, previousVotes);
  }

  @Override
  protected Voting createVoting() {
    return new AcceptingTeamVoting();
  }

}
