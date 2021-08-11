package com.tkachuk.library.repository;

import com.tkachuk.library.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
