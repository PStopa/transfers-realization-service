package com.kodilla.transfers_realization.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "accounts")
public class Accounts {

    @Id
    private Long id;

    @Column(name = "nrb")
    private String nrb;

    @Column(name = "currency")
    private String currency;

    @Column(name = "availableFounds")
    private BigDecimal availableFounds;

}