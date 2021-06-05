package game;

import java.util.Optional;
import java.util.UUID;

interface PlayerRepository {

  Player save(Player player);

  Optional<Player> findByUuid(UUID uuid);

}
