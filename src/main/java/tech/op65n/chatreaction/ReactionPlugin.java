package tech.op65n.chatreaction;

import org.bukkit.plugin.java.JavaPlugin;
import tech.op65n.chatreaction.runnable.RunnableRegistry;

public final class ReactionPlugin extends JavaPlugin {

    private final RunnableRegistry runnableRegistry = new RunnableRegistry(this);

    @Override
    public void onEnable() {
        runnableRegistry.initialize();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
