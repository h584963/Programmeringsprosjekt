package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double d = 0;
		for(int i = 0; i<gpspoints.length-1;i++) {
			d += GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
		}
		return d;
	}
	public double totalElevation() {

		double elevation = 0;
		for(int i = 1; i<gpspoints.length;i++) {
			if(gpspoints[i].getElevation()>gpspoints[i-1].getElevation()) {
			elevation +=gpspoints[i].getElevation() - gpspoints[i-1].getElevation();
			}
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		
		return gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		double[] speedtab= new double[gpspoints.length-1];
		for(int i = 0; i<speedtab.length;i++) {
			speedtab[i]= GPSUtils.speed(gpspoints[i],gpspoints[i+1]);
		}
		return speedtab;
	}
	public double maxSpeed() {
		
		double maxspeed=GPSUtils.speed(gpspoints[0], gpspoints[1]);
		double temp=0;
		;
		for(int i = 0;i<gpspoints.length-1;i++) {
			temp = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
			if(maxspeed<temp) {
				maxspeed=temp;
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {
		double average = 0;
		double totaltime  = totalTime();
		double totaldistance = totalDistance();
		average = totaldistance/totaltime;
		average = average *3.6;
		return average;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, double secs, double speed) {

		double kcal=0;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double mph = speed * MS;
		if(mph<10) {
			met = 4;
		}else if(mph<12&&mph>=10) {
			met = 6;
		}else if(mph<14&&mph>=12) {
			met = 8;
		}else if(mph<16&&mph>=14) {
			met = 10;
		}else if(mph<20&&mph>=16) {
			met = 12; 
		}else {
			met=16;
		}
		double timer = secs/3600;
		kcal = met * timer * weight;
		
		return kcal;
	}

	public double totalKcal(double weight) {
		double totalkcal = 0;
		
		int met= 0;
		double mph;
		double time;
		
		for(int i =  0; i<gpspoints.length-1;i++) {
			mph = GPSUtils.speed(gpspoints[i],gpspoints[i+1]);
			mph = mph * 3.6 * 0.62;
			if(mph<10) {
				met = 4;
			}else if(mph<12&&mph>=10) {
				met = 6;
			}else if(mph<14&&mph>=12) {
				met = 8;
			}else if(mph<16&&mph>=14) {
				met = 10;
			}else if(mph<20&&mph>=16) {
				met = 12; 
			}else {
				met=16;
			}
			
			time=gpspoints[i+1].getTime()-gpspoints[i].getTime();
			time= time/3600;
			
			totalkcal += met * weight * time;
		}
		
		return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");
		
	}

}
