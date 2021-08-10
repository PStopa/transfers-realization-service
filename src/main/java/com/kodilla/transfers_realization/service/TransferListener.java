package com.kodilla.transfers_realization.service;

import com.kodilla.commons.TransferMessage;
import com.kodilla.transfers_realization.domain.Accounts;
import com.kodilla.transfers_realization.domain.TransferData;
import com.kodilla.transfers_realization.repository.AccountsRepository;
import com.kodilla.transfers_realization.repository.TransferDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferListener {
    private final TransferDataRepository repository;
    private final ApprovedTransferProducer approvedTransferProducer;
    private final AccountsRepository accountsRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "transfers")
    @Transactional
    public void consume(@Payload TransferMessage transferMessage) throws IOException {
        TransferData data = new TransferData();
        data.setAmount(transferMessage.getTransfer().getAmount());
        data.setRecipientAccount(transferMessage.getTransfer().getRecipientAccount());
        data.setTitle(transferMessage.getTransfer().getTitle());
        data.setSenderAccount(transferMessage.getTransfer().getSenderAccount());
        repository.save(data);
        log.info("Transfer was saved in db: {}", transferMessage);
        approvedTransferProducer.sendApprovedTransfer(transferMessage.getTransfer());
        log.info("Transfer will be procedured");
    }

    @KafkaListener(topics = "approved_transfers")
    @Transactional
    public void consumeTransfer(@Payload TransferMessage transferMessage) throws IOException {
        List<Accounts> accounts = accountsRepository.findAll();
        BigDecimal amount = transferMessage.getTransfer().getAmount();
        Optional<Accounts> senderAccount = accounts.stream()
                .filter(a -> a.getNrb().equals(transferMessage.getTransfer().getSenderAccount())).findFirst();
        Optional<Accounts> recipientAccount = accounts.stream()
                .filter(a -> a.getNrb().equals(transferMessage.getTransfer().getRecipientAccount())).findFirst();
        if (senderAccount.isPresent()) {
            Accounts sender = senderAccount.get();
            sender.setAvailableFounds(sender.getAvailableFounds().subtract(amount));
            accountsRepository.save(sender);
        }
        if (recipientAccount.isPresent()) {
            Accounts recipient = recipientAccount.get();
            recipient.setAvailableFounds(recipient.getAvailableFounds().add(amount));
            accountsRepository.save(recipient);
        }
    }
}