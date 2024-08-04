package models;

public class UserModel {
    public UserModel(String name, String job) {
        this.name = name;
        this.job = job;
    }

    private final String name;
    private final String job;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
