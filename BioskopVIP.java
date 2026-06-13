import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BioskopVIP {
    // Warna Modern Dark Theme
    private final Color COLOR_SEAT_AVAILABLE = new Color(34, 197, 94); 
    private final Color COLOR_SEAT_SELECTED = new Color(59, 130, 246);  
    private final Color COLOR_SEAT_OCCUPIED = new Color(63, 63, 70);     
    private final Color COLOR_ACCENT = new Color(59, 130, 246);          
    private final Color COLOR_DANGER = new Color(239, 68, 68);           
    private final Color COLOR_SUCCESS = new Color(34, 197, 94);          
    private final Color COLOR_HEADER = new Color(18, 18, 18);            
    private final Color COLOR_BACKGROUND = new Color(24, 24, 27);        
    private final Color COLOR_CARD = new Color(32, 32, 35);              
    private final Color COLOR_TEXT_PRIMARY = new Color(244, 244, 245);    
    private final Color COLOR_TEXT_SECONDARY = new Color(161, 161, 170);  
    private final Color COLOR_BORDER = new Color(48, 48, 51);            

    private BioskopLogic logic;

    private JFrame frame;
    private JPanel panelKursi;
    private JButton[][] tombolKursi;
    private javax.swing.table.DefaultTableModel modelTabel;
    private JTable tabelRiwayat;
    private JLabel labelTotal;

    public BioskopVIP() {
        logic = new BioskopLogic();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Sistem Bioskop VIP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel panelUtama = new JPanel(new BorderLayout(0, 0));
        panelUtama.setBackground(COLOR_BACKGROUND);

        // Header Modern
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Load logo
        JLabel labelLogo = new JLabel();
        try {
            ImageIcon iconLogo = new ImageIcon("logo.png");
            if (iconLogo.getImageLoadStatus() == MediaTracker.COMPLETE || iconLogo.getIconWidth() > 0) {
                Image img = iconLogo.getImage();
                int newHeight = 60;
                int newWidth = (int) (img.getWidth(null) * ((double) newHeight / img.getHeight(null)));
                Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                labelLogo.setIcon(new ImageIcon(scaledImg));
                labelLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
            }
        } catch (Exception e) {
            System.err.println("Could not load logo.png: " + e.getMessage());
        }

        JPanel panelTitle = new JPanel(new GridLayout(2, 1, 4, 0));
        panelTitle.setOpaque(false);

        JLabel labelJudul = new JLabel("SISTEM BIOSKOP VIP");
        labelJudul.setFont(new Font("Segoe UI", Font.BOLD, 30));
        labelJudul.setForeground(COLOR_TEXT_PRIMARY);
        labelJudul.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel labelSubJudul = new JLabel("Oleh : Supardi Akhiyat | 230101010026");
        labelSubJudul.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelSubJudul.setForeground(COLOR_TEXT_SECONDARY);
        labelSubJudul.setHorizontalAlignment(SwingConstants.LEFT);

        panelTitle.add(labelJudul);
        panelTitle.add(labelSubJudul);

        panelHeader.add(labelLogo, BorderLayout.WEST);
        panelHeader.add(panelTitle, BorderLayout.CENTER);
        panelUtama.add(panelHeader, BorderLayout.NORTH);

        // Content area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(750);
        splitPane.setBorder(null);
        splitPane.setBackground(COLOR_BACKGROUND);

        // Panel Kursi (Left side)
        JPanel panelKursiWrapper = new JPanel(new BorderLayout());
        panelKursiWrapper.setBackground(COLOR_BACKGROUND);
        panelKursiWrapper.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 15));

        // Judul Kursi dengan Card Style
        JPanel panelJudulKursi = new JPanel(new BorderLayout());
        panelJudulKursi.setBackground(COLOR_CARD);
        panelJudulKursi.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        JLabel labelJudulKursi = new JLabel("PETA KURSI");
        labelJudulKursi.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelJudulKursi.setForeground(COLOR_TEXT_PRIMARY);

        // Legend untuk kursi
        JPanel panelLegend = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelLegend.setOpaque(false);

        addLegendItem(panelLegend, COLOR_SEAT_AVAILABLE, "Tersedia");
        addLegendItem(panelLegend, COLOR_SEAT_SELECTED, "Dipilih");
        addLegendItem(panelLegend, COLOR_SEAT_OCCUPIED, "Terisi");

        panelJudulKursi.add(labelJudulKursi, BorderLayout.WEST);
        panelJudulKursi.add(panelLegend, BorderLayout.EAST);

        panelKursi = new JPanel(new GridLayout(BioskopLogic.BARIS + 1, BioskopLogic.KOLOM + 1, 15, 15));
        panelKursi.setBackground(COLOR_BACKGROUND);
        panelKursi.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        tombolKursi = new JButton[BioskopLogic.BARIS][BioskopLogic.KOLOM];

        // Header kolom
        JLabel labelKosong = new JLabel("");
        labelKosong.setHorizontalAlignment(SwingConstants.CENTER);
        panelKursi.add(labelKosong);

        for (int kol = 0; kol < BioskopLogic.KOLOM; kol++) {
            JLabel labelKolom = new JLabel(String.valueOf(kol + 1));
            labelKolom.setHorizontalAlignment(SwingConstants.CENTER);
            labelKolom.setFont(new Font("Segoe UI", Font.BOLD, 14));
            labelKolom.setForeground(COLOR_TEXT_SECONDARY);
            panelKursi.add(labelKolom);
        }

        // Tombol kursi
        for (int bar = 0; bar < BioskopLogic.BARIS; bar++) {
            JLabel labelBaris = new JLabel(String.valueOf((char) ('A' + bar)));
            labelBaris.setHorizontalAlignment(SwingConstants.CENTER);
            labelBaris.setFont(new Font("Segoe UI", Font.BOLD, 14));
            labelBaris.setForeground(COLOR_TEXT_SECONDARY);
            panelKursi.add(labelBaris);

            for (int kol = 0; kol < BioskopLogic.KOLOM; kol++) {
                tombolKursi[bar][kol] = createSeatButton(logic.getNamaKursi(bar, kol), bar, kol);

                final int barisFinal = bar;
                final int kolomFinal = kol;
                tombolKursi[bar][kol].addActionListener(e -> togglePilihKursi(barisFinal, kolomFinal));

                panelKursi.add(tombolKursi[bar][kol]);
            }
        }

        // Representasi layar bioskop di bagian atas
        JPanel panelLayar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(COLOR_BORDER);
                
                int w = getWidth();
                g2.drawArc(30, 15, w - 60, 50, 40, 100);
                
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                g2.setColor(COLOR_TEXT_SECONDARY);
                FontMetrics fm = g2.getFontMetrics();
                String txt = "L A Y A R   B I O S K O P";
                int x = (w - fm.stringWidth(txt)) / 2;
                g2.drawString(txt, x, 32);
            }
        };
        panelLayar.setPreferredSize(new Dimension(0, 55));
        panelLayar.setOpaque(false);

        JPanel panelSeatArea = new JPanel(new BorderLayout());
        panelSeatArea.setOpaque(false);
        panelSeatArea.add(panelLayar, BorderLayout.NORTH);
        panelSeatArea.add(panelKursi, BorderLayout.CENTER);

        panelKursiWrapper.add(panelJudulKursi, BorderLayout.NORTH);
        panelKursiWrapper.add(panelSeatArea, BorderLayout.CENTER);
        splitPane.setLeftComponent(panelKursiWrapper);

        // Panel Kontrol (Right side)
        JPanel panelKontrol = new JPanel(new BorderLayout(0, 20));
        panelKontrol.setBackground(COLOR_BACKGROUND);
        panelKontrol.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 30));

        // Panel Info Harga
        JPanel panelHarga = createCardPanel();
        panelHarga.setLayout(new BorderLayout(0, 15));

        JLabel labelJudulHarga = new JLabel("Rincian Harga");
        labelJudulHarga.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelJudulHarga.setForeground(COLOR_TEXT_PRIMARY);

        JPanel panelDaftarHarga = new JPanel(new GridLayout(3, 1, 10, 10));
        panelDaftarHarga.setOpaque(false);

        addPriceItem(panelDaftarHarga, "Baris A - B (VIP Premium)", "Rp 100.000");
        addPriceItem(panelDaftarHarga, "Baris C - D (VIP Standard)", "Rp 75.000");
        addPriceItem(panelDaftarHarga, "Baris E (VIP Economy)", "Rp 50.000");

        labelTotal = new JLabel("TOTAL: Rp 0", SwingConstants.RIGHT);
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTotal.setForeground(COLOR_ACCENT);
        labelTotal.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        panelHarga.add(labelJudulHarga, BorderLayout.NORTH);
        panelHarga.add(panelDaftarHarga, BorderLayout.CENTER);
        panelHarga.add(labelTotal, BorderLayout.SOUTH);

        // Panel Log
        JPanel panelLog = createCardPanel();
        panelLog.setLayout(new BorderLayout(0, 12));

        JLabel labelJudulLog = new JLabel("Riwayat Pemesanan");
        labelJudulLog.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelJudulLog.setForeground(COLOR_TEXT_PRIMARY);

        String[] namaKolom = {"Waktu", "Kursi", "Harga"};
        modelTabel = new javax.swing.table.DefaultTableModel(namaKolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelRiwayat = new JTable(modelTabel);
        tabelRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelRiwayat.setBackground(COLOR_BACKGROUND);
        tabelRiwayat.setForeground(COLOR_TEXT_PRIMARY);
        tabelRiwayat.setRowHeight(30);
        tabelRiwayat.setGridColor(COLOR_BORDER);
        tabelRiwayat.setShowVerticalLines(false);
        tabelRiwayat.setFocusable(false);
        tabelRiwayat.setRowSelectionAllowed(false);

        tabelRiwayat.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelRiwayat.getTableHeader().setBackground(COLOR_HEADER);
        tabelRiwayat.getTableHeader().setForeground(COLOR_TEXT_PRIMARY);
        tabelRiwayat.getTableHeader().setReorderingAllowed(false);
        tabelRiwayat.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelRiwayat);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.setBackground(COLOR_BACKGROUND);
        scrollPane.getViewport().setBackground(COLOR_BACKGROUND);

        JPanel panelLogContent = new JPanel(new BorderLayout());
        panelLogContent.setOpaque(false);
        panelLogContent.add(scrollPane, BorderLayout.CENTER);

        panelLog.add(labelJudulLog, BorderLayout.NORTH);
        panelLog.add(panelLogContent, BorderLayout.CENTER);

        // Panel Tombol
        JPanel panelTombol = new JPanel(new GridLayout(1, 2, 15, 0));
        panelTombol.setOpaque(false);
        panelTombol.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JButton btnPesan = createActionButton("PESAN KURSI", COLOR_ACCENT);
        btnPesan.addActionListener(e -> tampilkanDialogPesan());

        JButton btnKeluar = createActionButton("KELUAR", COLOR_DANGER);
        btnKeluar.addActionListener(e -> keluarAplikasi());

        panelTombol.add(btnPesan); 
        panelTombol.add(btnKeluar); 

        panelKontrol.add(panelHarga, BorderLayout.NORTH);
        panelKontrol.add(panelLog, BorderLayout.CENTER);
        panelKontrol.add(panelTombol, BorderLayout.SOUTH);

        splitPane.setRightComponent(panelKontrol);

        panelUtama.add(splitPane, BorderLayout.CENTER);
        frame.add(panelUtama);
        frame.setVisible(true);
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        return panel;
    }

    private void addLegendItem(JPanel parent, Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        item.setOpaque(false);

        JLabel dot = new JLabel();
        dot.setOpaque(true);
        dot.setBackground(color);
        dot.setPreferredSize(new Dimension(14, 14));
        dot.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textLabel.setForeground(COLOR_TEXT_SECONDARY);

        item.add(dot);
        item.add(textLabel);
        parent.add(item);
    }

    private void addPriceItem(JPanel parent, String label, String price) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(false);

        JLabel labelItem = new JLabel(label);
        labelItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelItem.setForeground(COLOR_TEXT_SECONDARY);

        JLabel priceItem = new JLabel(price, SwingConstants.RIGHT);
        priceItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceItem.setForeground(COLOR_TEXT_PRIMARY);

        item.add(labelItem, BorderLayout.WEST);
        item.add(priceItem, BorderLayout.EAST);
        parent.add(item);
    }

    private void togglePilihKursi(int baris, int kolom) {
        if (logic.isKursiTerisi(baris, kolom)) {
            JOptionPane.showMessageDialog(frame,
                    "Kursi " + logic.getNamaKursi(baris, kolom) + " sudah terisi.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        logic.togglePilihKursi(baris, kolom);
        updateTampilanKursi(baris, kolom);
        updatePreviewHarga();
    }

    private void updateTampilanKursi(int baris, int kolom) {
        if (logic.isKursiTerisi(baris, kolom)) {
            tombolKursi[baris][kolom].setText("X");
            tombolKursi[baris][kolom].setBackground(COLOR_SEAT_OCCUPIED);
            tombolKursi[baris][kolom].setForeground(COLOR_TEXT_PRIMARY);
            tombolKursi[baris][kolom].setFont(new Font("Segoe UI", Font.BOLD, 18));
            tombolKursi[baris][kolom].setEnabled(false);
            tombolKursi[baris][kolom].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else if (logic.isKursiDipilih(baris, kolom)) {
            tombolKursi[baris][kolom].setText("X");
            tombolKursi[baris][kolom].setBackground(COLOR_SEAT_SELECTED);
            tombolKursi[baris][kolom].setForeground(COLOR_TEXT_PRIMARY);
            tombolKursi[baris][kolom].setFont(new Font("Segoe UI", Font.BOLD, 18));
        } else {
            tombolKursi[baris][kolom].setText(logic.getNamaKursi(baris, kolom));
            tombolKursi[baris][kolom].setBackground(COLOR_SEAT_AVAILABLE);
            tombolKursi[baris][kolom].setForeground(COLOR_HEADER);
            tombolKursi[baris][kolom].setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        tombolKursi[baris][kolom].setOpaque(true);
        tombolKursi[baris][kolom].setContentAreaFilled(true);
    }

    private void updatePreviewHarga() {
        int totalSementara = logic.getTotalHargaSementara();
        int jumlahDipilih = logic.getJumlahDipilih();

        if (jumlahDipilih > 0) {
            labelTotal.setText("TOTAL: Rp " + String.format("%,d", totalSementara) +
                    " (" + jumlahDipilih + " kursi)");
        } else {
            labelTotal.setText("TOTAL: Rp 0");
        }
    }

    private JButton createSeatButton(String text, int baris, int kolom) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(COLOR_SEAT_AVAILABLE);
        button.setForeground(COLOR_HEADER);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(90, 55));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        final int finalBaris = baris;
        final int finalKolom = kolom;

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled() && !logic.isKursiTerisi(finalBaris, finalKolom) && !logic.isKursiDipilih(finalBaris, finalKolom)) {
                    button.setBackground(new Color(22, 163, 74));
                } else if (button.isEnabled() && !logic.isKursiTerisi(finalBaris, finalKolom)
                        && logic.isKursiDipilih(finalBaris, finalKolom)) {
                    button.setBackground(new Color(37, 99, 235));
                }
            }

            public void mouseExited(MouseEvent e) {
                if (button.isEnabled() && !logic.isKursiTerisi(finalBaris, finalKolom) && !logic.isKursiDipilih(finalBaris, finalKolom)) {
                    button.setBackground(COLOR_SEAT_AVAILABLE);
                } else if (button.isEnabled() && !logic.isKursiTerisi(finalBaris, finalKolom)
                        && logic.isKursiDipilih(finalBaris, finalKolom)) {
                    button.setBackground(COLOR_SEAT_SELECTED);
                }
            }
        });

        return button;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(bgColor);
        button.setForeground(new Color(255, 255, 255));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void tampilkanDialogPesan() {
        int jumlahDipilih = logic.getJumlahDipilih();
        int totalHargaSementara = logic.getTotalHargaSementara();
        StringBuilder daftarKursi = new StringBuilder();

        for (int bar = 0; bar < BioskopLogic.BARIS; bar++) {
            for (int kol = 0; kol < BioskopLogic.KOLOM; kol++) {
                if (logic.isKursiDipilih(bar, kol)) {
                    int harga = logic.getHarga(bar + 1);
                    daftarKursi.append("  • Kursi ").append(logic.getNamaKursi(bar, kol))
                            .append(" | Rp ").append(String.format("%,d", harga)).append("\n");
                }
            }
        }

        if (jumlahDipilih == 0) {
            JOptionPane.showMessageDialog(frame,
                    "Belum ada kursi yang dipilih!\nKlik pada kursi di peta untuk memilih.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel panelKonfirmasi = new JPanel(new BorderLayout(12, 12));
        panelKonfirmasi.setBackground(COLOR_CARD);
        panelKonfirmasi.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel labelJudul = new JLabel("Konfirmasi Pemesanan (" + jumlahDipilih + " kursi)", SwingConstants.CENTER);
        labelJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelJudul.setForeground(COLOR_TEXT_PRIMARY);

        JTextArea textAreaDaftar = new JTextArea(daftarKursi.toString());
        textAreaDaftar.setEditable(false);
        textAreaDaftar.setFont(new Font("Consolas", Font.PLAIN, 13));
        textAreaDaftar.setBackground(COLOR_BACKGROUND);
        textAreaDaftar.setForeground(COLOR_TEXT_PRIMARY);
        textAreaDaftar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel labelTotalKonfirmasi = new JLabel("TOTAL HARGA: Rp " + String.format("%,d", totalHargaSementara),
                SwingConstants.CENTER);
        labelTotalKonfirmasi.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotalKonfirmasi.setForeground(COLOR_SUCCESS);

        panelKonfirmasi.add(labelJudul, BorderLayout.NORTH);
        panelKonfirmasi.add(new JScrollPane(textAreaDaftar), BorderLayout.CENTER);
        panelKonfirmasi.add(labelTotalKonfirmasi, BorderLayout.SOUTH);

        int konfirmasi = JOptionPane.showConfirmDialog(frame, panelKonfirmasi,
                "Konfirmasi Pemesanan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            for (int bar = 0; bar < BioskopLogic.BARIS; bar++) {
                for (int kol = 0; kol < BioskopLogic.KOLOM; kol++) {
                    if (logic.isKursiDipilih(bar, kol)) {
                        String[] data = logic.pesanKursi(bar, kol);
                        if (data != null) {
                            modelTabel.addRow(data);
                        }
                    }
                }
            }

            labelTotal.setText("TOTAL: Rp 0");

            for (int bar = 0; bar < BioskopLogic.BARIS; bar++) {
                for (int kol = 0; kol < BioskopLogic.KOLOM; kol++) {
                    updateTampilanKursi(bar, kol);
                }
            }

            JOptionPane.showMessageDialog(frame,
                    "Berhasil memesan " + jumlahDipilih + " kursi!\nTotal: Rp " +
                            String.format("%,d", totalHargaSementara),
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void keluarAplikasi() {
        int kursiTerisi = logic.hitungKursiTerisi();
        int totalKursi = BioskopLogic.BARIS * BioskopLogic.KOLOM;

        StringBuilder ringkasan = new StringBuilder();
        ringkasan.append("RINGKASAN PEMESANAN\n");
        ringkasan.append("=====================\n\n");
        ringkasan.append("Total Kursi  : ").append(totalKursi).append("\n");
        ringkasan.append("Terisi       : ").append(kursiTerisi).append("\n");
        ringkasan.append("Kosong       : ").append(totalKursi - kursiTerisi).append("\n");
        ringkasan.append("Total Harga  : Rp ").append(String.format("%,d", logic.getTotalHarga())).append("\n\n");

        if (!logic.getDataPemesanan().isEmpty()) {
            ringkasan.append("Detail:\n");
            for (String data : logic.getDataPemesanan()) {
                ringkasan.append("  - ").append(data).append("\n");
            }
        } else {
            ringkasan.append("Tidak ada pemesanan.\n");
        }

        ringkasan.append("\nTerima kasih telah menggunakan sistem ini.");

        JTextArea textArea = new JTextArea(ringkasan.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setBackground(COLOR_BACKGROUND);
        textArea.setForeground(COLOR_TEXT_PRIMARY);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));

        JOptionPane.showMessageDialog(frame,
                scrollPane,
                "Ringkasan",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    public static void main(String[] args) {
        System.out.println("Starting application...");

        try {
            FlatDarkLaf.setup();
            System.out.println("FlatDarkLaf theme loaded successfully!");

            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 15);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("CheckBox.icon.style", "filled");
            UIManager.put("Component.focusWidth", 0);
            UIManager.put("Panel.background", new Color(24, 24, 27));
            UIManager.put("OptionPane.background", new Color(32, 32, 35));
            UIManager.put("OptionPane.messageForeground", new Color(244, 244, 245));

        } catch (Exception e) {
            System.err.println("Failed to load FlatDarkLaf: " + e.getMessage());
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("Using system look and feel.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Look and feel: " + UIManager.getLookAndFeel().getClass().getName());
        SwingUtilities.invokeLater(BioskopVIP::new);
    }
}
