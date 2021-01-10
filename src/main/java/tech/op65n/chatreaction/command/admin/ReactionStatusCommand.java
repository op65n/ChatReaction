package tech.op65n.chatreaction.command.admin;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import tech.op65n.chatreaction.ReactionPlugin;
import tech.op65n.chatreaction.reaction.interval.ReactionInterval;
import tech.op65n.chatreaction.reaction.loader.holder.ReactionHolder;
import tech.op65n.chatreaction.reaction.runnable.RunnableRegistry;

@Command("reaction")
public final class ReactionStatusCommand extends CommandBase {

    private final ReactionPlugin plugin;

    public ReactionStatusCommand(final ReactionPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("status")
    @Permission("chatreaction.command.admin.status")
    public void onStatusCommand(final CommandSender sender, final String scope) {
        final RunnableRegistry runnableRegistry = plugin.getRunnableRegistry();

        switch (scope.toUpperCase()) {
            case "ACTIVE":
                runnableRegistry.getActiveReactionEntries().forEach((entry, holder) -> sendReactionHolderInformation(sender, holder, entry));
                break;
            case "ALL":
                runnableRegistry.getLoadedReactions().forEach(holder -> sendReactionHolderInformation(sender, holder, null));
        }
    }

    private void sendReactionHolderInformation(final CommandSender sender, final ReactionHolder holder, final String currentEntry) {
        final StringBuilder builder = new StringBuilder();

        builder.append("Reaction Identifier: ").append(holder.getReactionIdentifier()).append("\n");
        builder.append(" - Type: ").append(holder.getReactionType().name()).append("\n");

        final ReactionInterval interval = holder.getReactionInterval();
        builder.append(" - Interval: ").append(interval.getInterval()).append("\n");

        if (currentEntry != null) {
            builder.append(" - Entry: ").append(currentEntry).append("\n");
        }

        builder.append(" - Rewards: ").append("\n");
        holder.getReactionReward().getReactionCompletionRewards().forEach(reward -> builder.append("  > ").append(reward).append("\n"));

        sender.sendMessage(builder.toString());
    }

}
