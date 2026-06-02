package smarthome;

import java.awt.Image;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import 여진.Main;

public class start extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JLabel lblNewLabel = new JLabel("New label");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    start frame = new start();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(null);
        contentPane.setPreferredSize(new Dimension(600, 420));
        setContentPane(contentPane);

        JButton 타조선택 = new JButton("선택하기");
        타조선택.setBackground(Color.ORANGE);
        타조선택.setBounds(253, 301, 94, 40);
        contentPane.add(타조선택);

        JButton 코끼리선택 = new JButton("선택하기");
        코끼리선택.setBackground(Color.BLUE);
        코끼리선택.setBounds(22, 301, 94, 40);
        contentPane.add(코끼리선택);

        JButton 원숭이선택 = new JButton("선택하기");
        원숭이선택.setBackground(Color.GREEN);
        원숭이선택.setBounds(138, 301, 94, 40);
        contentPane.add(원숭이선택);

        JButton 기린선택 = new JButton("선택하기");
        기린선택.setBackground(new Color(128, 0, 128));
        기린선택.setBounds(370, 301, 94, 40);
        contentPane.add(기린선택);

        JButton 알파카선택 = new JButton("선택하기");
        알파카선택.setBackground(Color.PINK);
        알파카선택.setBounds(485, 301, 94, 40);
        contentPane.add(알파카선택);

        코끼리선택.addActionListener(e -> {
            new Main(createPlayers("코끼리", "원숭이", "타조", "기린", "알파카")).setVisible(true);
            dispose();
        });

        원숭이선택.addActionListener(e -> {
            new Main(createPlayers("원숭이", "코끼리", "타조", "기린", "알파카")).setVisible(true);
            dispose();
        });

        타조선택.addActionListener(e -> {
            new Main(createPlayers("타조", "코끼리", "원숭이", "기린", "알파카")).setVisible(true);
            dispose();
        });

        기린선택.addActionListener(e -> {
            new Main(createPlayers("기린", "코끼리", "원숭이", "타조", "알파카")).setVisible(true);
            dispose();
        });

        알파카선택.addActionListener(e -> {
            new Main(createPlayers("알파카", "코끼리", "원숭이", "타조", "기린")).setVisible(true);
            dispose();
        });

        ImageIcon icon = new ImageIcon("src/채연/동물레이스.png");
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(600, 420, Image.SCALE_SMOOTH);

        lblNewLabel.setIcon(new ImageIcon(changeImg));
        lblNewLabel.setBounds(0, -10, 600, 420);
        contentPane.add(lblNewLabel);

        pack();
        setLocationRelativeTo(null);
    }

    private List<Main.Player> createPlayers(String first, String second, String third, String fourth, String fifth) {
        List<Main.Player> players = new ArrayList<>();
        players.add(createPlayer(first));
        players.add(createPlayer(second));
        players.add(createPlayer(third));
        players.add(createPlayer(fourth));
        players.add(createPlayer(fifth));
        return players;
    }

    private Main.Player createPlayer(String animalName) {
        return new Main.Player(animalName, "src/채연/" + animalName + ".jpg");
    }
}
