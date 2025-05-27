package ee.ivkhkdev.productshop.model.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "purchased_products")
public class PurchasedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDate date;

    public PurchasedProduct() {}

    public PurchasedProduct(String customerName, String productName, int quantity, double price) {
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = LocalDate.now();
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public LocalDate getDate() { return date; }

    public double getTotalPrice() {
        return this.quantity * this.price;
    }


    // JavaFX Properties
    public LongProperty idProperty() { return new SimpleLongProperty(id); }
    public StringProperty customerNameProperty() { return new SimpleStringProperty(customerName); }
    public StringProperty productNameProperty() { return new SimpleStringProperty(productName); }
    public IntegerProperty quantityProperty() { return new SimpleIntegerProperty(quantity); }
    public DoubleProperty priceProperty() { return new SimpleDoubleProperty(price); }
    public ObjectProperty<LocalDate> dateProperty() { return new SimpleObjectProperty<>(date); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedProduct that = (PurchasedProduct) o;
        return quantity == that.quantity &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, productName, quantity, price);
    }
}