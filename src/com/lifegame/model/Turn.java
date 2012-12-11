package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Turn implements Parcelable  {

	// Play step : VIRUS -> LIFE -> UPDATE
	public static final int STEP_VIRUS = 0;
	public static final int STEP_LIFE = 1;
	public static final int STEP_UPDATE = 2;
	
	private int turn; // Number of the turn
	private int step; // Step of current turn
	private int sleep; // Time to sleep in each cycle for auto mode
	
	/**
	 * Constructor
	 * @param sleep: sleep time between step
	 */
	public Turn(int sleep) {
		super();
		this.turn = 1;
		this.step = STEP_VIRUS;
		this.sleep = sleep;
	}
	
	public Turn(Parcel parcel) {
		this.turn = parcel.readInt();
		this.step = parcel.readInt();
		this.sleep = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Turn> CREATOR = new Parcelable.Creator<Turn>()
		{
		    @Override
		    public Turn createFromParcel(Parcel source)
		    { return new Turn(source);}

		    @Override
		    public Turn[] newArray(int size)
		    { return new Turn[size];}
		};
	
	
	/**
	 * Getter turn
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * Setter turn
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	/**
	 * Getter step
	 * @return the step
	 */
	public int getStep() {
		return step;
	}
	/**
	 * Setter step
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}
	
	
	/**
	 * Getter sleep
	 * @return the sleep
	 */
	public int getSleep() {
		return sleep;
	}

	/**
	 * Setter sleep
	 * @param sleep the sleep to set
	 */
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public void endTurn() {
		this.step=Turn.STEP_VIRUS;
		this.turn++;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(turn);
		parcel.writeInt(step);
		parcel.writeInt(sleep);
	}
}
