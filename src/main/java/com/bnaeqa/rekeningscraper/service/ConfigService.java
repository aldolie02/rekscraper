package com.bnaeqa.rekeningscraper.service;

import com.bnaeqa.rekeningscraper.dto.ConfigDTO;
import com.bnaeqa.rekeningscraper.util.Constants;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigService {
    public static void save() {
        ObjectOutputStream objectOutputStream = null;
        try {
            ConfigDTO r = new ConfigDTO();
            r.setBankIndex(Constants.BANK_INDEX);
            r.setMinRange(Long.valueOf(Constants.MIN_RANGE.replaceAll("[^0-9]","")));
            r.setMaxRange(Long.valueOf(Constants.MAX_RANGE.replaceAll("[^0-9]","")));
            r.setBankAccounts(new ArrayList<>(Constants.ACCOUNTS));
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(Constants.CONFIG));
            objectOutputStream.writeObject(r);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constants.CONFIG));
            ConfigDTO r = (ConfigDTO) objectInputStream.readObject();
            Constants.MIN_RANGE = String.valueOf(r.getMinRange());
            Constants.MAX_RANGE = String.valueOf(r.getMaxRange());
            Constants.BANK_INDEX = r.getBankIndex();
            Constants.ACCOUNTS = (r.getBankAccounts() == null ? null : new ArrayList<>(r.getBankAccounts()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
