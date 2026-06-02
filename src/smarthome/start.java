package smarthome;

import java.awt.Image;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import animal.기린;
import animal.알파카;
import animal.원숭이;
import animal.코끼리;
import animal.타조;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

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

        코끼리선택.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(createPlayers("코끼리", "원숭이", "타조", "기린", "알파카")).setVisible(true);
                dispose();
            }
        });

        원숭이선택.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(createPlayers("원숭이", "코끼리", "타조", "기린", "알파카")).setVisible(true);
                dispose();
            }
        });

        타조선택.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(createPlayers("타조", "코끼리", "원숭이", "기린", "알파카")).setVisible(true);
                dispose();
            }
        });

        기린선택.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(createPlayers("기린", "코끼리", "원숭이", "타조", "알파카")).setVisible(true);
                dispose();
            }
        });

        알파카선택.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(createPlayers("알파카", "코끼리", "원숭이", "타조", "기린")).setVisible(true);
                dispose();
            }
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

    private List<Object> createPlayers(String first, String second, String third, String fourth, String fifth) {
        List<Object> players = new ArrayList<>();
        players.add(createAnimal(first));
        players.add(createAnimal(second));
        players.add(createAnimal(third));
        players.add(createAnimal(fourth));
        players.add(createAnimal(fifth));
        return players;
    }

    private Object createAnimal(String animalName) {
        if ("코끼리".equals(animalName)) {
            return new 코끼리();
        }
        if ("원숭이".equals(animalName)) {
            return new 원숭이();
        }
        if ("타조".equals(animalName)) {
            return new 타조();
        }
        if ("기린".equals(animalName)) {
            return new 기린();
        }
        if ("알파카".equals(animalName)) {
            return new 알파카();
        }
        return animalName;
    }
}
