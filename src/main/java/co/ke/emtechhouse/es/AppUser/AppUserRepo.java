package co.ke.emtechhouse.es.AppUser;


import co.ke.emtechhouse.es.Auth.Members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {


    boolean existsByUserName(String userName);

    Optional<Object> findByUserName(String userName);
    Optional<AppUser> getByuserName(String userName);


    Collection<Object> searchByUserName(String userName);

//    String getDeleteFlag(String userName);
//    List<AppUser> findByDeletedFlag(Character deletedFlag);
//    String getAccountLockedStatus(String userName);
//
//    String getAccountInactiveStatus(String userName);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT deleted_flag FROM app_user WHERE user_name = :userName")
    String getDeleteFlag(
            @Param(value = "userName") String userName
    );

    //Get Account locked status
    @Transactional
    @Query(nativeQuery = true,value = "SELECT locked FROM app_user WHERE user_name = :userName")
    String getAccountLockedStatus(
            @Param(value = "userName") String userName
    );

    //Get Account inactive status
    @Transactional
    @Query(nativeQuery = true,value = "SELECT active FROM app_user WHERE user_name = :userName")
    String getAccountInactiveStatus(
            @Param(value = "userName") String userName
    );

}
