package Models;

/**
 *
 * @author Luis
 */
public class Categories {

    private int id;
    private String name;
    private String created;
    private String update;

    public Categories() {
    }

    public Categories(int id, String name, String created, String update) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
}
