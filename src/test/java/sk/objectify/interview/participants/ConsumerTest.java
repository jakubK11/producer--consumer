package sk.objectify.interview.participants;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import sk.objectify.interview.repository.User;
import sk.objectify.interview.repository.UsersRepository;

public class ConsumerTest {
    BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
    UsersRepository repository = Mockito.mock(UsersRepository.class);

    Consumer consumer = new Consumer(queue, repository, false);

    @Test
    void testAddCommand() throws InterruptedException {
        // GIVEN
        UserDto dto = new UserDto(1L, "1a", "username");
        queue.put(new Command(CommandType.ADD, dto));

        // WHEN
        consumer.run();

        // THEN
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository, only()).save(captor.capture());

        User user = captor.getValue();
        Assertions.assertNotNull(user);

        Assertions.assertEquals(dto.getId(), user.getId());
        Assertions.assertEquals(dto.getGuid(), user.getGuid());
        Assertions.assertEquals(dto.getUsername(), user.getUsername());

        // AND
        Mockito.verify(repository, never()).findAll();
        Mockito.verify(repository, never()).deleteAll();
    }

    @Test
    void testPrintAllCommand() throws InterruptedException {
        // GIVEN
        queue.put(new Command(CommandType.PRINT_ALL));

        // WHEN
        consumer.run();

        // THEN
        Mockito.verify(repository, only()).findAll();

        // AND
        Mockito.verify(repository, never()).save(ArgumentMatchers.any());
        Mockito.verify(repository, never()).deleteAll();
    }

    @Test
    void testDeleteAllCommand() throws InterruptedException {
        // GIVEN
        queue.put(new Command(CommandType.DELETE_ALL));

        // WHEN
        consumer.run();

        // THEN
        Mockito.verify(repository, only()).deleteAll();

        // AND
        Mockito.verify(repository, never()).save(ArgumentMatchers.any());
        Mockito.verify(repository, never()).findAll();
    }
}
