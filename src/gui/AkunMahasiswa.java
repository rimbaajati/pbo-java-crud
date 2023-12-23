package gui;

import other.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.HashMap;

public class AkunMahasiswa {
    private JPanel pnlMahasiswa;
    private JLabel tkodeprodi;
    private JLabel tidangkatan;
    private JTextField tnim;
    private JComboBox tstatus;
    private JButton btnsave;
    private JButton btnupdate;
    private JButton btndelete;
    private JButton btncancel;
    private JComboBox CBAngkatan;
    private JComboBox CBProdi;
    private JTextField tnama_lengkap;
    private JTextArea talamat;
    private JTable tblmahasiswa;

    public static void main(String[] args) {
        JFrame frame = new JFrame("AkunMahasiswa");
        frame.setContentPane(new AkunMahasiswa().pnlMahasiswa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void createLayout() {
        JFrame JFrame = new JFrame("Menu Prodi");
        JFrame.setContentPane(pnlMahasiswa);
        JFrame.pack();
        JFrame.setLocationRelativeTo(null);
        JFrame.setVisible(true);
    }

    Connection con;
    private DefaultTableModel tabmode;

    protected void kosongmahasiswa() {
        tnim.setText("");
        tnama_lengkap.setText("");
        CBProdi.setSelectedItem("");
        CBAngkatan.setSelectedItem("");
        talamat.setText("");
        tstatus.setSelectedItem("");
    }

    protected void tabelmahasiswa() {
        Object[] barismahasiswa = {"NIM", "NAMA", "PRODI", "ANGKATAN", "ALAMAT", "STATUS"};
        tabmode = new DefaultTableModel(null,
                barismahasiswa);
        tblmahasiswa.setModel(tabmode);
        String sql = "SELECT nim,nama_lengkap, tb_prodi.nama_prodi, tb_angkatan.tahun_angkatan,alamat,status " + "FROM tb_mahasiswa " + "INNER JOIN tb_prodi ON tb_mahasiswa.prodi = tb_prodi.kode_prodi " + "INNER JOIN tb_angkatan ON tb_mahasiswa.angkatan = tb_angkatan.id";
// String sql = "select * from akun_mahasiswa";
        try {
            java.sql.Statement stat =
                    con.createStatement();
            ResultSet dataprodi = stat.executeQuery(sql);
            while (dataprodi.next()) {
                String a = dataprodi.getString("nim");
                String b =
                        dataprodi.getString("nama_lengkap");
                String c =
                        dataprodi.getString("nama_prodi");
                String d =
                        dataprodi.getString("tahun_angkatan");
                String e = dataprodi.getString("alamat");
                String f = dataprodi.getString("status");
                String[] data = {a, b, c, d, e, f};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Tampil" + e);
        }
    }

    public void ComboAngkatan() {
        Koneksi mq = new Koneksi();
        HashMap<String, Integer> map = mq.PopulateAngkatan();
        for (String s : map.keySet()) {
            CBAngkatan.addItem(s);
        }
    }

    public void ComboProdi() {
        Koneksi mq = new Koneksi();
        HashMap<String, String> map = mq.PopulateProdi();
        for (String s : map.keySet()) {
            CBProdi.addItem(s);
        }
    }

    public AkunMahasiswa() {
        Koneksi DBA = new Koneksi();
        DBA.config();
        con = DBA.con;
        tabelmahasiswa();
        ComboAngkatan();
        ComboProdi();

        btnsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String sql = "insert into tb_mahasiswa(nim, nama_lengkap, prodi, angkatan, alamat, status) values (?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement stat = con.prepareStatement(sql);
                    stat.setString(1, tnim.getText());
                    stat.setString(2, tnama_lengkap.getText());
                    stat.setString(3, tkodeprodi.getText());
                    stat.setInt(4, Integer.parseInt(tidangkatan.getText()));
                    stat.setString(5, talamat.getText());
                    stat.setString(6, tstatus.getSelectedItem().toString());
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    kosongmahasiswa();
                    tabelmahasiswa();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Data Gagal Disimpan " + ex);
                }
            }
        });

        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nimm, nm, kp, idang, talam, stat;
                nimm = tnim.getText();
                nm = tnama_lengkap.getText();
                kp = tkodeprodi.getText();
                idang = tidangkatan.getText();
                talam = talamat.getText();
                stat = tstatus.getSelectedItem().toString();
                try {
                    Statement stt = con.createStatement();
                    stt.executeUpdate("UPDATE tb_mahasiswa SET nama_lengkap='" + nm + "',prodi='" + kp + "',angkatan='" + idang + "'," + "alamat='" + talam + "',status='" + stat + "' WHERE nim='" + nimm + "'");
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                    kosongmahasiswa();
                    tabelmahasiswa();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);

                }
            }
        });
        btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int ok =
                        JOptionPane.showConfirmDialog(null, "Apakah anda yakin akan menghapus? ", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (ok == 0) {
                    String sql = "delete from tb_mahasiswa where nim ='" + tnim.getText() + "'";
                    try {
                        PreparedStatement stat =
                                con.prepareStatement(sql);
                        stat.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                        kosongmahasiswa();
                        tabelmahasiswa();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);
                    }

                }
            }
        });

        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kosongmahasiswa();
            }

        });
        tblmahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int bar = tblmahasiswa.getSelectedRow();
                String a = tabmode.getValueAt(bar, 0).toString();
                String b = tabmode.getValueAt(bar, 1).toString();
                String c = tabmode.getValueAt(bar, 2).toString();
                String d = tabmode.getValueAt(bar, 3).toString();
                String e = tabmode.getValueAt(bar, 4).toString();
                String f = tabmode.getValueAt(bar, 5).toString();
                tnim.setText(a);
                tnama_lengkap.setText(b);
                CBProdi.setSelectedItem(c);
                CBAngkatan.setSelectedItem(d);
                talamat.setText(e);
                tstatus.setSelectedItem(f);
            }
        });
        CBProdi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Koneksi Prod = new Koneksi();
                HashMap<String, String> map =
                        Prod.PopulateProdi();
                tkodeprodi.setText(map.get(CBProdi.getSelectedItem().toString()));
            }
        });

        CBAngkatan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Koneksi angktan = new Koneksi();
                HashMap<String, Integer> map =
                        angktan.PopulateAngkatan();
                tidangkatan.setText(map.get(CBAngkatan.getSelectedItem().toString()).toString());
            }
        });
    }
}