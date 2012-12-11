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
	private int owner; 		// Cell owner
	private Virus virus; 	// Cell virus

	/**
	 * Constructor
	 * @param x: x position in the grid
	 * @param y: y position in the grid
	 * @param status: cell status (empty, in life, dead, new)
	 * @param virus: virus infection
	 * @param owner: player 1 or player 2
	 */
	public Cell(int x, int y, int status, Virus virus, int owner) {
		super();
		this.x = x;
		this.y = y;
		this.status = status;
		this.virus = virus;
		this.owner = owner;
	}

	public Cell(Parcel parcel) {
		this.x = parcel.readInt();
		this.x = parcel.readInt();
		this.status = parcel.readInt();
		this.owner = parcel.readInt();
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

	/**
	 * Getter owner
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * Setter owner
	 * @param owner the owner to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
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
		parcel.writeInt(owner);
		parcel.writeParcelable(virus, flags);
	}
	
}
