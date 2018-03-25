package com.account;

import org.springframework.data.repository.CrudRepository;

import com.model.User;

public interface AccountRepository extends CrudRepository<User, Long> {
    // We can do basic CRUD operations as they are already defind in CRUDRepository class.
}
