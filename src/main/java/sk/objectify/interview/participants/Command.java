package sk.objectify.interview.participants;

public class Command {
    private CommandType type;
    private UserDto user;

    public Command(CommandType type) {
        this.type = type;
    }

    public Command(CommandType type, UserDto user) {
        this.type = type;
        this.user = user;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Command [user=" + user + ", type=" + type + "]";
    }
}
