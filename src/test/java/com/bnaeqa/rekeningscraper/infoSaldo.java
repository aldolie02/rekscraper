package com.bnaeqa.rekeningscraper;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class infoSaldo {

    public static void main(String[] args) {
        try {
            openCalculator();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openCalculator() throws MalformedURLException {
        AndroidDriver driver = getAndroidDriver();
        System.out.println("Application Started!");

        //CONFIGS
        String cfgKodeAkses = "zion02";

        ///LOGIN PAGE
        //TEKAN TOMBOL M-BCA
        WebElement loginpage_mBCA_btn = driver.findElement(By.id("com.bca:id/main_btn_bca"));
        loginpage_mBCA_btn.click();

        //ISI KODE AKSES
        WebElement loginpage_kodeAkses_txt = driver.findElement(By.id("com.bca:id/login_edit_text"));
        loginpage_kodeAkses_txt.click();
        loginpage_kodeAkses_txt.sendKeys(cfgKodeAkses);

        //KLIK LOGIN
        WebElement loginpage_login_btn = driver.findElement(By.id("com.bca:id/login_ok_button"));
        loginpage_login_btn.click();

        ///HOME PAGE
        //M-INFO
        //WebElement x = driver.findElement(By.id(""));
        WebElement homepage_mInfo_btn = driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.bca:id/main_recycler_view_layout\"]/android.view.ViewGroup[1]"));
        homepage_mInfo_btn.click();

        //INFO SALDO
        WebElement infopage_infoSaldo_btn = driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.bca:id/info_menu_recycler_view\"]/android.widget.LinearLayout[1]"));
        infopage_infoSaldo_btn.click();

        //OK
        WebElement infopage_ok_btn = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"PopUp Button - OK\"]"));
        infopage_ok_btn.click();
    }

    private static AndroidDriver getAndroidDriver() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("deviceName","Pixel 6");
        cap.setCapability("UDID","23221FDF6003J8");
        cap.setCapability("platformName","Android");
        cap.setCapability("platformVersion","14");
        cap.setCapability("automationName", "UiAutomator2");

        cap.setCapability("appPackage","com.bca");
        cap.setCapability("appActivity","com.bca.mobile.MainActivity");
        cap.setCapability("skipDeviceInitialization",true);
        cap.setCapability("skipServerInstallation",true);
        cap.setCapability("noReset",true);

        URL url = new URL("http://127.0.0.1:4723");

        return new AndroidDriver(url, cap);
    }
}
