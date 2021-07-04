package game.voting;

import java.util.UUID;

public class Vote {

  private final boolean value;
  private final UUID playerUuid;

  public Vote(boolean value, UUID playerUuid) {
    this.value = value;
    this.playerUuid = playerUuid;
  }

  public boolean isApproval() {
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
