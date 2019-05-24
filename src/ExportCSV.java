import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ExportCSV {


    public static void exportCSV(ArrayList<Data> data,
                                 String fileNmae){

        // Create a new file
        File file = new File(fileNmae);
        FileOutputStream output = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;

        try
        {
            output = new FileOutputStream(file);
            writer = new OutputStreamWriter(output);
            bw = new BufferedWriter(writer);

            // Write titles of csv file
            bw.append("Time tick,Poor Number,Middle Number,Rich Number,Gini Value\n");

            // Add data into file
            if (!data.isEmpty())
            {
                for (Data record : data)
                {
                    bw.append(record.getData()).append("\n");
                }
            }

            // Must close BufferWriter
            bw.flush();
            bw.close();

            System.out.println("CSV file is generated successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
