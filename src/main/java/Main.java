
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Double> travelTimes = Utility.getTravelTimes("straight-corridor-with-sep_tt_5RshSpVK9f.csv");



        System.out.println(Utility.listMean(travelTimes));

    }


}
