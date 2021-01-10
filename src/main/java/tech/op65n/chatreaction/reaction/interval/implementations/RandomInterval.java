package tech.op65n.chatreaction.reaction.interval.implementations;

import org.bukkit.configuration.ConfigurationSection;
import tech.op65n.chatreaction.reaction.interval.ReactionInterval;

import java.util.SplittableRandom;

public final class RandomInterval implements ReactionInterval {

    private static final SplittableRandom RANDOM = new SplittableRandom();

    private final int maxBoundary;
    private final int minBoundary;

    public RandomInterval(final ConfigurationSection section) {
        this.maxBoundary = section.getInt("max-boundary");
        this.minBoundary = section.getInt("min-boundary");
    }

    @Override
    public long getInterval() {
        return RANDOM.nextInt(minBoundary, maxBoundary + 1);
    }

}
