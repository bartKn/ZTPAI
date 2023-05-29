package pl.bartkn.ztpai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bartkn.ztpai.model.dto.response.split.ISimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.split.ISplitData;
import pl.bartkn.ztpai.model.entity.Split;

import java.util.Collection;

public interface SplitRepository extends JpaRepository<Split, Long> {
    @Query(value = "SELECT u.username, u.id as userId, usm.contribution, s.id as splitId, s.finished\n" +
            "FROM users u\n" +
            "INNER JOIN users_splits_mapping usm ON u.id = usm.users_contributions_key\n" +
            "INNER JOIN split s ON usm.split_id = s.id\n" +
            "WHERE usm.split_id IN (\n" +
            "    SELECT split_id\n" +
            "    FROM users_splits_mapping\n" +
            "    WHERE users_contributions_key = :id)", nativeQuery = true)
    Collection<ISplitData> findByUserId(@Param("id") Long userId);

    @Query(value = "select s.id as splitId, s.finished from users u \n" +
            "inner join users_splits_mapping usm on u.id = usm.users_contributions_key\n" +
            "inner join split s on usm.split_id = s.id\n" +
            "where usm.split_id in (\n" +
            "select split_id\n" +
            "from users_splits_mapping usm2\n" +
            "where usm2.users_contributions_key = :id)\n" +
            "group by s.id, s.finished;", nativeQuery = true)
    Collection<ISimpleSplitData> findDataByUserId(@Param("id") Long userId);
}
