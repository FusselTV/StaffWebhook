package tv.fussel;


import org.slf4j.Logger;
import tv.fussel.discord.Webhook;
import tv.fussel.handler.ChangeService;
import tv.fussel.staff.StaffList;

import java.util.concurrent.TimeUnit;

public class StaffWebhook {
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StaffWebhook.class);
    private static final StaffWebhook instance = new StaffWebhook();
    private Webhook webhook;
    private StaffList staffList;
    private ChangeService changeService;

    public static void main(String[] args) {
        instance.start();
        Runtime.getRuntime().addShutdownHook(new Thread(instance::shutdown));
    }

    public static StaffWebhook getInstance() {
        return instance;
    }

    private void start() {
        this.webhook = new Webhook(System.getenv("webhook"));
        this.staffList = new StaffList();
        this.changeService = new ChangeService(staffList, webhook);

        changeService.schedule(30, TimeUnit.MINUTES);
    }

    private void shutdown() {
        this.changeService.shutdown();
        LOGGER.info("ChangeService shutdown");

        this.webhook.shutdown();
        LOGGER.info("Webhook shutdown");
    }
}