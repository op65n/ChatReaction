package tech.op65n.chatreaction.runnable;

import org.bukkit.Bukkit;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.entry.ReactionEntryType;
import tech.op65n.chatreaction.interval.ReactionInterval;
import tech.op65n.chatreaction.loader.ReactionLoader;
import tech.op65n.chatreaction.loader.holder.ReactionHolder;
import tech.op65n.chatreaction.type.Reaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RunnableRegistry {

    private final Map<String, ReactionHolder> activeReactionEntries = new HashMap<>();

    private final ReactionLoader reactionLoader;
    private final ReactionPlugin plugin;
    private final Logger logger;

    public RunnableRegistry(final ReactionPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        this.reactionLoader = new ReactionLoader(plugin);
    }

    public void initialize() {
        final Set<ReactionHolder> reactions = reactionLoader.getLoadedReactions();

        for (final ReactionHolder reactionHolder : reactions) {

            final ReactionEntryType entryType = reactionHolder.getReactionEntryType();
            final ReactionInterval reactionInterval = reactionHolder.getReactionInterval();

            if (reactionInterval == null) {
                logger.log(Level.WARNING, "Reaction Interval was null for Reaction, skipping execution!");
                continue;
            }

            if (entryType == null) {
                logger.log(Level.WARNING, "Reaction Entry Type was null for Reaction, skipping execution!");
                continue;
            }

            scheduleTask(reactionHolder, reactionInterval, entryType);
        }
    }

    private void scheduleTask(final ReactionHolder holder, final ReactionInterval interval, final ReactionEntryType entryType) {
        this.activeReactionEntries.put(entryType.getCurrentEntry(), holder);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

        }, interval.getInterval());
    }

}
