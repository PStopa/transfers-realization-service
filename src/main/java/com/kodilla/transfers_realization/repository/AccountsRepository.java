package com.kodilla.transfers_realization.repository;

import com.kodilla.transfers_realization.domain.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    @Override
    List<Accounts> findAll();
}