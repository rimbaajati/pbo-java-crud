package gui;

import other.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;



public class MenuAngkatan {
    private JPanel pnlAngkatan;
    private JTextField tid;
    private JTextField tangkatan;
    private JButton savebtn;
    private JButton editbtn;
    private JButton deletebtn;
    private JButton cancelbtn;
    private JTable tblangkatan;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MenuAngkatan");
        frame.setContentPane(new MenuAngkatan().pnlAngkatan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void createLayout(){
        JFrame JFrame = new JFrame("MenuProdi");
        JFrame.setContentPane(pnlAngkatan);
        JFrame.pack();
        JFrame.setLocationRelativeTo(null);
        JFrame.setVisible(true);
    }

    Connection con;
    private DefaultTableModel tabmode;

    protected void tabelangkatan() {
        Object[] barisangkatan = {"NO", "TAHUN"};
        tabmode = new DefaultTableModel(null, barisangkatan);
        tblangkatan.setModel(tabmode);
        String sql = "select * from tb_angkatan";
        try {
            java.sql.Statement stat = con.createStatement();
            ResultSet dataangkatan = stat.executeQuery(sql);
            while (dataangkatan.next()) {
                String a = dataangkatan.getString("id");
                String b =
                        dataangkatan.getString("tahun_angkatan");
                String[] data = {a, b};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Tampil" + e);
        }
    }

    protected void kosongprodi() {
        tangkatan.setText("");
    }
    protected void otomatis() {
        try {
            Statement stt = con.createStatement();
            String sql = "select MAX(id) from tb_angkatan";
            ResultSet rs = stt.executeQuery(sql);
            while (rs.next()) {
                int no = rs.getInt(1) + 1;
                String cno = String.valueOf(no);
                tid.setText(cno);
            }
            rs.close();
            stt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public MenuAngkatan() {
        Koneksi DBA = new Koneksi();
        DBA.config();
        con = DBA.con;
        tabelangkatan();
        otomatis();

    savebtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String sql = "insert into tb_angkatan values (?,?)";
            try {
                PreparedStatement stat = con.prepareStatement(sql);
                    stat.setString(1,tid.getText());
                    stat.setString(2,tangkatan.getText());
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                        kosongprodi();
                        otomatis();
                        tabelangkatan();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Data Gagal Disimpan " + e);


                    }
                }

            });

    editbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String id_angkt,angkt;
            id_angkt =tid.getText();
            angkt =tangkatan.getText();
            try{
                Statement stt= con.createStatement();
                stt.executeUpdate("UPDATE tb_angkatan SET tahun_angkatan='"+angkt+"' WHERE id = '"+ id_angkt+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                kosongprodi();
                otomatis();
                tabelangkatan();
//
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
            }
        }
    });

    deletebtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int ok =
                    JOptionPane.showConfirmDialog(null, "Apakah anda yakin akan menghapus? ", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == 0) {
                String sql = "delete from tb_angkatan where tahun_angkatan ='" + tangkatan.getText() + "'";
                try {
                    PreparedStatement stat =
                            con.prepareStatement(sql);
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    kosongprodi();
                    otomatis();
                    tabelangkatan();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);

                }
            }
        }
    });

    cancelbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            kosongprodi();
            otomatis();
        }
    });

    tblangkatan.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            super.mouseClicked(mouseEvent);
            int angkataan = tblangkatan.getSelectedRow();
            String a = tabmode.getValueAt(angkataan, 0).toString();
            String b = tabmode.getValueAt(angkataan, 1).toString();
            tid.setText(a);
            tangkatan.setText(b);

            }
        });
    }
}
