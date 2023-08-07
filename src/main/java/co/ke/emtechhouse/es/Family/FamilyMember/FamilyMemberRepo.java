package co.ke.emtechhouse.es.Family.FamilyMember;

import co.ke.emtechhouse.es.Community.CommunityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberRepo extends JpaRepository<FamilyMember,Long> {
    List<FamilyMember> findByDeletedFlag(Character deleteFlag);
    Optional<FamilyMember> findByDeletedFlagAndId(Character deleteFlag, Long id);
    List<FamilyMember> findByFamilyIdAndDeletedFlag(Long familyId, Character deletedFlag);
    Boolean existsByFamilyId(Long familyId);
    Optional<FamilyMember> findByIdAndFamilyId( Long familyMemberId, Long familyId);
    FamilyMember findByMemberNumber(String memberNumber);
//    Optional<FamilyMember> findByMemberNumber(String memberNumber);

}
