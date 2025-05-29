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

    // Конструкторы
    public Product() {}

    public Product(String name, float price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    // Сеттеры
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

    // JavaFX свойства
    public LongProperty idProperty() {
        return new SimpleLongProperty(this.id != null ? this.id : 0L);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(this.name);
    }

    public FloatProperty priceProperty() {
        return new SimpleFloatProperty(this.price);
    }

    public IntegerProperty amountProperty() {
        return new SimpleIntegerProperty(this.amount);
    }

    // equals и hashCode — только по id, если он есть
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id); // сравнение по id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
