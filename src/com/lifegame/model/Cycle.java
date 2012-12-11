package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Cycle implements Parcelable {

	private Turn turn; // Current turn
	private Mode mode; // Play mode
	private Grid grid; // The grid
	
	/**
	 * Constructor
	 * @param mode the play mode
	 * @param grid the grid
	 * @param turn the turn
	 */
	public Cycle(Mode mode, Grid grid, Turn turn) {
		super();
		this.mode = mode;
		this.grid = grid;
		this.turn = turn;
	}
	
	public Cycle(Parcel parcel) {
		this.turn = parcel.readParcelable(Turn.class.getClassLoader());
		this.mode = parcel.readParcelable(Mode.class.getClassLoader());
		this.grid = parcel.readParcelable(Grid.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Cycle> CREATOR = new Parcelable.Creator<Cycle>()
		{
		    @Override
		    public Cycle createFromParcel(Parcel source)
		    { return new Cycle(source);}

		    @Override
		    public Cycle[] newArray(int size)
		    { return new Cycle[size];}
		};
	
	/**
	 * Getter turn
	 * @return the turn
	 */
	public Turn getTurn() {
		return turn;
	}
	/**
	 * Setter turn
	 * @param turn the turn to set
	 */
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	/**
	 * Getter mode
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * Setter mode
	 * @param mode the mode to set
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	/**
	 * Getter grid
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}
	/**
	 * Setter grid
	 * @param grid the grid to set
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeParcelable(turn, flags);
		parcel.writeParcelable(mode, flags);
		parcel.writeParcelable(grid, flags);
	}
	
}
