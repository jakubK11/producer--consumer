package sk.objectify.interview.participants;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.objectify.interview.repository.User;
import sk.objectify.interview.repository.UsersRepository;

public class Consumer extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private BlockingQueue<Command> queue;
    private UsersRepository usersRepository;
    private boolean runForever = true;

    public Consumer(BlockingQueue<Command> queue, UsersRepository usersRepository) {
        this.queue = queue;
        this.usersRepository = usersRepository;
    }

    public Consumer(BlockingQueue<Command> queue, UsersRepository usersRepository, boolean runForever) {
        this.queue = queue;
        this.usersRepository = usersRepository;
        this.runForever = runForever;
    }

    @Override
    public void run() {
        try {
            do {
             
                Command command = queue.take();
                LOGGER.debug("Received comand: {}.", command);

                mapCommandToAction(command);
            } while (runForever);
        } catch (InterruptedException e) {
            LOGGER.info("Consumer thread was interrupted");
        } catch (SQLException e) {
            throw new IllegalStateException("Invalid sql", e);
        }

    }

    private void mapCommandToAction(Command command) throws SQLException {
        switch (command.getType()) {
            case ADD:
                add(command.getUser());
                break;
            case DELETE_ALL:
                usersRepository.deleteAll();
                break;
            case PRINT_ALL:
                printAll();
                break;
            default:
                throw new IllegalStateException("Unknown user command: " + command.getType());
        }
    }

    private void add(UserDto dto) throws SQLException {
        User user = new User();
        user.setId(dto.getId());
        user.setGuid(dto.getGuid());
        user.setUsername(dto.getUsername());
        usersRepository.save(user);
    }

    /*
     * REQUIEREMENT: prints all users into standard output
     */
    private void printAll() throws SQLException {
        System.out.println("Prints all users:");

        for (User user : usersRepository.findAll()) {
            System.out.println("(" + user.getId() + ", " + user.getGuid() + ", " + user.getUsername() + ")");
        }
    }
}
