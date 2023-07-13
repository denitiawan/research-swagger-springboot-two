package com.deni.app.module.user;


import com.deni.app.module.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("Select u from User u where u.id = ?1 AND u.username = ?2")
    User findByIdAndUsername(Long id, String username);

    @Query("Select u from User u where u.username like %:usernameLike%")
    List<User> searchListLikeUsername(String usernameLike);

    @Query("Select u from User u where u.username like %:username% and u.roles like %:role%  and u.active = :active  and u.blocked =:blocked")
    List<User> searchListByFilter(String username, String role, int active, int blocked);


}
