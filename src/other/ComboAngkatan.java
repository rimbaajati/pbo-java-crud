package other;

public class ComboAngkatan {
    private String ThnAngkatan;
    private Integer KdId;

    public ComboAngkatan(Integer kdId, String thnAngkatan) {
        this.KdId = kdId;
        this.ThnAngkatan = thnAngkatan;
    }

    public String getThnAngkatan() {
        return ThnAngkatan;
    }

    public Integer getKdId() {
        return KdId;

    }
}