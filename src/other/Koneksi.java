package other;
import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Koneksi {
    public Connection con;
    Statement stm;

    public void config() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =
                    DriverManager.getConnection("jdbc:mysql://localhost/mahasiswa",
                            "root", "");
            stm = con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "koneksi gagal "
                    + e.getMessage());
        }
    }

    public HashMap<String, Integer> PopulateAngkatan() {
        HashMap<String, Integer> map = new HashMap<String,
                Integer>();
        Koneksi DBA = new Koneksi();
        DBA.config();
        con = DBA.con;
        ResultSet rs;
        Statement stt;
        try {
            stt = con.createStatement();
            rs = stt.executeQuery("SELECT `id`,`tahun_angkatan` FROM `tb_angkatan`");
            ComboAngkatan cmi;
            while (rs.next()) {
                cmi = new ComboAngkatan(rs.getInt(1),
                        rs.getString(2));
                map.put(cmi.getThnAngkatan(), cmi.getKdId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE
                    , null, ex);
        }
        return map;
    }

    public HashMap<String, String> PopulateProdi() {
        HashMap<String, String> map = new HashMap<String,
                String>();
        Koneksi DBA = new Koneksi();
        DBA.config();
        con = DBA.con;
        ResultSet rs;
        Statement stt;
        try {
            stt = con.createStatement();
            rs = stt.executeQuery("SELECT `kode_prodi`,`nama_prodi` FROM `tb_prodi`");
            ComboProdi cmi;
            while (rs.next()) {
                cmi = new ComboProdi(rs.getString(1),
                        rs.getString(2));
                map.put(cmi.getNmProdi(), cmi.getKdProdi());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE
                    , null, ex);
        }
        return map;
    }
}

