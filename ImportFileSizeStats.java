import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.opencsv.CSVReader;

public class ImportFileSizeStats
{
    public static void main(String[] args)
    {
            readCsv();
            readCsvUsingLoad();
    }

    private static void readCsv()
    {

        try (CSVReader reader = new CSVReader(new FileReader("/tmp/filesizestats.csv"), ','); 
                     Connection connection = DBConnection.getConnection();)
        {
                String insertQuery = "Insert into vmlogsizes (name,logsize,logdate) values (?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                String[] rowData = null;
                int i = 0;
                while((rowData = reader.readNext()) != null)
                {
                    for (String data : rowData)
                    {
                            pstmt.setString((i % 3) + 1, data);

                            if (++i % 3 == 0)
                                    pstmt.addBatch();// add batch

                            if (i % 30 == 0)// insert when the batch size is 10
                                    pstmt.executeBatch();
                    }
                }
                System.out.println("Data Successfully Uploaded");
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }

    }

    private static void readCsvUsingLoad()
    {
        try (Connection connection = DBConnection.getConnection())
        {
            String[] credentials = {"jdbc:mysql://localhost/feedback","sqluser","sqlusermd"};
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(connects[0],connects[1],connects[2]);

                String loadQuery = "LOAD DATA LOCAL INFILE '" + "/tmp/filesizestats.csv" + "' INTO TABLE vmlogsizes FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' (name,logsize,logdate) ";
                System.out.println(loadQuery);
                Statement stmt = connection.createStatement();
                stmt.execute(loadQuery);
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
    }

}

