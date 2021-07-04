package game.voting;

import game.*;

import java.util.List;

public class MissionVoteOperator extends VoteOperator {

  public MissionVoteOperator(List<Player> players, List<Vote> previousVotes) {
    super(players, previousVotes);
  }

  @Override
  protected Voting createVoting() {
    return new MissionVoting();
  }

}
