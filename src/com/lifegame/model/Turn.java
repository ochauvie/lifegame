package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Turn implements Parcelable  {

	public static final int STEP_LIFE = 0;
	public static final int STEP_MAJ = 1;
	
	private int turn; // Number of the turn
	private int step; // Step of current turn
	
	public Turn() {
		super();
		this.turn = 1;
		this.step = STEP_LIFE;
	}
	
	public Turn(Parcel parcel) {
		this.turn = parcel.readInt();
		this.step = parcel.readInt();
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

	public void endTurn() {
		this.step=Turn.STEP_LIFE;
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
	}
}
