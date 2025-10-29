import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DashboardFrame extends JFrame {
    private JComboBox<String> cbKategori;
    private JTextArea taResult;
    private String username;

    public DashboardFrame(String username) {
        this.username = username;

        setTitle("Dashboard - Daftar Campaign");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Navbar
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblUser = new JLabel("👤 " + username);
        JButton btnLogout = new JButton("Logout");
        navbar.add(lblUser);
        navbar.add(btnLogout);
        add(navbar, BorderLayout.NORTH);

        // Pilih kategori
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Pilih Kategori Donasi:"));
        cbKategori = new JComboBox<>(new String[]{
            "-- Pilih Kategori --",
            "1 - Pangan",
            "2 - Kesehatan",
            "3 - Pendidikan",
            "4 - Ekonomi"
        });
        topPanel.add(cbKategori);
        add(topPanel, BorderLayout.CENTER);

        // Daftar hasil campaign
        taResult = new JTextArea();
        taResult.setEditable(false);

        JButton btnDonasi = new JButton("💜 Donasi Sekarang");
        btnDonasi.addActionListener(e -> {
            new DonasiFrame(username).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(new JScrollPane(taResult), BorderLayout.CENTER);
        bottomPanel.add(btnDonasi, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        cbKategori.addActionListener(e -> triggerFilter((String) cbKategori.getSelectedItem()));
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void triggerFilter(String kategori) {
        if (kategori == null || kategori.equals("-- Pilih Kategori --")) {
            taResult.setText("");
            return;
        }

        taResult.setText("Daftar Campaign kategori " + kategori + ":\n");
        switch (kategori) {
            case "1 - Pangan":
                taResult.append("- Donasi Sembako Desa A\n- Dapur Umum Mingguan\n");
                break;
            case "2 - Kesehatan":
                taResult.append("- Donasi Obat Gratis\n- Pemeriksaan Kesehatan\n");
                break;
            case "3 - Pendidikan":
                taResult.append("- Beasiswa Anak Desa\n- Kelas Literasi Anak\n");
                break;
            case "4 - Ekonomi":
                taResult.append("- Pelatihan UMKM Lokal\n- Program Pemberdayaan\n");
                break;
        }
    }
}
