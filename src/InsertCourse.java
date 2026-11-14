import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertCourse {
    public void insert(String courseId, String courseName, String duration, double fees, String instructor) throws Exception {
        Connection con = DBConnection.getConnection(); // DBConnection class ko call kiya[span_3](start_span)[span_3](end_span)
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO courses (course_id, course_name, duration, fees, instructor) VALUES (?, ?, ?, ?, ?)"
        );
        ps.setString(1, courseId);
        ps.setString(2, courseName);
        ps.setString(3, duration);
        ps.setDouble(4, fees);
        ps.setString(5, instructor);

        ps.executeUpdate();
        System.out.println("Course Inserted Successfully");
        con.close();
    }
}