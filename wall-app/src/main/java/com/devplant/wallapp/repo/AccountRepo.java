package com.devplant.wallapp.repo;

import com.devplant.wallapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, String> {

}
