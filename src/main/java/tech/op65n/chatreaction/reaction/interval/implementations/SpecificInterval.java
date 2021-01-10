package tech.op65n.chatreaction.reaction.interval.implementations;

import org.bukkit.configuration.ConfigurationSection;
import tech.op65n.chatreaction.reaction.interval.ReactionInterval;

public final class SpecificInterval implements ReactionInterval {

    private final long intervalTimer;

    public SpecificInterval(final ConfigurationSection section) {
        this.intervalTimer = section.getLong("interval-timer");
    }

    @Override
    public long getInterval() {
        return this.intervalTimer;
    }
}
