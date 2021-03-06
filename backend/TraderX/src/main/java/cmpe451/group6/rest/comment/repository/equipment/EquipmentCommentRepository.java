package cmpe451.group6.rest.comment.repository.equipment;

import cmpe451.group6.rest.comment.model.equipment.EquipmentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentCommentRepository extends JpaRepository<EquipmentComment,Integer> {

    List<EquipmentComment> findAll();

    List<EquipmentComment> findTop50ByAuthor_UsernameAndEquipment_Code(String username, String code);

    List<EquipmentComment> findTop50ByEquipment_Code(String code);

    List<EquipmentComment> findTop50ByAuthor_Username(String username);

    EquipmentComment findById(int id);

    int countByAuthor_Username(String username);

}
