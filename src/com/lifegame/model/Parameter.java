package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {
	
	public static final int INITX = 16; // Default number of lines
	public static final int INITY = 16; // Default number of columns
	public static final int INITDensity = 3; // Default for density
	
	public static final int MINX = 1; // Minimum number of lines
	public static final int MINY = 1; // Minimum number of columns
	public static final int MINDensity = 1; // Minimum for density
	public static final int MAXX = 100; // Maximum number of lines
	public static final int MAXY = 100; // Maximum number of columns
	public static final int MAXDensity = 10; // Maximum for density
	
	private int gridX;
	private int gridY;
	private int gridDensity;
	private Mode mode; 

	public Parameter() {
		this.gridX = INITX;
		this.gridY = INITY;
		this.gridDensity = INITDensity;
		this.mode = new Mode(Mode.MODE_STEP);
	}
	
	public Parameter(Parcel parcel) {
		this.gridX = parcel.readInt();
		this.gridY = parcel.readInt();
		this.gridDensity = parcel.readInt();
		this.mode = parcel.readParcelable(Mode.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Parameter> CREATOR = new Parcelable.Creator<Parameter>()
		{
		    @Override
		    public Parameter createFromParcel(Parcel source)
		    { return new Parameter(source);}

		    @Override
		    public Parameter[] newArray(int size)
		    { return new Parameter[size];}
		};
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeInt(gridX);
		parcel.writeInt(gridY);
		parcel.writeInt(gridDensity);
		parcel.writeParcelable(mode, flag);
	}

	/**
	 * Getter gridX
	 * @return the gridX
	 */
	public int getGridX() {
		return gridX;
	}

	/**
	 * Setter gridX
	 * @param gridX the gridX to set
	 */
	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	/**
	 * Getter gridY
	 * @return the gridY
	 */
	public int getGridY() {
		return gridY;
	}

	/**
	 * Setter gridY
	 * @param gridY the gridY to set
	 */
	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	/**
	 * Getter gridDensity
	 * @return the gridDensity
	 */
	public int getGridDensity() {
		return gridDensity;
	}

	/**
	 * Setter gridDensity
	 * @param gridDensity the gridDensity to set
	 */
	public void setGridDensity(int gridDensity) {
		this.gridDensity = gridDensity;
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
			
		
	
}
