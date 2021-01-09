package tech.op65n.chatreaction.entry.implementations;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.entry.ReactionEntryType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ReactionWordEntry implements ReactionEntryType {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    private final Logger logger;

    private final List<String> entries = new ArrayList<>();
    private final ConfigurationSection section;

    private final EntrySelectionStyle selectionStyle;

    private int lastEntryIndex;

    public ReactionWordEntry(final ReactionPlugin plugin, final ConfigurationSection section) {
        this.logger = plugin.getLogger();
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
        final boolean fromFile = section.get("contents") != null;

        if (!fromFile) {
            entries.addAll(section.getStringList("contents"));
            return;
        }

        final String filePath = section.getString("contents-file");
        if (filePath == null) {
            logger.log(Level.WARNING, "Contents file path could not be found!");
            return;
        }

        final File file = new File(plugin.getDataFolder(), filePath);
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (configuration.get("contents") == null) {
            logger.log(Level.WARNING, String.format("Contents could not be found for file at path '%s'", filePath));
            return;
        }

        entries.addAll(configuration.getStringList("contents"));
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
