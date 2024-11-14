package com.bnaeqa.rekeningscraper.ui;

import com.bnaeqa.rekeningscraper.service.ConfigService;
import com.bnaeqa.rekeningscraper.service.ReadWorker;
import com.bnaeqa.rekeningscraper.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainUI {
    private static int logX = 0;
    private static int logY = 0;
    public static JTextArea jtxt;
    public static JButton jbtnStart;

    public static int getLogY() {
        return logY;
    }

    public static int getLogX() {
        return logX;
    }

    public static void appendLog(JTextArea log, String msg) {
        log.append(msg);
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static void main(String[] args) {
        try {
            mainUI();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void mainUI() {
        // Elements
        JFrame frame = new JFrame("REKSCRAP v" + Constants.APP_VERSION);

        frame.setResizable(false);

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Opsi");
        JMenuItem mRefresh = new JMenuItem("Refresh");
        JMenuItem mExit = new JMenuItem("Exit");

        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        String[] bankArr = {"Nama Bank", "BCA"};

        JComboBox<String> namaBank = new JComboBox<>(bankArr);
        JTextField minRangeBank = new JTextField("Min Range");
        JTextField maxRangeBank = new JTextField("Max Range");
        JButton mulai = new JButton("Mulai");
        JTextArea log = new JTextArea();
        JScrollPane logScroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Action Listeners
        namaBank.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Constants.BANK_INDEX = namaBank.getSelectedIndex();
            }
        });

        mulai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((minRangeBank.getText().contains("Range") || minRangeBank.getText().equalsIgnoreCase("")) || (maxRangeBank.getText().contains("Range") || maxRangeBank.getText().equalsIgnoreCase(""))) {
                    appendLog(log, Constants.printLog("Mohon isi Min Range dan Max Range!!"));
                    return;
                }

                String selectedBank = namaBank.getSelectedItem().toString().toLowerCase();
                boolean errorOccured = false;
                Long minRange = Long.valueOf(minRangeBank.getText().replaceAll("[^0-9]",""));
                Long maxRange = Long.valueOf(maxRangeBank.getText().replaceAll("[^0-9]",""));

                if (minRange > maxRange) {
                    appendLog(log, Constants.printLog("Min Range tidak bisa lebih besar daripada Max Range!"));
                    errorOccured = true;
                } else if (minRange == maxRange) {
                    appendLog(log, Constants.printLog("Min Range dan Max Range tidak boleh sama!"));
                    errorOccured = true;
                }

                if (selectedBank.contains("bca")) {
                    if (String.valueOf(minRange).length() != 10) {
                        appendLog(log, Constants.printLog("Min Range Rekening BCA tidak boleh lebih atau kurang dari 10 digit!"));
                        errorOccured = true;
                    }
                    if (String.valueOf(maxRange).length() != 10) {
                        appendLog(log, Constants.printLog("Max Range Rekening BCA tidak boleh lebih atau kurang dari 10 digit!"));
                        errorOccured = true;
                    }
                }

                if (errorOccured) {return;}
                mulai.setText("Berjalan");
                mulai.setEnabled(false);
                ReadWorker running = new ReadWorker(minRange, maxRange, selectedBank);
                Constants.RUNNING_TASK = running;
                running.execute();
            }
        });

        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        minRangeBank.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (minRangeBank.getText().equalsIgnoreCase("Min Range")) {
                    minRangeBank.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!minRangeBank.getText().equalsIgnoreCase("Min Range")) {
                    if (minRangeBank.getText().replace(" ", "").equalsIgnoreCase("")) {
                        minRangeBank.setText("Min Range");
                        return;
                    }
                    Constants.MIN_RANGE = minRangeBank.getText();
                    appendLog(log, Constants.printLog("Min Range berhasil diubah ke " + Constants.MIN_RANGE + "."));
                }
            }
        });

        maxRangeBank.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (maxRangeBank.getText().equalsIgnoreCase("Max Range")) {
                    maxRangeBank.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!maxRangeBank.getText().equalsIgnoreCase("Max Range")) {
                    if (maxRangeBank.getText().replace(" ", "").equalsIgnoreCase("")) {
                        maxRangeBank.setText("Max Range");
                        return;
                    }
                    Constants.MAX_RANGE = maxRangeBank.getText();
                    appendLog(log, Constants.printLog("Max Range berhasil diubah ke " + Constants.MAX_RANGE + "."));
                }
            }
        });

        log.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                log.setCaretPosition(log.getText().length());
            }
        });

        mb.add(m1);
        m1.add(mRefresh);
        m1.add(mExit);

        bottomPanel.add(namaBank);
        bottomPanel.add(Box.createHorizontalStrut(120));
        bottomPanel.add(minRangeBank);
        bottomPanel.add(maxRangeBank);
        bottomPanel.add(Box.createHorizontalStrut(120));
        bottomPanel.add(mulai);

//        centerPanel.add(logScroll);

        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, logScroll);

        // Configurations
        frame.setVisible(true);
        frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimPerangkat = new Dimension(200, namaBank.getHeight());
        Dimension dimReferral = new Dimension(100, 26);
        Dimension dimJScrollLog = log.getSize();

        logScroll.setPreferredSize(dimJScrollLog);
        logScroll.setSize(dimJScrollLog);

        setGlobalSize(minRangeBank, dimReferral);
        setGlobalSize(maxRangeBank, dimReferral);
        setGlobalSize(namaBank, dimPerangkat);

        frame.setLocation((dim.width / 2 - frame.getWidth()), (dim.height / 2 - frame.getHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        log.setEditable(false);

        jtxt = log;
        jbtnStart = mulai;
        logX = log.getX();
        logY = log.getY();

        // Load Config
        if (Files.exists(Path.of(Constants.CONFIG))) {
            ConfigService.load();

            if (Constants.MIN_RANGE != null) {
                minRangeBank.setText(String.valueOf(Constants.MIN_RANGE));
                maxRangeBank.setText(String.valueOf(Constants.MAX_RANGE));
                namaBank.setSelectedItem(namaBank.getItemAt(Constants.BANK_INDEX));
            }
        }
    }

    public static void setGlobalSize(Component component, Dimension dimension) {
        component.setSize(dimension);
        component.setMaximumSize(dimension);
        component.setMinimumSize(dimension);
        component.setPreferredSize(dimension);
    }


//        // Config Load
//        if (Files.exists(cfgPath)) {
//            List<String> findCfg = Files.readAllLines(cfgPath);
//            if ((long) findCfg.size() < 4) {
//                JOptionPane.showMessageDialog(frame, "File Konfigurasi Rusak atau Tidak Sesuai.\n Isi Kembali Konfigurasi Yang Sesuai!");
//
//            }
//
//            deviceConfig.setDeviceName(findCfg.get(0));
//            deviceConfig.setDeviceUDID(findCfg.get(1));
//            deviceConfig.setDeviceVersion(findCfg.get(2));
//            deviceConfig.setMbcaAccess(findCfg.get(3));
//        }


}
