package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.appengine.api.utils.SystemProperty;

@WebServlet(
        name = "HelloGoogleCloudSqlAppBocek",
        urlPatterns = {"/form-page"}
)
public class HelloAppEngine extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<h2>Hello App With Google SQL DB</h2>");

        DataSource pool = null;
        Connection conn = null;
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) { // when running locally, environment value is always Development, on Cloud is olways production
            out.println("Production version");
            pool = CloudConnection.getPool();
            try {
                conn = pool.getConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                out.println("Development version");
                conn = LocalConnection.getDevConnection();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Saving part
                if (request.getMethod().equals("POST")) {
                    String breed = request.getParameter("breed");
                    String strWeight = request.getParameter("weight");
                    String age = request.getParameter("age");
                    String name = request.getParameter("name");


                    stmt.executeUpdate(String.format("insert into animal(breed, weight, age, name) values ('%s', '%s', '%s', '%s')", breed, strWeight, age, name));
                }

                ResultSet RS = stmt.executeQuery("select * from animal");

                out.println("<p>" +
                        "<a href='/'>" +
                        "Add new animal" +
                        "</a>" +
                        "</p>");

                out.println("<table>");

                while (RS.next()) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(RS.getString("breed"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(RS.getString("weight"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(RS.getString("age"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(RS.getString("name"));
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                conn.close();
            } else {
                out.println("No connection!!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("layout.html");
        rd.include(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}