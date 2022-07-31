package io.allenshi.ghaze.service;

import io.allenshi.ghaze.dto.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Slf4j
public class BatchService implements DisposableBean {
    private final ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(100),
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
        executorService.execute(() -> {
            log.info("executed task..." + task.getTaskDesc());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }

    private void shutdown() {
        log.info("BatchService tasks shutdown...");
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
            log.info("BatchService awaitTerminationSeconds: " + awaitTerminationSeconds);
            try {
                BlockingQueue<Runnable> queuedTasks = executorService.getQueue();
                log.info("QueuedTask number before is {}, completed task count is {}, " +
                                "active thread number is {}, total task number had ever been scheduled is {}, current thread number in pool is {}",
                        queuedTasks.size(), executorService.getCompletedTaskCount(),
                        executorService.getActiveCount(), executorService.getTaskCount(), executorService.getPoolSize());
                this.executorService.awaitTermination(this.awaitTerminationSeconds, TimeUnit.SECONDS);
                log.info("QueuedTask number after is {}, completed task count is {}, " +
                                "active thread number is {}, total task number had ever been scheduled is {}, current thread number in pool is {}",
                        queuedTasks.size(), executorService.getCompletedTaskCount(),
                        executorService.getActiveCount(), executorService.getTaskCount(), executorService.getPoolSize());
                log.info("BatchService finish to wait...");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
