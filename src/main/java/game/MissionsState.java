package game;

import game.voting.VoteResult;

import java.util.ArrayList;
import java.util.List;

import static game.voting.VoteResult.APPROVED;

final class MissionsState {

  private final List<Boolean> missionSuccesses;

  MissionsState() {
    missionSuccesses = new ArrayList<>();
  }

  public VoteResult updateMission(VoteResult voteResult) {
    missionSuccesses.add(APPROVED.equals(voteResult));
    if (agentsHasWon()) {
      return VoteResult.AGENTS_WIN;
    } else if (spiesHasWon()) {
      return VoteResult.SPIES_WIN;
    } else {
      return voteResult;
    }
  }

  private boolean agentsHasWon() {
    return missionSuccesses.stream()
        .filter(wasMissionSuccessful -> wasMissionSuccessful.equals(true))
        .count() >= 3;
  }

  private boolean spiesHasWon() {
    return missionSuccesses.stream()
        .filter(wasMissionSuccessful -> wasMissionSuccessful.equals(false))
        .count() >= 3;
  }

}
