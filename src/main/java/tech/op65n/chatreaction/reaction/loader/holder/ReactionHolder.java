package tech.op65n.chatreaction.reaction.loader.holder;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.reaction.entry.ReactionEntryType;
import tech.op65n.chatreaction.reaction.entry.implementations.ReactionMathEntry;
import tech.op65n.chatreaction.reaction.entry.implementations.ReactionWordEntry;
import tech.op65n.chatreaction.reaction.interval.ReactionInterval;
import tech.op65n.chatreaction.reaction.interval.implementations.RandomInterval;
import tech.op65n.chatreaction.reaction.interval.implementations.SpecificInterval;
import tech.op65n.chatreaction.reaction.reward.ReactionReward;
import tech.op65n.chatreaction.reaction.type.ReactionType;

public final class ReactionHolder {

    private final FileConfiguration configuration;
    private final ReactionPlugin plugin;

    private final String identifier;
    private final ReactionType type;
    private final ReactionInterval interval;
    private final ReactionEntryType entryType;
    private final ReactionReward reward;

    public ReactionHolder(final ReactionPlugin plugin, final ReactionType type, final FileConfiguration configuration) {
        this.configuration = configuration;
        this.plugin = plugin;

        this.identifier = configuration.getString("reaction-identifier");
        this.type = type;
        this.interval = assignReactionInterval();
        this.entryType = assignReactionEntryType();
        this.reward = assignReactionReward();
    }

    private ReactionInterval assignReactionInterval() {
        final ConfigurationSection section = configuration.getConfigurationSection("settings.timer");
        if (section == null) {
            return null;
        }

        return section.get("interval-timer") != null ? new SpecificInterval(section) : new RandomInterval(section);
    }

    @SuppressWarnings("ConstantConditions")
    private ReactionEntryType assignReactionEntryType() {
        final ConfigurationSection section = configuration.getConfigurationSection("entries");
        if (section == null) {
            return null;
        }

        return section.getString("entry-type", "WORD").equalsIgnoreCase("WORD") ? new ReactionWordEntry(plugin, section) : new ReactionMathEntry(section);
    }

    private ReactionReward assignReactionReward() {
        if (configuration.get("reward") == null) {
            return null;
        }

        return () -> configuration.getStringList("reward");
    }

    public ReactionType getReactionType() {
        return this.type;
    }

    public ReactionInterval getReactionInterval() {
        return this.interval;
    }

    public ReactionEntryType getReactionEntryType() {
        return this.entryType;
    }

    public ReactionReward getReactionReward() {
        return this.reward;
    }

    public String getReactionIdentifier() {
        return this.identifier;
    }
}
