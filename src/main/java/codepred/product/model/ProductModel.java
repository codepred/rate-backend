package codepred.product.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "PRODUCT")
@Entity
@Data
@NoArgsConstructor
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "category", nullable = false)
    private String category;

    @Lob
    private byte[] file1;

    @Lob
    private byte[] file2;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
}
