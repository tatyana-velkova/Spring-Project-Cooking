package bg.softuni.cooking.model.service;

public class RoleServiceModel{
    private Long id;
    private String role;

    public RoleServiceModel() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return role;
    }

    public void setName(String name) {
        this.role = name;
    }
}
