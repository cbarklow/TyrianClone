package com.chrisbarklow.tyrianclone.utils;

public class StopWatch {

	private long startTime;
	private long stopTime;
	private boolean running;

	public void start(){
		if(startTime == 0)
			this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	public void stop(){
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}
	
	public static String convertToString(long time){
		String min = "" + (time/60000) % 60;
		String second = "" + (time/1000) % 60;
		String milli = "" + time % 1000;
		
		return min + ":" + second + ":" + milli;
	}
	
	public long getTotalElapsedMilli(){
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		}
		else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	//elaspsed time in milliseconds
	public long getElapsedTimeMilli() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		}
		else {
			elapsed = (stopTime - startTime);
		}
		
		return elapsed % 1000;
	}

	//elaspsed time in seconds
	public long getElapsedTimeSecs() {
		long elapsed;
		if (running) {
			elapsed = ((System.currentTimeMillis() - startTime) / 1000);
		}
		else {
			elapsed = ((stopTime - startTime) / 1000);
		}
		
		return elapsed % 60;
	}

	//elapsed time in minutes
	public long getElapsedTimeMin(){
		long elapsed;
		if(running){
			elapsed = ((System.currentTimeMillis() - startTime)/ 60000);
		} else {
			elapsed = ((stopTime - startTime) / 60000);
		}
		
		return elapsed % 60;
	}
}
