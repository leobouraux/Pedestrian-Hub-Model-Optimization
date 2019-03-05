import java.util.ArrayList;
import java.util.List;

public class Utility {
    List<List<String>> records = new ArrayList<List<String>>();
try (CSVReader csvReader = new CSVReader(new FileReader("book.csv"));) {
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
            records.add(Arrays.asList(values));
        }
    }
}
