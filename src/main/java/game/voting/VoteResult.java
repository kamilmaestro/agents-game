package game.voting;

public enum VoteResult {
  AGENTS_WIN,
  APPROVED,
  IN_PROGRESS,
  REJECTED,
  SPIES_WIN;

  public boolean isCompleted() {
    return equals(APPROVED) || equals(REJECTED);
  }

}
