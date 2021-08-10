package com.kodilla.transfers_realization.service;

import com.kodilla.commons.Transfer;
import com.kodilla.commons.TransferMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovedTransferProducer {

    private static final String APPROVED_TRANSFERS_TOPIC = "approved_transfers";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendApprovedTransfer(final Transfer transfer) {
        TransferMessage transferMessage = new TransferMessage(transfer);
        log.info("Sending message to Kafka, transferMessage: {}", transferMessage);
        kafkaTemplate.send(APPROVED_TRANSFERS_TOPIC, transferMessage);
        log.info("Message was sent");
    }

}
