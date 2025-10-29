
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DonasiFrame extends JFrame {
    private JTextField tfNominal;
    private JTextArea taDoa;
    private JComboBox<String> cbCampaign;
    private DonationController controller = new DonationController();
    private String username;

    public DonasiFrame(String username) {
        this.username = username;
        setTitle("Form Donasi - " + username);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblHeader = new JLabel("💜 Form Donasi", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblHeader, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        cbCampaign = new JComboBox<>(new String[]{
            "-- Pilih Campaign --",
            "Donasi Sembako Desa A",
            "Beasiswa Anak Sekolah",
            "Program Kesehatan Gratis"
        });
        tfNominal = new JTextField();
        taDoa = new JTextArea(3, 20);
        taDoa.setLineWrap(true);
        taDoa.setWrapStyleWord(true);

        formPanel.add(new JLabel("Pilih Campaign:"));
        formPanel.add(cbCampaign);
        formPanel.add(new JLabel("Nominal Donasi (Rp):"));
        formPanel.add(tfNominal);
        formPanel.add(new JLabel("Tulis Doa:"));
        formPanel.add(new JScrollPane(taDoa));

        add(formPanel, BorderLayout.CENTER);

        JButton btnKirim = new JButton("Kirim Donasi");
        btnKirim.addActionListener(e -> submitDonasi());
        add(btnKirim, BorderLayout.SOUTH);
    }

    private void submitDonasi() {
        String campaign = (String) cbCampaign.getSelectedItem();
        String nominalText = tfNominal.getText().trim();
        String doa = taDoa.getText().trim();

        if (campaign.equals("-- Pilih Campaign --") || nominalText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field!");
            return;
        }

        try {
            int nominal = Integer.parseInt(nominalText);
            Map<String, Object> data = new HashMap<>();
            data.put("campaign", campaign);
            data.put("nominal", nominal);
            data.put("doa", doa);

            boolean success = controller.submitDonasi(username, data);
            if (success) {
                JOptionPane.showMessageDialog(this, "Donasi berhasil! Terima kasih 💜");
                new DashboardFrame(username).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Donasi gagal! Campaign tidak valid.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal harus berupa angka!");
        }
    }
}

class DonationController {
    private DonationService service = new DonationService();

    public boolean submitDonasi(String username, Map<String, Object> data) {
        return service.validateAndSave(username, data);
    }
}

class DonationService {
    private DonationModel model = new DonationModel();

    public boolean validateAndSave(String username, Map<String, Object> data) {
        String campaign = (String) data.get("campaign");
        if (!model.isValidCampaign(campaign)) return false;
        model.insertDonasi(username, data);
        return true;
    }
}

class DonationModel {
    private java.util.List<String> validCampaigns = Arrays.asList(
        "Donasi Sembako Desa A",
        "Beasiswa Anak Sekolah",
        "Program Kesehatan Gratis"
    );

    public boolean isValidCampaign(String campaign) {
        return validCampaigns.contains(campaign);
    }

    public void insertDonasi(String username, Map<String, Object> data) {
        data.put("username", username);
        data.put("tanggal", new Date());
        System.out.println("✅ Donasi tersimpan: " + data);
    }
}

