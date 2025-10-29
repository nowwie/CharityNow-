import java.awt.*;
import java.io.File;
import javax.swing.*;

public class ProfileFrame extends JFrame {
    private JTextField tfNama, tfEmail;
    private JLabel lblFoto;
    private JButton btnEdit, btnSimpan, btnUpload;
    private String username;
    private boolean isEditing = false;
    private ImageIcon fotoProfile;

    public ProfileFrame(String username) {
        this.username = username;
        setTitle("Profil Pengguna");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("👤 Kelola Profil", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        String nama = username.equalsIgnoreCase("admin") ? "Administrator" : "User Biasa";
        String email = username.equalsIgnoreCase("admin") ? "admin@example.com" : "user@example.com";
        fotoProfile = new ImageIcon(new ImageIcon("default.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        JPanel content = new JPanel(new GridLayout(5, 1, 8, 8));
        lblFoto = new JLabel(fotoProfile);
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(lblFoto);

        tfNama = new JTextField(nama);
        tfNama.setEditable(false);
        content.add(new JLabel("Nama:"));
        content.add(tfNama);

        tfEmail = new JTextField(email);
        tfEmail.setEditable(false);
        content.add(new JLabel("Email:"));
        content.add(tfEmail);

        add(content, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnEdit = new JButton("✏️ Edit Profil");
        btnSimpan = new JButton("💾 Simpan");
        btnUpload = new JButton("📸 Upload Foto");
        JButton btnBack = new JButton("⬅️ Kembali");

        btnSimpan.setEnabled(false);
        btnUpload.setEnabled(false);

        btnPanel.add(btnEdit);
        btnPanel.add(btnUpload);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        btnEdit.addActionListener(e -> {
            isEditing = true;
            tfNama.setEditable(true);
            tfEmail.setEditable(true);
            btnSimpan.setEnabled(true);
            btnUpload.setEnabled(true);
        });

        btnUpload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon newPhoto = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                        .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblFoto.setIcon(newPhoto);
                JOptionPane.showMessageDialog(this, "Foto berhasil diupload!");
            }
        });

        btnSimpan.addActionListener(e -> {
            String newNama = tfNama.getText();
            String newEmail = tfEmail.getText();
            isEditing = false;
            tfNama.setEditable(false);
            tfEmail.setEditable(false);
            btnSimpan.setEnabled(false);
            btnUpload.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Profil berhasil disimpan!\nNama: " + newNama + "\nEmail: " + newEmail);
        });

        btnBack.addActionListener(e -> {
            new DashboardFrame(username).setVisible(true);
            dispose();
        });
    }
}
