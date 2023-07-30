package Models;

/**
 *
 * @author Luis
 */
public class Customers {
    private int id;
    private String full_name;
    private String address;
    private String telephone;
    private String email;
    private String created;
    private String update;

    public Customers() {
    }

    public Customers(int id, String full_name, String address, String telephone, String email, String created, String update) {
        this.id = id;
        this.full_name = full_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.created = created;
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
