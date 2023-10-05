package tv.fussel.discord;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import tv.fussel.handler.ChangeEntry;

public class ChangeMessage {
    private final ChangeEntry changeEntry;

    public ChangeMessage(ChangeEntry changeEntry) {
        this.changeEntry = changeEntry;
    }

    public WebhookMessage getMessage() {
        String pvpGymLogo = "https://i.ibb.co/LgnChT7/pvpgymbig.png";

        return new WebhookMessageBuilder()
                .setAvatarUrl(pvpGymLogo)
                .setUsername("PvPGym")
                .addEmbeds(getEmbed())
                .build();
    }

    public WebhookEmbed getEmbed() {
        return new WebhookEmbedBuilder()
                .setAuthor(getFormat().author())
                .setColor(changeEntry.changeType().color.getRGB())
                .setTitle(getEmbedTitle())
                .setDescription(getFormat().friendlyText())
                .setThumbnailUrl("https://visage.surgeplay.com/bust/" + changeEntry.newPlayer().uuid())
                .build();
    }

    private WebhookEmbed.EmbedTitle getEmbedTitle() {
        String accountInfo = "https://laby.net/@" + changeEntry.newPlayer().uuid().toString();
        return new WebhookEmbed.EmbedTitle(changeEntry.newPlayer().username(), accountInfo);
    }

    private EmbedData getFormat() {
        String authorPicture;
        String embedText;
        String authorName;

        switch (changeEntry.changeType()) {
            case JOIN -> {
                authorPicture = "https://s6.gifyu.com/images/S6LCh.gif";
                embedText = "to the Team";
                authorName = "WELCOME";
            }
            case LEFT -> {
                authorPicture = "https://s6.gifyu.com/images/S6LCh.gif";
                embedText = "is no longer Staff";
                authorName = "TEAM LEAVE";
            }
            case DEMOTION -> {
                authorPicture = "https://s6.gifyu.com/images/S6LCh.gif";
                embedText = "was demoted";
                authorName = "DEMOTION";
            }
            case PROMOTION -> {
                authorPicture = "https://s6.gifyu.com/images/S6LCh.gif";
                embedText = "was promoted";
                authorName = "PROMOTION";
            }
            default -> {
                authorPicture = null;
                embedText = "null";
                authorName = "null";
            }
        }
        WebhookEmbed.EmbedAuthor author = new WebhookEmbed.EmbedAuthor(authorName, authorPicture, null);
        return new EmbedData(author, embedText);
    }
}
