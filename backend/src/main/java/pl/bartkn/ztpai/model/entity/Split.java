package pl.bartkn.ztpai.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "users_splits_mapping",
        joinColumns = {@JoinColumn(name = "split_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "users")
    @Column(name = "contribution")
    private Map<User, BigDecimal> usersContributions = new HashMap<>();

    private boolean finished;
}
