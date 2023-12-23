package gui;

import other.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class MenuProdi {
    private JPanel pnlProdi;
    private JTextField tkode;
    private JTextField tprodi;
    private JButton savebtn;
    private JButton updatebtn;
    private JButton deletebtn;
    private JButton cancelbtn;
    private JTable tblprodi;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuProdi");
        frame.setContentPane(new MenuProdi().pnlProdi);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void createLayout(){
        JFrame JFrame = new JFrame("MenuProdi");
        JFrame.setContentPane(pnlProdi);
        JFrame.pack();
        JFrame.setLocationRelativeTo(null);
        JFrame.setVisible(true);
    }

    Connection con;
    private DefaultTableModel tabmode;

    protected void tabelprodi() {
        Object[] barisprodi = {"KODE PRODI", "PRODI"};
        tabmode = new DefaultTableModel(null, barisprodi);
        tblprodi.setModel(tabmode);
        String sql = "select * from tb_prodi";
        try {
            java.sql.Statement stat = con.createStatement();
            ResultSet dataprodi = stat.executeQuery(sql);
            while (dataprodi.next()) {
                String a = dataprodi.getString("kode_prodi");
                String b = dataprodi.getString("nama_prodi");
                String[] data = {a, b};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Tampil" + e);
        }
    }

    public void kodeotomatis() {
        try {
            String sql = "select right(kode_prodi,2) as kd_prod from tb_prodi";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.first() == false) {
                tkode.setText("PROD-01");
            } else {
                res.last();
                int no = res.getInt(1) + 1;
                String cno = String.valueOf(no);
                int pjg_cno = cno.length();
                for (int i = 0; i < 2 - pjg_cno; i++) {
                    cno = cno;
                }
                tkode.setText("PROD-0" + cno);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void kosongprodi() {
        tkode.setText("");
        tprodi.setText("");
    }

    public MenuProdi() {
        Koneksi DBA = new Koneksi();
        DBA.config();
        con = DBA.con;
        tabelprodi();
        kodeotomatis();

        savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "insert into tb_prodi values (?,?)";
                try {
                    PreparedStatement stat =
                            con.prepareStatement(sql);
                    stat.setString(1, tkode.getText());
                    stat.setString(2, tprodi.getText());
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null,
                            "Data Berhasil Disimpan");
                    kosongprodi();
                    kodeotomatis();
                    tabelprodi();
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null,
                            "Data Gagal Disimpan " + e);
                }
            }
        });

        updatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kd, prod;
                kd = tkode.getText();
                prod = tprodi.getText();
                try {
                    Statement stt = con.createStatement();
                    stt.executeUpdate("UPDATE tb_prodi SET nama_prodi='" + prod + "' WHERE kode_prodi='" + kd + "'");
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                    kosongprodi();
                    kodeotomatis();
                    tabelprodi();
//
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null,
                            "Data Gagal Diubah" + e);

                }

            }
        });
        deletebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ok =
                        JOptionPane.showConfirmDialog(null, "Apakah anda yakin akan menghapus? ", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (ok == 0) {
                    String sql = "delete from tb_prodi where kode_prodi ='" + tkode.getText() + "'";
                    try {
                        PreparedStatement stat =
                                con.prepareStatement(sql);
                        stat.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                        kosongprodi();
                        kodeotomatis();
                        tabelprodi();
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);
                    }
                }

            }
        });
        cancelbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kosongprodi();
                kodeotomatis();
            }
        });
        tblprodi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int proddi = tblprodi.getSelectedRow();
                String a = tabmode.getValueAt(proddi,
                        0).toString();
                String b = tabmode.getValueAt(proddi,
                        1).toString();
                tkode.setText(a);
                tprodi.setText(b);
            }
        });
    }
}
