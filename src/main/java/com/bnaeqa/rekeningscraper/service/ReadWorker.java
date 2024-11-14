package com.bnaeqa.rekeningscraper.service;

import com.bnaeqa.rekeningscraper.dto.BankAccount;
import com.bnaeqa.rekeningscraper.ui.MainUI;
import com.bnaeqa.rekeningscraper.util.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bnaeqa.rekeningscraper.ui.MainUI.appendLog;

public class ReadWorker extends SwingWorker<Void, String> {
    String bankCode="0";
    Long minRange;
    Long maxRange;
    String bankName;

    public ReadWorker(Long minRange, Long maxRange, String bankName) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.bankName = bankName;
    }

    public synchronized BankAccount cekRekening(String rekening, String bankCode) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println("\n [+] Reading Rekening: " + rekening);
        HttpGet postMethod = new HttpGet("https://api-rekening.lfourr.com/getBankAccount?bankCode=" + bankCode + "&accountNumber=" + rekening );
        System.out.println(" - URL: " + postMethod.getURI());
        try {
            HttpResponse response = httpclient.execute(postMethod);
            StatusLine statusLine = response.getStatusLine();
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            if (statusLine.getStatusCode() == 200) {
                JSONObject result = new JSONObject(responseBody);
                System.out.println(" - JSON Response:\n" + result.toString() +"\n");
                String name = (String) result.getJSONObject("data").get("accountname");

                BankAccount resultAcc = new BankAccount(bankCode, rekening, "");
                resultAcc.setKeterangan(name.toUpperCase());


                publish(Constants.printLog(rekening + " : Rekening a/n " + name.toUpperCase()));
//                appendLog(MainUI.jtxt, Constants.printLog(rekening + " : Rekening a/n " + name.toUpperCase()));
                return resultAcc;
            } else {
//                resultAcc.setKeterangan("Error Response " + statusLine.getStatusCode() + "!");
//                resultAcc.setError(true);
                publish(Constants.printLog(rekening + " : " + "Error Response " + statusLine.getStatusCode() + "!"));
//                appendLog(MainUI.jtxt, Constants.printLog(rekening + " : " + "Error Response " + statusLine.getStatusCode() + "!"));
                return null;
            }
        } catch (Exception e) {
//            BankAccount resultAcc = new BankAccount(bankCode, rekening, "Rekening Not Found");
//            resultAcc.setError(true);
            e.printStackTrace();
            publish(Constants.printLog(rekening + " : " + "Rekening Tidak Valid"));
//            appendLog(MainUI.jtxt, Constants.printLog(rekening + " : " + "Rekening Not Found"));
            return null;
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Error Checking
        if (minRange == 0 && maxRange == 0 || bankName.isEmpty()) {
//            appendLog(MainUI.jtxt, Constants.printLog("Unknown Error! Robot berhenti."));
            publish(Constants.printLog("Unknown Error! Robot berhenti."));
            done();
        }

        // Define Bank Code
        if (bankName.contains("bca")) {
            bankCode = "014";
        }

        if (bankCode.equalsIgnoreCase("0")) {
//            appendLog(MainUI.jtxt, Constants.printLog("Error! Bank tidak diketahui."));
            publish(Constants.printLog("Error! Bank tidak diketahui."));
            done();
        }

        // Starts Working
        publish(Constants.printLogLn("Memulai Pembacaan dari " + Constants.bankCodeParser(bankCode) + " dengan range " + minRange + " s/d " + maxRange));
        for (Long readHead = minRange; readHead <= maxRange; readHead++) {
            BankAccount rekening = cekRekening(String.valueOf(readHead), bankCode);
            if (rekening != null) {
                Constants.ACCOUNTS.add(rekening);
            }
        }
        return null;
    }

    @Override
    protected void done() {
        MainUI.jbtnStart.setText("Start");
        MainUI.jbtnStart.setEnabled(true);

        Constants.RUNNING_TASK = null;
        publish(Constants.printLog("Selesai membaca " + (maxRange - minRange)  + " Rekening!"));

        ConfigService.save();
        publish(Constants.printLog("Selesai menyimpan total " + Constants.ACCOUNTS.size() + " Rekening."));

        super.done();
    }

    @Override
    protected void process(List<String> chunks) {
        for (String chunk : chunks) {
            appendLog(MainUI.jtxt, chunk);
        }
    }
}
