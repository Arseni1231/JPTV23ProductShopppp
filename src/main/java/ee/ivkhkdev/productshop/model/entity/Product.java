package ee.ivkhkdev.productshop.model.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int amount;

    public Product() {}

    public Product(String name, float price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public int getAmount() { return amount; }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // JavaFX Properties
    public LongProperty idProperty() { return new SimpleLongProperty(id); }
    public StringProperty nameProperty() { return new SimpleStringProperty(name); }
    public FloatProperty priceProperty() { return new SimpleFloatProperty(price); }
    public IntegerProperty amountProperty() { return new SimpleIntegerProperty(amount); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
