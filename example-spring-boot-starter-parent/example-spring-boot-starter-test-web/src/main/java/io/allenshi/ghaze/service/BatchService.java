package io.allenshi.ghaze.service;

import io.allenshi.ghaze.dto.Task;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

public class BatchService implements DisposableBean {
    private final ExecutorService executorService =
            new ThreadPoolExecutor(2, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1000),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r, "cust-batch-thread");
                        }
                    });


    private boolean waitForTasksToCompleteOnShutdown = false;
    private long awaitTerminationSeconds;

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
        this.awaitTerminationSeconds = awaitTerminationSeconds;
    }

    public void doBatchTask(final Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("executed task..." + task.getTaskDesc());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }

    private void shutdown() {
        System.out.println("shutdown...");
        if (this.waitForTasksToCompleteOnShutdown) {
            this.executorService.shutdown();
        } else {
            this.executorService.shutdownNow();
        }
        awaitTerminationIfNecessary();
    }

    /**
     * Wait for the executor to terminate, according to the value of the
     * {@link #setAwaitTerminationSeconds "awaitTerminationSeconds"} property.
     */
    private void awaitTerminationIfNecessary() {
        if (this.awaitTerminationSeconds > 0) {
            System.out.println("awaitTerminationSeconds: " + awaitTerminationSeconds);
            try {
                this.executorService.awaitTermination(this.awaitTerminationSeconds, TimeUnit.SECONDS);
                System.out.println("finish to wait...");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
