package co.ke.emtechhouse.es.Groups.GroupMemberComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepo extends JpaRepository<GroupMember,Long> {

//    List<GroupMember> findByStatus(String status);
//    Optional<GroupMember> findByStatusAndId(String status, Long id);
//    Optional<GroupMember> findByGroupIdAndStatus(Long groupId, String status);
//    List<GroupMember> findByMemberIddAndStatus(Long memberId, String status);

//
//    @Query( value ="SELECT group_member.* FROM group_member WHERE group_member.group_fk=:groupId", nativeQuery = true)
//    List<GroupMember> findByGroupFk(Long groupId);
//    @Query(value = "SELECT CONCAT(users.firstName, users.lastName) AS groupMemberName, group_member.official_role FROM users JOIN group_member ON group_member.users_Fk=users.id", nativeQuery = true)
//    List<GroupMember> findByUsersFk(Long usersId);
//
//    @Query( value ="SELECT group_member.group_member_name AS groupMemberName, group_member.official_role AS officialRole FROM group_member WHERE group_member.group_fk=:groupId", nativeQuery = true)
//    List<GroupMemberDetails> getGroupMemberDetailsByGroupFk(Long groupId);
//
//    List<GroupMember> findByGroupMemberStatus(GroupMemberStatus groupMemberStatus);








}
