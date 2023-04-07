package pl.bartkn.ztpai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartkn.ztpai.model.entity.Split;

public interface SplitRepository extends JpaRepository<Split, Long> {
}
