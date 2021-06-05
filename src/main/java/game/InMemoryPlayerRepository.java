package game;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryPlayerRepository implements PlayerRepository {

  Map<UUID, Player> values = new ConcurrentHashMap<>();

  @Override
  public Player save(Player player) {
    values.put(player.getUuid(), player);
    return player;
  }

  @Override
  public Optional<Player> findByUuid(UUID uuid) {
    return Optional.ofNullable(values.get(uuid));
  }

}
