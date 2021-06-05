package game;

public enum VoteResult {
  APPROVED,
  IN_PROGRESS,
  REJECTED;

  boolean isCompleted() {
    return equals(APPROVED) || equals(REJECTED);
  }

}
