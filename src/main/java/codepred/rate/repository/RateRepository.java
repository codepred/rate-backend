package codepred.rate.repository;

import codepred.rate.model.RateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<RateModel, Long> {
}
