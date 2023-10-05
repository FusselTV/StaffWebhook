package tv.fussel.handler;

import tv.fussel.StaffWebhook;
import tv.fussel.discord.Webhook;
import tv.fussel.staff.StaffList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChangeService {
    private final ScheduledExecutorService service;
    private final StaffList staffList;
    private final Webhook webhook;

    public ChangeService(StaffList staffList, Webhook webhook) {
        service = Executors.newSingleThreadScheduledExecutor();
        this.webhook = webhook;
        this.staffList = staffList;

        StaffWebhook.LOGGER.info("Initialized ChangeService");
    }

    public void schedule(long interval, TimeUnit timeUnit) {
        service.scheduleAtFixedRate(this::sendChanges, 0, interval, timeUnit);

        StaffWebhook.LOGGER.info("Schedule ChangeService every: " + interval + " " + timeUnit.name());
    }

    public void shutdown() {
        service.shutdown();
    }

    private void sendChanges() {
        staffList.update();
        List<ChangeEntry> staffChanges = staffList.getStaffChanges();
        if (staffChanges == null) return;
        staffChanges.forEach(webhook::send);
        staffList.clearChanges();
    }
}
