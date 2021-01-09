package tech.op65n.chatreaction.loader.holder;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.entry.ReactionEntryType;
import tech.op65n.chatreaction.entry.implementations.ReactionMathEntry;
import tech.op65n.chatreaction.entry.implementations.ReactionWordEntry;
import tech.op65n.chatreaction.interval.ReactionInterval;
import tech.op65n.chatreaction.interval.implementations.RandomInterval;
import tech.op65n.chatreaction.interval.implementations.SpecificInterval;
import tech.op65n.chatreaction.type.ReactionType;

public final class ReactionHolder {

    private final FileConfiguration configuration;
    private final ReactionPlugin plugin;

    private final ReactionType type;
    private final ReactionInterval interval;
    private final ReactionEntryType entryType;

    public ReactionHolder(final ReactionPlugin plugin, final ReactionType type, final FileConfiguration configuration) {
        this.configuration = configuration;
        this.plugin = plugin;

        this.type = type;
        this.interval = assignReactionInterval();
        this.entryType = assignReactionEntryType();
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

    public ReactionType getReactionType() {
        return this.type;
    }

    public ReactionInterval getReactionInterval() {
        return this.interval;
    }

    public ReactionEntryType getReactionEntryType() {
        return this.entryType;
    }
}
