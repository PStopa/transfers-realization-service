package com.kodilla.transfers_realization.repository;

import com.kodilla.transfers_realization.domain.Accounts;
import com.kodilla.transfers_realization.domain.TransferData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransferDataRepository extends CrudRepository<TransferData, Long> {
}