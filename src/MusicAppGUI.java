import javax.swing.*;
import java.awt.*;

public class MusicAppGUI {

    public static void loadSongs(JTextField artistInput,
                                 JLabel statusLabel,
                                 JLabel song1, JLabel song2, JLabel song3,
                                 JLabel song4, JLabel song5,
                                 JLabel updateLabel) {

        String artist = artistInput.getText().trim();

        if (artist.isEmpty() || artist.equalsIgnoreCase("Enter artist...")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter an artist name!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        statusLabel.setText("Status: Finding songs...");

        try {
            String[] songs = SongGenerator.getSongsByArtist(artist);

            song1.setText("1. " + songs[0]);
            song2.setText("2. " + songs[1]);
            song3.setText("3. " + songs[2]);
            song4.setText("4. " + songs[3]);
            song5.setText("5. " + songs[4]);

            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

            updateLabel.setText("Last updated: " + now.format(formatter));
            statusLabel.setText("Status: Done");

        } catch (Exception ex) {
            song1.setText("1. Error finding songs");
            song2.setText("2.");
            song3.setText("3.");
            song4.setText("4.");
            song5.setText("5.");
            updateLabel.setText("Last updated: Error");
            statusLabel.setText("Status: Something went wrong");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Song Generator");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Random Song Generator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel artistLabel = new JLabel("Enter Artist:", SwingConstants.CENTER);

        JTextField artistInput = new JTextField();

        JButton generateButton = new JButton("Generate Songs");
        JButton againButton = new JButton("Generate Again");

        generateButton.setBackground(new Color(70, 70, 70));
        generateButton.setForeground(Color.WHITE);

        againButton.setBackground(new Color(70, 70, 70));
        againButton.setForeground(Color.WHITE);

        JLabel picksLabel = new JLabel("Your Song Picks:", SwingConstants.CENTER);
        picksLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel statusLabel = new JLabel("Status: Waiting...");

        JLabel song1 = new JLabel("1. Song Title - Album - Artist");
        JLabel song2 = new JLabel("2. Song Title - Album - Artist");
        JLabel song3 = new JLabel("3. Song Title - Album - Artist");
        JLabel song4 = new JLabel("4. Song Title - Album - Artist");
        JLabel song5 = new JLabel("5. Song Title - Album - Artist");

        JLabel updateLabel = new JLabel("Last updated: ");

        generateButton.addActionListener(e -> {
            loadSongs(artistInput, statusLabel, song1, song2, song3, song4, song5, updateLabel);
        });

        againButton.addActionListener(e -> {
            loadSongs(artistInput, statusLabel, song1, song2, song3, song4, song5, updateLabel);
        });

        panel.add(titleLabel);
        panel.add(artistLabel);
        panel.add(artistInput);
        panel.add(generateButton);
        panel.add(picksLabel);
        panel.add(song1);
        panel.add(song2);
        panel.add(song3);
        panel.add(song4);
        panel.add(song5);
        panel.add(againButton);
        panel.add(statusLabel);
        panel.add(updateLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
}