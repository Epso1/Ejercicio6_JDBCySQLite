import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Piloto {
    private Integer driverid;
    private String code;
    private String forename;
    private String surname;
    private LocalDate dob;
    private String nationality;
    private String url;

    public Piloto(Integer driverId, String code, String forename, String surname, String dob, String nationality, String url) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.driverid = driverId;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = LocalDate.parse(dob, formato);
        this.nationality = nationality;
        this.url = url;
    }

    public Piloto() {
    }

    public Integer getDriverid() {
        return driverid;
    }

    public String getCode() {
        return code;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getNationality() {
        return nationality;
    }

    public String getUrl() {
        return url;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "driverid=" + driverid +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", nationality='" + nationality + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
