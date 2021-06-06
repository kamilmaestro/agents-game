package game;

public enum VoteResult {
  AGENTS_WIN,
  APPROVED,
  IN_PROGRESS,
  REJECTED,
  SPIES_WIN;

  boolean isCompleted() {
    return equals(APPROVED) || equals(REJECTED);
  }

}
