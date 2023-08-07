package co.ke.emtechhouse.es.Groups;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupsRepo extends JpaRepository<Groups,Long> {
    List<Groups> findByDeletedFlag(Character deleteFlag);
    Optional<Groups> findByDeletedFlagAndId(Character deleteFlag, Long id);
    List<Groups> findByOutStationId(Long outStationId);
//    List<Groups> findAll();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT g.id as groupsId,g.group_name as groupName,s.id as stationId,s.out_station_name as station,COUNT(DISTINCT mg.member_id) AS members  from out_station s join groups g on g.out_station_id=s.id JOIN member_groups mg on mg.id = g.id group by g.id;")
    List<GroupDetails> getAllGroups();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT g.id as groupsId,g.group_name as groupName,s.id as stationId,s.out_station_name as station  from out_station s join groups g on g.out_station_id=s.id group by g.id;")
    List<GroupDetails> getOutStation();

}
