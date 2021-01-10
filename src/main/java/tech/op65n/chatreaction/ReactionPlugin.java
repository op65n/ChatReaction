package tech.op65n.chatreaction;

import com.github.frcsty.frozenactions.wrapper.ActionHandler;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mfmsg.base.MessageOptions;
import me.mattstudios.mfmsg.base.internal.Format;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tech.op65n.chatreaction.command.admin.ReactionStatusCommand;
import tech.op65n.chatreaction.listener.AsyncPlayerChatListener;
import tech.op65n.chatreaction.reaction.runnable.RunnableRegistry;

import java.io.File;
import java.util.Arrays;

public final class ReactionPlugin extends JavaPlugin {

    private final ActionHandler actionHandler = new ActionHandler(this);
    private final RunnableRegistry runnableRegistry = new RunnableRegistry(this);

    @Override
    public void onEnable() {
        saveFiles(
                "reactions/math-reaction.yml",
                "reactions/scramble-reaction.yml",
                "reactions/word-reaction.yml",

                "words/scramble-words.yml"
        );

        actionHandler.loadDefaults(false);
        actionHandler.createBukkitMessage(
                MessageOptions.builder().removeFormat(Format.ITALIC).build()
        );

        runnableRegistry.initialize();

        registerListeners(
                new AsyncPlayerChatListener(this)
        );

        final CommandManager commandManager = new CommandManager(this);

        commandManager.register(
                new ReactionStatusCommand(this)
        );
    }

    public RunnableRegistry getRunnableRegistry() {
        return this.runnableRegistry;
    }

    public ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    private void registerListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(it -> getServer().getPluginManager().registerEvents(it, this));
    }

    private void saveFiles(final String... paths) {
        Arrays.stream(paths).forEach(it -> {
            if (!new File(getDataFolder(), it).exists()) {
                saveResource(it, false);
            }
        });
    }

}
