import javax.swing.*;
import java.awt.*;

public class MusicAppGUI {

    public static void loadSong(JComboBox<String> genreBox, JLabel statusLabel,
                                JTextArea songTitleLabel, JTextArea artistLabel,
                                JLabel updateLabel) {
        String genre = (String) genreBox.getSelectedItem();
        statusLabel.setText("Status: Finding a song for you...");

        try {
            String[] result = SongGenerator.getSong(genre);
            songTitleLabel.setText("Title: " + result[0]);
            artistLabel.setText("Artist: " + result[1]);

            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

            updateLabel.setText("Last updated: " + now.format(formatter));
            statusLabel.setText("Status: Done");
        } catch (Exception ex) {
            songTitleLabel.setText("Title: Error");
            artistLabel.setText("Artist: Error");
            updateLabel.setText("Last updated: Error");
            statusLabel.setText("Status: Something went wrong");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Music Generator App");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Music Generator App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] genres = {"rap", "rock", "pop", "jazz", "classical"};
        JComboBox<String> genreBox = new JComboBox<>(genres);

        JButton button = new JButton("Generate Song");
        JButton refreshButton = new JButton("New Song");

        JLabel statusLabel = new JLabel("Status: Waiting...");

        JTextArea songTitleLabel = new JTextArea("Title: ");
        songTitleLabel.setLineWrap(true);
        songTitleLabel.setWrapStyleWord(true);
        songTitleLabel.setEditable(false);
        songTitleLabel.setBackground(panel.getBackground());

        JTextArea artistLabel = new JTextArea("Artist: ");
        artistLabel.setLineWrap(true);
        artistLabel.setWrapStyleWord(true);
        artistLabel.setEditable(false);
        artistLabel.setBackground(panel.getBackground());

        JLabel updateLabel = new JLabel("Last updated: ");

        button.addActionListener(e -> {
            loadSong(genreBox, statusLabel, songTitleLabel, artistLabel, updateLabel);
        });

        refreshButton.addActionListener(e -> {
            loadSong(genreBox, statusLabel, songTitleLabel, artistLabel, updateLabel);
        });

        panel.add(titleLabel);
        panel.add(genreBox);
        panel.add(button);
        panel.add(refreshButton);
        panel.add(statusLabel);
        panel.add(songTitleLabel);
        panel.add(artistLabel);
        panel.add(updateLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
}