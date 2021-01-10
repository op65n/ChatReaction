package tech.op65n.chatreaction.reaction.type;

public enum ReactionType {

    MATH_REACTION("MATH"),
    WORD_REACTION("WORD"),
    SCRAMBLE_REACTION("SCRAMBLE"),
    ;

    private final String identifier;

    ReactionType(final String identifier) {
        this.identifier = identifier;
    }

    public static ReactionType getNullableType(final String identifier) {
        ReactionType type = null;

        for (final ReactionType reactionType : values()) {
            if (!identifier.equalsIgnoreCase(reactionType.identifier)) {
                continue;
            }

            type = reactionType;
            break;
        }

        return type;
    }

}
