package ee.ivkhkdev.productshop.model.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Transient
    private Basket basket = new Basket();

    public enum Role {
        USER, MANAGER, ADMIN
    }

    public AppUser() {}

    public AppUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }
    public Basket getBasket() {
        return basket;
    }
    public void setId(Long id) {

        this.id = id;
    }
    public void setUsername(String username) {

        this.username = username;
    }
    public void setPassword(String password) {

        this.password = password;
    }
    public void setRole(Role role) {

        this.role = role;
    }
    public void setBasket(Basket basket) {

        this.basket = basket;
    }

    public LongProperty idProperty() {
        return new SimpleLongProperty(id);
    }
    public StringProperty usernameProperty() {
        return new SimpleStringProperty(username);
    }
    public ObjectProperty<Role> roleProperty() {
        return new SimpleObjectProperty<>(role);
    }
}