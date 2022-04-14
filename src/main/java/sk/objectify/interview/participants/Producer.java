package sk.objectify.interview.participants;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private BlockingQueue<Command> queue;

    public Producer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    public void send(List<Command> commands) {
        try {
            for (Command command : commands) {
                LOGGER.debug("Sending command: {}.", command);
                queue.put(command);
            }

            LOGGER.debug("Producer finished sending");
        } catch (InterruptedException e) {
            LOGGER.error("Producer thread was interrupted", e);
        }
    }
}
