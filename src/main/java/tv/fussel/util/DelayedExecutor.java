package tv.fussel.util;

import java.util.concurrent.*;

public class DelayedExecutor {
    private final ScheduledExecutorService executor;
    private final long delay;
    private final TimeUnit timeUnit;
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public DelayedExecutor(long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        scheduleExecution();
    }

    public void send(Runnable runnable) {
        queue.offer(runnable);
    }

    private void scheduleExecution() {
        executor.schedule(() -> {
            Runnable task = queue.poll();
            if (task != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            scheduleExecution(); // Re-schedule for the next task
        }, delay, timeUnit);
    }

    public void shutdown() {
        executor.shutdown();
    }
}