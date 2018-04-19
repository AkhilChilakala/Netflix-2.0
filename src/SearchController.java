import javafx.scene.control.TextField;
import java.sql.*;

public class SearchController {
    public TextField word,fiter;

    public void onClickSearch() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "test", "password");
        Statement s = con.createStatement();
        ResultSet result = s.executeQuery("SELECT * FROM MOVIE_DET,MOVIE WHERE MOVIE.ID=MOVIE_DET.ID AND "+fiter.getText()+" LIKE '%"+word.getText()+"'%;");
        StringBuilder results = new StringBuilder();    //stores ALL the results by appending temp
        while(result.next())
        {
            String temp = "";   //stores EACH result's details
            int did = result.getInt("DID");
            int pid = result.getInt("PID");
            ResultSet dir = s.executeQuery("SELECT NAME FROM DIRECTOR WHERE DID="+did);
            ResultSet producer = s.executeQuery("SELECT NAME FROM PRODHOUSE WHERE PID="+pid);
            temp+= result.getString("NAME") + result.getInt("YEAR") + result.getDouble("RATING") +
                    result.getString("GENRE") + result.getString("BOX") + dir.getString("NAME") +
                    producer.getString("NAME");
            results.append(temp).append("\n\n");
        }
    }

    public void onClickGroup() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "test", "password");
        Statement s = con.createStatement();
        String category = fiter.getText();
        if(category.equalsIgnoreCase("director"))
        {
            ResultSet result = s.executeQuery("SELECT DIRECTOR.NAME,MOVIE.NAME FROM ((MOVIE INNER JOIN MOVIE_DET ON MOVIE.ID=MOVIE_DET.ID) INNER JOIN DIRECTOR ON DIRECTOR.DID = MOVIE_DET.DID);");
        }
    }
}