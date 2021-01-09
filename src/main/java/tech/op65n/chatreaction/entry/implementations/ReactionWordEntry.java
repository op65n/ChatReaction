package tech.op65n.chatreaction.entry.implementations;

import org.bukkit.configuration.ConfigurationSection;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.entry.ReactionEntryType;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public final class ReactionWordEntry implements ReactionEntryType {

    private static final SplittableRandom RANDOM = new SplittableRandom();

    private final List<String> entries = new ArrayList<>();
    private final ConfigurationSection section;

    private final EntrySelectionStyle selectionStyle;

    private int lastEntryIndex;

    public ReactionWordEntry(final ReactionPlugin plugin, final ConfigurationSection section) {
        this.section = section;

        this.selectionStyle = EntrySelectionStyle.getSelectionStyle(section.getString("entry-selection"));
        loadEntries(plugin);
    }

    @Override
    public String getCurrentEntry() {
        switch (selectionStyle) {
            case RANDOM:
                lastEntryIndex = RANDOM.nextInt(entries.size());
                return entries.get(lastEntryIndex);
            case ORDERED:
                if (lastEntryIndex >= entries.size()) {
                    lastEntryIndex = 0;
                }

                return entries.get(lastEntryIndex++);
        }

        return "Failed To Retrieve Valid Reaction Entry!";
    }

    private void loadEntries(final ReactionPlugin plugin) {

    }

    private enum EntrySelectionStyle {

        RANDOM(),
        ORDERED();

        private static EntrySelectionStyle getSelectionStyle(final String identifier) {
            EntrySelectionStyle style = null;

            for (final EntrySelectionStyle entrySelectionStyle : values()) {
                if (!identifier.equalsIgnoreCase(entrySelectionStyle.name())) {
                    continue;
                }

                style = entrySelectionStyle;
                break;
            }

            return style;
        }

    }

}
