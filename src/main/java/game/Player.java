package game;

import java.util.UUID;

public class Player {

  public enum Character {
    AGENT,
    SPY
  }

  private final UUID uuid;
  private final String name;
  private boolean isReady = false;
  private Character character;

  Player(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
    this.character = Character.AGENT;
  }

  public Player assignRole(Character character) {
    this.character = character;
    return this;
  }

  void markAsReady() {
    isReady = true;
  }

  boolean isReady() {
    return isReady;
  }

  boolean isAgent() {
    return character.equals(Character.AGENT);
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

}
