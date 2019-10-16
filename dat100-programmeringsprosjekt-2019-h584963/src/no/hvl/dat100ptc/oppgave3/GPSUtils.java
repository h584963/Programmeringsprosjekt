package no.hvl.dat100ptc.oppgave3;

import static java.lang.String.format;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {
	
	public static double findMax(double[] da) {
		
		double max; 
		
		max = da[0];
		
		for (double i : da) {
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
		
		for (double i : da) {
			if (i < min) {
				min = i;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] latis = new double [gpspoints.length];
		for(int i =  0; i<gpspoints.length; i++) {
			latis[i]=gpspoints[i].getLatitude();
		}
		return latis;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		double[] longs = new double [gpspoints.length];
		for(int i =  0; i<gpspoints.length; i++) {
			longs[i]=gpspoints[i].getLongitude();
		}
		return longs;
	}

	private static int R = 6371000; 
	
	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		
		double d;
		double latitude1, longitude1, latitude2, longitude2;
		latitude1  = gpspoint1.getLatitude();
		latitude2  = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2 = gpspoint2.getLongitude();
		
		double R = 6371000;
		double q1=Math.toRadians(latitude1);
		double q2=Math.toRadians(latitude2);
		double a1=Math.toRadians(latitude2-latitude1);
		double a2=Math.toRadians(longitude2-longitude1);
		double a=((Math.sin(a1/2))*(Math.sin(a1/2))) + Math.cos(q1) * Math.cos(q2)*(Math.sin(a2/2)*Math.sin(a2/2));
		double c=2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R * c;
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double secs = gpspoint2.getTime()-gpspoint1.getTime();
		double speed;
		
		double  meter=distance(gpspoint1,gpspoint2);
		double timer = secs/3600;
		double km=meter/1000;
		speed = km/timer;
		
		return speed;
	}

	public static String formatTime(int secs) {

		String TIMESEP = ":";
		
		int sekunder = secs;
		int timer = sekunder/3600;
		sekunder= sekunder - (timer * 3600);
		int minutter = sekunder /60;
		sekunder= sekunder - (minutter * 60);
		String sekStr="";
		String minStr="";
		String timStr="";
		
		if(timer<10) {
			timStr="0"+timer;
		}else{
			timStr = ""+timer;
		}
		if(minutter<10) {
			minStr="0"+minutter;
		}else{
			minStr = ""+minutter;
		}
		if(sekunder<10) {
			sekStr="0"+sekunder;
		}else{
			sekStr = ""+sekunder;
		}
		
		String timestr = timStr+TIMESEP+minStr+TIMESEP+sekStr;
		  
		String rtrn = format("%10s",timestr);
		return rtrn;
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		
		String str = format("%.2f",d);
		String rtrn = format("%10s",str);
		return rtrn;
	}
}
																																