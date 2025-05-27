package ee.ivkhkdev.productshop.services;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.model.entity.PurchasedProduct;
import ee.ivkhkdev.productshop.model.repositories.ProductRepository;
import ee.ivkhkdev.productshop.model.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    public void processPurchase(Product product, int quantity) {
        if (JPTV23ProductShopApplication.currentUser.getRole() != AppUser.Role.USER) {
            throw new IllegalArgumentException("У вас нет прав для покупки товара.");
        }
        if (product.getAmount() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }
        product.setAmount(product.getAmount() - quantity);
        productRepository.save(product);

        PurchasedProduct purchase = new PurchasedProduct(
                "customer", product.getName(), quantity, product.getPrice()
        );
        purchaseRepository.save(purchase);
    }

    public List<PurchasedProduct> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public double calculateProfit(LocalDate startDate, LocalDate endDate) {
        List<PurchasedProduct> purchases = purchaseRepository.findAllByDateBetween(startDate, endDate);
        return purchases.stream().mapToDouble(PurchasedProduct::getTotalPrice).sum();
    }
}