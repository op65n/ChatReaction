package tech.op65n.chatreaction.reaction.runnable;

import org.bukkit.Bukkit;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.reaction.entry.ReactionEntryType;
import tech.op65n.chatreaction.reaction.interval.ReactionInterval;
import tech.op65n.chatreaction.reaction.loader.ReactionLoader;
import tech.op65n.chatreaction.reaction.loader.holder.ReactionHolder;

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
        final String reactionEntry = entryType.getCurrentEntry();
        this.activeReactionEntries.put(reactionEntry, holder);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (getReactionHolderByEntry(reactionEntry) == null) {
                return;
            }



            removeReactionHolderByEntry(reactionEntry);
        }, interval.getInterval());
    }

    public ReactionHolder getReactionHolderByEntry(final String entry) {
        return this.activeReactionEntries.get(entry);
    }

    public void removeReactionHolderByEntry(final String entry) {
        this.activeReactionEntries.remove(entry);
    }

    public Map<String, ReactionHolder> getActiveReactionEntries() {
        return this.activeReactionEntries;
    }

    public Set<ReactionHolder> getLoadedReactions() {
        return this.reactionLoader.getLoadedReactions();
    }

}
