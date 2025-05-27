package ee.ivkhkdev.productshop.model.repositories;

import ee.ivkhkdev.productshop.model.entity.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchasedProduct, Long> {
    List<PurchasedProduct> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
