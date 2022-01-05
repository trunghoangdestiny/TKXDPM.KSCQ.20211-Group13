package eco.bike.rental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestMinusDate {
    public static void main(String[] args) throws ParseException {
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String start = "2022-01-05 10:50:25";
        String stop = "2022-01-05 11:49:17";

        Date startDate = simpleDateFormat.parse(start.split(" ")[1]);

        Date stopDate = simpleDateFormat.parse(stop.split(" ")[1]);

        long diff = stopDate.getTime() - startDate.getTime();

        TimeUnit hours = TimeUnit.HOURS;
        TimeUnit minutes = TimeUnit.MINUTES;
        TimeUnit seconds = TimeUnit.SECONDS;

        long usedTime = seconds.convert(diff, TimeUnit.MILLISECONDS);

        System.out.println(usedTime); //seconds
    }
}
