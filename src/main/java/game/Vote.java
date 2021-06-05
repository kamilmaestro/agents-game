package game;

import java.util.UUID;

final class Vote {

  private final boolean value;
  private final UUID playerUuid;

  Vote(boolean value, UUID playerUuid) {
    this.value = value;
    this.playerUuid = playerUuid;
  }

  boolean isApproval() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Vote vote = (Vote) o;
    return playerUuid.equals(vote.playerUuid);
  }

}
