package co.ke.emtechhouse.es.Groups;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityDetails;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupsRepo extends JpaRepository<Groups,Long> {

    List<Groups> findByDeletedFlag(Character deleteFlag);
    Optional<Groups> findByDeletedFlagAndId(Character deleteFlag, Long id);
    List<Groups> findByOutStationId(Long outStationId);
//    List<Groups> findAll();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT g.id as groupsId,g.group_name as groupName,s.id as stationId,s.out_station_name as station,COUNT(mg.group_id) AS members  from out_station s join groups g on g.out_station_id=s.id JOIN member_groups mg on mg.group_id = g.id group by mg.group_id;")
    List<GroupDetails> getAllGroups();

    @Transactional
    @Query(nativeQuery = true,value = "SELECT g.id as groupsId,g.group_name as groupName,s.id as stationId,s.out_station_name as station,COUNT(mg.group_id) AS members  from out_station s join groups g on g.out_station_id=s.id JOIN member_groups mg on mg.group_id = g.id AND s.id = :id group by mg.group_id ")
    List<GroupDetails> getAllByOustationId(Long id);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT g.id as groupsId,g.group_name as groupName,s.id as stationId,s.out_station_name as station  from out_station s join groups g on g.out_station_id=s.id group by g.id;")
    List<GroupDetails> getOutStation();

    @Transactional
    @Query(nativeQuery = true, value = "SELECT m.member_number, m.last_name, m.first_name FROM members m JOIN member_groups mg ON m.id = mg.member_id JOIN groups g ON mg.group_id = g.id WHERE g.id = :groupId")
    List<Object[]> getGroupMembers(Long groupId);




}
