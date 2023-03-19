package telran.monitoring.model;

import java.util.Objects;

public class NotificationData {

    public String doctorEmail;
    public String doctorName;
    public String patientName;

    public NotificationData(String doctorEmail, String doctorName, String patientName) {
        this.doctorEmail = doctorEmail;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }

    public NotificationData() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorEmail, doctorName, patientName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificationData other = (NotificationData) obj;
        return Objects.equals(doctorEmail, other.doctorEmail) && Objects.equals(doctorName, other.doctorName)
                && Objects.equals(patientName, other.patientName);
    }

    @Override
    public String toString() {
        return "NotificationData [doctorEmail=" + doctorEmail + ", doctorName=" + doctorName + ", patientName="
                + patientName + "]";
    }

}
