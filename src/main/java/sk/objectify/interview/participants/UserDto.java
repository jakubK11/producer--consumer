package sk.objectify.interview.participants;

public class UserDto {
    private Long id;
    private String guid;
    private String username;

    public UserDto(Long id, String guid, String username) {
        this.id = id;
        this.guid = guid;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto [guid=" + guid + ", id=" + id + ", username=" + username + "]";
    }
}
