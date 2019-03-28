package balint.kovacs.first_project.model;

public class Category {

    private long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this(name);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
