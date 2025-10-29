import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private AuthController controller = new AuthController();

    public LoginFrame() {
        setTitle("Login");
        setSize(320, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        tfUsername = new JTextField();
        pfPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        add(new JLabel("Username/Email:"));
        add(tfUsername);
        add(new JLabel("Password:"));
        add(pfPassword);
        add(new JLabel(""));
        add(btnLogin);

        btnLogin.addActionListener(e -> {
            String key = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword());

            try {
                boolean success = controller.submitLogin(key, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Login berhasil!");
                    new DashboardFrame(key).setVisible(true);
                    dispose();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

class AuthController {
    private AuthService authService = new AuthService();

    public boolean submitLogin(String key, String password) {
        return authService.validateLogin(key, password);
    }
}

class AuthService {
    private UserModel userModel = new UserModel();

    public boolean validateLogin(String key, String password) {
        String storedPassword = userModel.findByEmailOrUsername(key);
        if (storedPassword == null)
            throw new RuntimeException("Akun tidak ditemukan");
        if (!PasswordHasher.verify(password, storedPassword))
            throw new RuntimeException("Password salah");
        return true;
    }
}

class UserModel {
    private static java.util.Map<String, String> users = new java.util.HashMap<>();
    static {
        users.put("User", "123456");
        users.put("admin", "admin123");
    }
    public String findByEmailOrUsername(String key) {
        return users.get(key);
    }
}

class PasswordHasher {
    public static boolean verify(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}
