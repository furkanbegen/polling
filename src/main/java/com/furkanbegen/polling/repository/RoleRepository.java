package com.furkanbegen.polling.repository;

import com.furkanbegen.polling.model.Role;
import com.furkanbegen.polling.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName roleName);
}
