package tv.fussel.handler;

import tv.fussel.staff.Player;

public record ChangeEntry(Player oldPlayer, Player newPlayer, ChangeType changeType) {
}
