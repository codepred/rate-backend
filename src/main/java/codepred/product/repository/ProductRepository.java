package codepred.product.repository;

import codepred.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query("SELECT product FROM ProductModel product WHERE product.isEnabled = true")
    List<ProductModel> findPublicProducts();

    @Query("SELECT product FROM ProductModel product WHERE product.isEnabled = false")
    List<ProductModel> findOwnProducts();
}
