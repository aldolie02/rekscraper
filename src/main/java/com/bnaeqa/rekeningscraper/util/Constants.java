package com.bnaeqa.rekeningscraper.util;

import com.bnaeqa.rekeningscraper.dto.BankAccount;
import com.bnaeqa.rekeningscraper.dto.DeviceConfigurationDTO;
import com.bnaeqa.rekeningscraper.service.ReadWorker;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    //CONFIG
    public static int BANK_INDEX = 0;
    public static String MIN_RANGE = "";
    public static String MAX_RANGE = "";
    public static List<BankAccount> ACCOUNTS = new ArrayList<>();

    public static String DEFAULT_LOCATION = System.getProperty("user.home");
    public static String CACHE_LOCATION = DEFAULT_LOCATION + File.separator + "BNAEQA_BINARY_STUDIO" + File.separator + "botocrypto_cache";
    public static String APPIUM_LOCATION = DEFAULT_LOCATION + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "npm" +
            File.separator + "node_modules" + File.separator + "appium" + File.separator + "build" + File.separator + "lib" + File.separator + "main.js";
    public static String LOG = CACHE_LOCATION + File.separator + "cd.log";
    public static String CONFIG = CACHE_LOCATION + File.separator + "config.rekscrap";

    public static String APP_VERSION = "1.0 BCA";


    public static HashMap<String,String> DEVICES = new HashMap<>();
    public static ReadWorker RUNNING_TASK = null;

    public static long APPIUM_POLLING = 100;
    public static long APPIUM_TIMEOUT = 100;



//    public static String LAST_PASSWORD = "";

    public static boolean CONFIGURED = false;
    public static boolean THREAD_RUNNING = false;

    public static int CURRENT_ACCOUNT = 0;
    public static int ACCOUNTS_COUNT = 0;
    public static int STEP = 0;
    public static int LOOP = 0;

    public static HashMap<String, String> bankCodeHashMap = new HashMap<>();
    public static HashMap<String, String> getBankCodeHashmap() {
        if (bankCodeHashMap.isEmpty()) {
            bankCodeHashMap.put("014", "BCA");
            bankCodeHashMap.put("008", "MANDIRI");
            bankCodeHashMap.put("009", "BNI");
            bankCodeHashMap.put("002", "BRI");
            bankCodeHashMap.put("426", "MEGA");

            bankCodeHashMap.put("013", "PERMATA");
            bankCodeHashMap.put("011", "DANAMON");
            bankCodeHashMap.put("441", "BUKOPIN");
            bankCodeHashMap.put("451", "BSI");
            bankCodeHashMap.put("028", "OCBC NISP");

            bankCodeHashMap.put("200", "BTN");
            bankCodeHashMap.put("547", "BTPN");
            bankCodeHashMap.put("019", "PANIN");
            bankCodeHashMap.put("016", "MAYBANK");
            bankCodeHashMap.put("022", "CIMB NIAGA");
        }
        return bankCodeHashMap;
    }

    public static String bankCodeParser(String bankCode) {
        HashMap<String, String> bankCodes = Constants.getBankCodeHashmap();
        for (Map.Entry<String, String> s : bankCodes.entrySet()) {
            if (bankCode.equals(s.getKey())) {
                return s.getValue();
            }
        }
        return bankCode;
    }

    public static void showErrorDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Error!",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Information!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningDialog(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(new JFrame(), message, "Warning!",
                JOptionPane.WARNING_MESSAGE);
    }

    public static String printLog(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n");
    }

    public static String printTaskLog(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : Task " + CURRENT_ACCOUNT + "/" + ACCOUNTS_COUNT + " [" + (LOOP == 0 ? 1 : LOOP)  + "] : " + log + "\n");
    }

    public static String printTaskLogLnAf(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : Task " + CURRENT_ACCOUNT + "/" + ACCOUNTS_COUNT + " [" + (LOOP == 0 ? 1 : LOOP) + "] : " + log + "\n\n");
    }

    public static String printLogLn(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return ("\n" + dtf.format(now) + " : " + log + "\n");
    }

    public static String printLogLnAf(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now) + " : " + log + "\n\n");
    }

    public static String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now));
    }
}
