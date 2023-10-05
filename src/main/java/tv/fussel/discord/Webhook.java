package tv.fussel.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import tv.fussel.StaffWebhook;
import tv.fussel.handler.ChangeEntry;
import tv.fussel.util.DelayedExecutor;

import java.util.concurrent.TimeUnit;

public class Webhook {
    private final WebhookClient webhookClient;
    private final DelayedExecutor executor;

    public Webhook(String url) {
        executor = new DelayedExecutor(5, TimeUnit.SECONDS);
        webhookClient = new WebhookClientBuilder(url).build();

        StaffWebhook.LOGGER.info("Established Webhook connection");
    }

    public void send(ChangeEntry changeEntry) {
        executor.send(() -> webhookClient.send(new ChangeMessage(changeEntry).getMessage()));
    }

    public void shutdown() {
        executor.shutdown();
        webhookClient.close();
    }
}
