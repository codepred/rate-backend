package codepred.rate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "RATE")
@Entity
@Data
@NoArgsConstructor
public class RateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "is_like", nullable = false)
    private boolean isLike;

}