package com.bnaeqa.rekeningscraper.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConfigDTO implements Serializable {
    Long minRange;
    Long maxRange;
    int bankIndex;
    List<BankAccount> bankAccounts;
}
