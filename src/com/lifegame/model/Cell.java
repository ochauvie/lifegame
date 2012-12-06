package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cell implements Parcelable {

	// Cell status
	public static final int CEL_EMPTY = 0;
	public static final int CEL_IN_LIFE = 1;
	public static final int CEL_DEAD = 2;
	public static final int CEL_NEW = 3;
	public static final int CEL_FROZEN = 4;
	
	
	private int x; 			// Cell line
	private int y; 			// Cell column
	private int status; 	// Cell status
	private Virus virus; 	// Cell virus

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param status
	 */
	public Cell(int x, int y, int status, Virus virus) {
		super();
		this.x = x;
		this.y = y;
		this.status = status;
		this.virus = virus;
	}

	public Cell(Parcel parcel) {
		this.x = parcel.readInt();
		this.x = parcel.readInt();
		this.status = parcel.readInt();
		this.virus = parcel.readParcelable(Virus.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>()
		{
		    @Override
		    public Cell createFromParcel(Parcel source)
		    { return new Cell(source);}

		    @Override
		    public Cell[] newArray(int size)
		    { return new Cell[size];}
		};
	
	/**
	 * Getter x
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter x
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter y
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter y
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter status
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter status
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Getter virus
	 * @return the virus
	 */
	public Virus getVirus() {
		return virus;
	}

	/**
	 * Setter virus
	 * @param virus the virus to set
	 */
	public void setVirus(Virus virus) {
		this.virus = virus;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(x);
		parcel.writeInt(y);
		parcel.writeInt(status);
		parcel.writeParcelable(virus, flags);
	}
	
}
