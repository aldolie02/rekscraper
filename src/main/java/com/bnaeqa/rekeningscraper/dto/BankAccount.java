package com.bnaeqa.rekeningscraper.dto;

import com.bnaeqa.rekeningscraper.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount implements Serializable {
    String bank;
    String rekening;
    String keterangan;
    boolean error = false;

    public BankAccount(String bankCode, String rekening, String keterangan) {
        this.bank = Constants.bankCodeParser(bankCode);
        this.rekening = rekening;
        this.keterangan = keterangan;
        this.error = false;
    }
}
