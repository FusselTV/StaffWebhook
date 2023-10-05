package tv.fussel.staff;

import java.util.UUID;

public record Player(UUID uuid, String username, Rank rank) {
}
