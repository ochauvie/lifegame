package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cell implements Parcelable {

	public static final int CEL_EMPTY = 0;
	public static final int CEL_IN_LIFE = 1;
	public static final int CEL_DEAD = 2;
	public static final int CEL_NEW = 3;
	
	private int x;
	private int y;
	private int status;

	/**
	 * Constructor
	 * @param status
	 */
	public Cell(int x, int y, int status) {
		super();
		this.x = x;
		this.y = y;
		this.status = status;
	}

	public Cell(Parcel parcel) {
		this.x = parcel.readInt();
		this.x = parcel.readInt();
		this.status = parcel.readInt();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(x);
		parcel.writeInt(y);
		parcel.writeInt(status);
	}
	
}
