package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

    private static HashMap<Integer, ArrayList<Alarm>> alarms = new HashMap<Integer, ArrayList<Alarm>>(); 
	
	public void onCreate()
    {
        super.onCreate();       
    }
    
    public void createAlarms(int id, String days, int time_h, int time_m, Context c)
	{ 
		Log.d("AlarmService", "creating alarms"); 
    	ArrayList<Alarm> temp; 
    	if(alarms.containsKey(id))
		{
			temp = alarms.get(id); 
		}
		else
			temp = new ArrayList<Alarm>(); 
    	DateTime dt = new DateTime(); 
		int currYear = dt.getYear(); 
		int currMonth = dt.getMonthOfYear();
		int currDate = dt.getDayOfMonth();
		int currDay = dt.getDayOfWeek(); 
		DateTime firstDate; 
		long first; 
		if(days.length() == 7)
		{
			Alarm a = new Alarm(); 
			firstDate = new DateTime(currYear, currMonth, currDate+1, time_h, time_m); 
			first = firstDate.getMillis();  
			temp.add(a);  
			start(a, c, first, DateTimeConstants.MILLIS_PER_DAY); 
		}
		else 
		{
			for(int i=0; i<days.length(); i++)
			{
				Alarm a = new Alarm(); 
				//user is setting it for Monday
				if(days.charAt(i) == '0')
				{
					if(currDay == 1)
						firstDate = dt.withDayOfWeek(DateTimeConstants.MONDAY).withHourOfDay(time_h).withMinuteOfHour(time_m);
					else
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.MONDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				//Tuesday
				else if(days.charAt(i) == '1')
				{
					if(currDay <= DateTimeConstants.TUESDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.TUESDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.TUESDAY).withHourOfDay(time_h).withMinuteOfHour(time_m);
				}
				//Wednesday
				else if(days.charAt(i) == '2')
				{
					if(currDay <= DateTimeConstants.WEDNESDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.WEDNESDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.WEDNESDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				//Thursday
				else if(days.charAt(i) == '3')
				{
					if(currDay <= DateTimeConstants.THURSDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.THURSDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.THURSDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				//Friday
				else if(days.charAt(i) == '4')
				{
					if(currDay <= DateTimeConstants.FRIDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.FRIDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.FRIDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				//Saturday
				else if(days.charAt(i) == '5')
				{
					if(currDay <= DateTimeConstants.SATURDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.SATURDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.SATURDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				//Sunday
				else
				{
					if(currDay <= DateTimeConstants.SUNDAY)
						firstDate = dt.withDayOfWeek(DateTimeConstants.SUNDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
					else 
						firstDate = dt.plusWeeks(1).withDayOfWeek(DateTimeConstants.SUNDAY).withHourOfDay(time_h).withMinuteOfHour(time_m); 
				}
				first = firstDate.getMillis();  
				temp.add(a);  
				start(a, c, first, DateTimeConstants.MILLIS_PER_WEEK);
			}
		}
		alarms.put(id, temp); 
		Log.d("AlarmService", "size of alarms: " + alarms.size()); 
	}
    
    public void start(Alarm a, Context c, long first, long interval)
    {
    	Log.d("AlarmService", "start alarm"); 
    	a.setAlarm(c,first,interval); 
    }
    
    //this is for testing purposes 
    public void create2(Context c)
    {
    	Log.d("AlarmService", "create 2"); 
    	Alarm a = new Alarm(); 
    	start2(a, c); 
    }
    
    //this one was more for testing purposes
    public void start2(Alarm a, Context c)
    {
    	Log.d("AlarmService", "start 2"); 
    	a.setAlarm2(c); 
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void cancel(Context c, int id)
	{
		Log.d("AlarmService", "cancel alarm: " + id); 
		ArrayList<Alarm> r = alarms.get(id); 
		for (Alarm a:r)
		{
			a.cancelAlarm(c); 
		}
		alarms.remove(id); 
		Log.d("AlarmService", "size of alarms: " + alarms.size()); 
	}


}
