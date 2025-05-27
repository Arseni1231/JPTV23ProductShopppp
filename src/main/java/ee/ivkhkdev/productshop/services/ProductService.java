package ee.ivkhkdev.productshop.services;

import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        if (product.getPrice() > 0 && product.getAmount() > 0) {
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Цена и количество должны быть положительными");
        }
    }

    public void updateProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Продукт не найден");
        }
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Продукт не найден");
        }
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
}