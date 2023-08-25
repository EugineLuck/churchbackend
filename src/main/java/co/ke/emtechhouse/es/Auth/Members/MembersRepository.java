package co.ke.emtechhouse.es.Auth.Members;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.Familydata;
import co.ke.emtechhouse.es.Groups.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByUsername(String username);
    Optional<Members> findByPhoneNumber(String phoneNumber);
    Optional<Members>findByAppId(Long appId);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.id as mid,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,mg.id as groupsId,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname,m.app_id AS appId, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE mr.role_id = 1 group by m.id")
    List<MemberDetails> getAllMembers();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.id as mid,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,mg.id as groupsId,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname,m.app_id AS appId, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE mr.role_id NOT IN (1, 5) group by m.id")
    List<MemberDetails> getAllUsers();
    @Transactional

    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.id as mid,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,mg.id as groupsId,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname,m.app_id AS appId, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE m.member_number =:number group by m.id")
    Optional<MemberDetails> searchByNumber(@Param(value = "number") String number);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE g.id =:groupsId group by m.id")
    List<MemberDetails> searchByGroupsId(@Param(value = "groupsId") Long groupsId);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE m.posted_time between :fromDate and  :toDate  group by m.id")
    List<MemberDetails> searchByDateRange(@Param(value = "fromDate") String fromDate, @Param(value = "toDate") String toDate);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE c.id =:communityId group by m.id")
    List<MemberDetails> searchByCommunityId(@Param(value = "communityId") Long communityId);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT m.posted_time as postedTime,m.logged_in,m.id as id,m.mode_of_registration as mor, mr.role_id as roleId,m.date_of_birth as dob,c.id as communityId,f.id as familyId,o.id as churchId,  m.first_name AS firstname, m.last_name AS lastname, m.member_number AS number, m.phone_number AS phone, m.nationalid AS nation, m.email AS email, f.family_name AS family, c.community_name AS community, o.out_station_name AS church, g.group_name AS groups FROM members m JOIN family f on f.id = m.family_id JOIN community c on c.id= m.community_id JOIN out_station o on o.id = m.out_station_id JOIN members_roles mr on mr.member_id = m.id JOIN member_groups mg on mg.member_id = m.id JOIN groups g ON g.id = mg.group_id WHERE o.id =:outstationId group by m.id")
    List<MemberDetails> searchByOutstationId(@Param(value = "outstationId") Long outstationId);

    Boolean existsByUsername(String username);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM members WHERE member_number =:memberNumber")
    List<Members> searchByMemberNumber(@Param(value = "memberNumber") String memberNumber);
    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phone);

    Optional<Members> findByIdAndDeletedFlag(String memberNumber, Character deletedFlag);
    Optional<Members> findByFamilyId(Long familyId);
    List<Members> findByDeletedFlag(Character deletedFlag);
    List<Members> findByIsAdminAndDeletedFlag(Boolean isAdmin, Character deletedFlag);
    Optional<Members> findByEmail(String email);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT deleted_flag FROM members WHERE member_number = :memberNumber")
    String getDeleteFlag(
            @Param(value = "memberNumber") String memberNumber
    );

    //Get Account locked status
    @Transactional
    @Query(nativeQuery = true,value = "SELECT locked FROM members WHERE member_number = :memberNumber")
    String getAccountLockedStatus(
            @Param(value = "memberNumber") String memberNumber
    );

    //Get Account inactive status
    @Transactional
    @Query(nativeQuery = true,value = "SELECT active FROM members WHERE member_number = :memberNumber")
    String getAccountInactiveStatus(
            @Param(value = "memberNumber") String memberNumber
    );

    @Query(value = "SELECT  * FROM `members` order by id desc limit 1 ", nativeQuery = true)
    Optional<Members> getMemberNumber();
    Optional<Members> findByMemberNumber(String memberNumber);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM members WHERE member_number =:memberNumber")
    List<Members> searchByMemberNumbers(@Param(value = "memberNumber") String memberNumber);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update members set loggedin = :loggedin,modified_on= :modified_on,modified_by = :modified_by where member_number = :memberNumber")
    void updateLogInToTrue(
            @Param(value = "loggedin") String loggedin,
            @Param(value = "modified_on") String modifiedon,
            @Param(value = "modified_by") String modifiedby,
            @Param(value = "memberNumber") String memberNumber
    );
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update members set loggedin = :loggedin,modified_on= :modified_on,modified_by = :modified_by where member_number = :memberNumber")
    void updateLogInToFalse(
            @Param(value = "loggedin") String loggedin,
            @Param(value = "modified_on") String modifiedon,
            @Param(value = "modified_by") String modifiedby
    );

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM members a, members_roles b  WHERE b.member_id = a.id and b.role_id = 1")
    List<Members> allRoleMember();
    //Update member Role
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update members_roles set role_id = :role_id where member_id = :memberId")
    void updateMemberRole(
            @Param(value = "role_id") long role_id,
            @Param(value = "memberId") Long memberId
    );

//    List<Members> findByGroups(Groups group);

    List<Members> findByOutStationId(Long outStation);

    List<Members> findByCommunityId(Long community);

}
