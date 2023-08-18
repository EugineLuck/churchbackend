package co.ke.emtechhouse.es.Family;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family,Long> {
      List<Family> findByOutStationId(Long outStationId);
//    @Transactional
//    @Query(nativeQuery = true,value = "SELECT  f.id as familyId,f.family_number as fn,c.community_name as communities,f.family_name as families,o.out_station_name as church,count(m.family_id) as members from community c, out_station o ,family f, members m where c.out_station_id=o.id and f.community_id=c.id group by f.id")
//    List<Familydata> getAllFamilies();
    @Query(value = "SELECT  * FROM `family` order by id desc limit 1 ", nativeQuery = true)
    Optional<Family> getFamilyNumber();


        @Query(value = "SELECT family.* FROM `family` join members on family.id = members.family_id and members.nationalid =:nationalID order by family.id desc limit 1", nativeQuery = true)
        Optional<Family> getFamilyByNId(@Param("nationalID") String nationalID);

    @Query(value = "SELECT  id FROM `family` order by id desc limit 1 ", nativeQuery = true)
   Long getLastInsertedId();
    List<Family> findByCommunityId(Long communityId);

    Optional<Family> findByMemberNumber(String memberNumber);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT  * from members join family on members.family_id=family.id where family.id =:familyId group by members.id")
    List<Members> findByFamilyId(Long familyId);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT f.id AS familyId,m.member_role as role, f.family_number AS fn, c.community_name AS communities, f.family_name AS families, o.out_station_name AS church, COUNT(m.id) AS members FROM community c, out_station o, family f LEFT JOIN members m ON f.id = m.family_id WHERE c.out_station_id = o.id AND f.community_id = c.id GROUP BY f.id")
    List<Familydata> getAllFamilies();
    @Transactional
    @Query(nativeQuery = true, value = "SELECT f.id AS familyId,m.member_role as role, f.family_number AS fn, c.community_name AS communities,c.id AS communityId, f.family_name AS families, o.out_station_name AS church, COUNT(m.id) AS members FROM community c, out_station o, family f LEFT JOIN members m ON f.id = m.family_id WHERE c.out_station_id = o.id AND f.community_id = c.id  AND  c.id =:communityId GROUP BY f.id")
    List<Familydata> searchByCommunityId(Long communityId);
  @Transactional
    @Query(nativeQuery = true, value = "SELECT f.id AS familyId,m.member_role as role, f.family_number AS fn, c.community_name AS communities,c.id AS communityId, f.family_name AS families, o.out_station_name AS church, COUNT(m.id) AS members FROM community c, out_station o, family f LEFT JOIN members m ON f.id = m.family_id WHERE c.out_station_id = o.id AND f.community_id = c.id  AND  o.id =:churchId GROUP BY f.id")
    List<Familydata> searchByChurchId(Long churchId);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.id as id,m.member_role as role,f.id as familyId, m.first_name AS firstname, m.last_name AS lastname, m.member_number AS number,  f.family_name AS family FROM members m JOIN family f on f.id = m.family_id where f.id =:familyId group by m.id")
    Optional<MemberDetails> searchById( Long familyId);

}
