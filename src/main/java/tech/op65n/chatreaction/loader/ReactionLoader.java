package tech.op65n.chatreaction.loader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.loader.holder.ReactionHolder;
import tech.op65n.chatreaction.type.ReactionType;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ReactionLoader {

    private final Set<ReactionHolder> reactions = new HashSet<>();

    private final ReactionPlugin plugin;
    private final Logger logger;

    public ReactionLoader(final ReactionPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        loadReactionsFromDirectory();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadReactionsFromDirectory() {
        final File directory = new File(plugin.getDataFolder() + "/reactions");

        if (!directory.exists())
            directory.mkdir();

        final File[] reactionFiles = directory.listFiles();
        if (reactionFiles == null || reactionFiles.length == 0) {
            logger.log(Level.INFO, "No Reactions were found within 'reactions' directory!");
            return;
        }

        for (final File reactionFile : reactionFiles) {
            final String fileName = reactionFile.getName();
            if (!fileName.contains(".yml")) {
                logger.log(Level.INFO, String.format("File '%s' is not of format .yml, skipping!", fileName));
                continue;
            }

            final FileConfiguration configuration = YamlConfiguration.loadConfiguration(reactionFile);
            final String stringType = configuration.getString("reaction-type");
            final ReactionType type = ReactionType.getNullableType(stringType);

            if (type == null) {
                logger.log(Level.WARNING, String.format("File '%s' contains an unrecognized Reaction Type ('%s')!", fileName, stringType));
                continue;
            }

            reactions.add(new ReactionHolder(plugin, type, configuration));
        }
    }

    public Set<ReactionHolder> getLoadedReactions() {
        return this.reactions;
    }
}
