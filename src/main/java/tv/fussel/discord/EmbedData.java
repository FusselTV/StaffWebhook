package tv.fussel.discord;

import club.minnced.discord.webhook.send.WebhookEmbed;

public record EmbedData(WebhookEmbed.EmbedAuthor author, String friendlyText) {
}
