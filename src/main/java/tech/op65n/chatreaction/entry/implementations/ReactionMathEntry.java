package tech.op65n.chatreaction.entry.implementations;

import org.bukkit.configuration.ConfigurationSection;
import tech.op65n.chatreaction.entry.ReactionEntryType;

public final class ReactionMathEntry implements ReactionEntryType {

    private final ConfigurationSection section;

    public ReactionMathEntry(final ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public String getCurrentEntry() {
        return null;
    }
}
