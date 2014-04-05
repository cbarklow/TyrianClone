package com.chrisbarklow.tyrianclone.utils;

public class StopWatch {

	private long startTime;
	private long stopTime;
	private boolean running;

	public void start(){
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	public void stop(){
		this.stopTime = System.currentTimeMillis();
		this.running = false;
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
		elapsed = elapsed % 1000;
		return elapsed;
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
		elapsed = elapsed % 60;
		return elapsed;
	}

	//elapsed time in minutes
	public long getElapsedTimeMin(){
		long elapsed;
		if(running){
			elapsed = ((System.currentTimeMillis() - startTime)/ 60000);
		} else {
			elapsed = ((stopTime - startTime) / 60000);
		}
		elapsed = elapsed % 60;
		return elapsed;
	}
}
