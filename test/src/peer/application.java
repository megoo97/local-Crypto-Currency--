package peer;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class application {
	
    public static void main(String[] args) throws IOException, ParseException {
       new Mserver().start();
        new Mclientthread().run();
    
    }
}
