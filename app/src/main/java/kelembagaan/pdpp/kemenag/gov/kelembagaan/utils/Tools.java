package kelembagaan.pdpp.kemenag.gov.kelembagaan.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Amiral on 7/13/17.
 */

public class Tools {

    public static long sisaHariMasaberlaku(String masaBerlaku){
        long days = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = new Date();
            Date date2 = myFormat.parse(masaBerlaku);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.i("lbg", "Days: " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return days;
    }
}
