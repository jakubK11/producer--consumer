package sk.objectify.interview.repository;

public class User {
    private Long id;
    private String guid;
    private String username;

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
        return "User [guid=" + guid + ", id=" + id + ", username=" + username + "]";
    }

}
