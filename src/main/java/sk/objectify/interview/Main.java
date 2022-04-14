package sk.objectify.interview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.objectify.interview.participants.Command;
import sk.objectify.interview.participants.CommandType;
import sk.objectify.interview.participants.Consumer;
import sk.objectify.interview.participants.Producer;
import sk.objectify.interview.participants.UserDto;
import sk.objectify.interview.repository.UsersRepository;
import sk.objectify.interview.repository.impl.UsersRepositoryImpl;

class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Connection connection = initH2();

            BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

            new Producer(queue).send(List.of(
                    new Command(CommandType.ADD, new UserDto(1L, "1a", "Robert")),
                    new Command(CommandType.ADD, new UserDto(2L, "2a", "Martin")),
                    new Command(CommandType.PRINT_ALL),
                    new Command(CommandType.DELETE_ALL),
                    new Command(CommandType.PRINT_ALL)));

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            Thread consumerThread = new Consumer(queue, usersRepository);
            consumerThread.start();

            Thread.sleep(5000L);
            consumerThread.interrupt();
        } catch (InterruptedException e) {
            LOGGER.error("Application was interrupted");
        } catch (SQLException e) {
            LOGGER.error("Failed to connect to database", e);
        }
    }

    public static Connection initH2() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:mem:˜/test;INIT=DROP TABLE SUSERS IF EXISTS\\; CREATE TABLE SUSERS (USER_ID long, USER_GUID varchar(255), USER_NAME varchar(255))");
    }
}