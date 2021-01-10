package tech.op65n.chatreaction.listener;

import com.github.frcsty.frozenactions.wrapper.ActionHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.reaction.loader.holder.ReactionHolder;
import tech.op65n.chatreaction.reaction.reward.ReactionReward;
import tech.op65n.chatreaction.reaction.runnable.RunnableRegistry;

public final class AsyncPlayerChatListener implements Listener {

    private final ActionHandler actionHandler;
    private final RunnableRegistry runnableRegistry;

    public AsyncPlayerChatListener(final ReactionPlugin plugin) {
        this.actionHandler = plugin.getActionHandler();
        this.runnableRegistry = plugin.getRunnableRegistry();
    }

    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        final ReactionHolder reactionHolder = runnableRegistry.getReactionHolderByEntry(message);
        if (reactionHolder == null) {
            return;
        }

        final ReactionReward reactionReward = reactionHolder.getReactionReward();
        if (reactionReward != null) {
            actionHandler.execute(player, reactionReward.getReactionCompletionRewards());
        }

        runnableRegistry.removeReactionHolderByEntry(message);
    }

}
