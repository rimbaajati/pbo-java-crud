package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUtama {
    private JPanel pnlRoot;
    private JButton akunmahasiswabtn;
    private JButton prodibtn;
    private JButton angkatanbtn;

    public MenuUtama() {
        akunmahasiswabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AkunMahasiswa Akunmhs = new AkunMahasiswa();
                Akunmhs.createLayout();
            }
        });
        prodibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuProdi Mprodi = new MenuProdi();
                Mprodi.createLayout();
            }
        });
        angkatanbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuAngkatan Mangkatan = new MenuAngkatan();
                Mangkatan.createLayout();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuUtama");
        frame.setContentPane(new MenuUtama().pnlRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
