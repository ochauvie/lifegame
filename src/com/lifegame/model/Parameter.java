package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {
	
	public static final int INITX = 30; // Default number of lines
	public static final int INITY = 30; // Default number of columns
	public static final int INITDensity = 3; // Default for density
	public static final int INITSleep = 500; // Default sleep time (ms)
	public static final int INITPlayer = 1; // Default number for player
	public static final int INITNbVirus = 3; // Default number for virus
	
	public static final int MINX = 1; // Minimum number of lines
	public static final int MINY = 1; // Minimum number of columns
	public static final int MINDensity = 1; // Minimum for density
	public static final int MINSleep = 0; // Minimum for sleep
	public static final int MINPlayer = 1; // Minimum number for player
	public static final int MINNbVirus = 1; // Minimum number for virus
	
	public static final int MAXX = 100; // Maximum number of lines
	public static final int MAXY = 100; // Maximum number of columns
	public static final int MAXDensity = 10; // Maximum for density
	public static final int MAXSleep = 10000; // Maximum for sleep
	public static final int MAXPlayer = 2; // Maximum number for player
	public static final int MAXNbVirus = 10; // Maximum number for virus
	
	public static final int MAXTurn = 1000; // Maximum for density
	
	
	
	private int gridX; // Number of lines
	private int gridY; // Number of columns
	private int gridDensity; // Density of cells
	private int turnSleep; // Time to sleep between two cycle in auto mode
	private Mode mode; // Play mode
	private int nbPlayer; // Number of player
	private int nbVirus; // Number of virus

	public Parameter() {
		this.gridX = INITX;
		this.gridY = INITY;
		this.gridDensity = INITDensity;
		this.turnSleep = INITSleep;
		this.mode = new Mode(Mode.MODE_STEP);
		this.nbPlayer = INITPlayer;
		this.nbVirus = INITNbVirus;
	}
	
	public Parameter(Parcel parcel) {
		this.gridX = parcel.readInt();
		this.gridY = parcel.readInt();
		this.gridDensity = parcel.readInt();
		this.turnSleep = parcel.readInt();
		this.mode = parcel.readParcelable(Mode.class.getClassLoader());
		this.nbPlayer = parcel.readInt();
		this.nbVirus = parcel.readInt();
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
		parcel.writeInt(turnSleep);
		parcel.writeParcelable(mode, flag);
		parcel.writeInt(nbPlayer);
		parcel.writeInt(nbVirus);
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
	 * Getter turnSleep
	 * @return the turnSleep
	 */
	public int getTurnSleep() {
		return turnSleep;
	}

	/**
	 * Setter turnSleep
	 * @param turnSleep the turnSleep to set
	 */
	public void setTurnSleep(int turnSleep) {
		this.turnSleep = turnSleep;
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
	 * Getter nbPlayer
	 * @return the nbPlayer
	 */
	public int getNbPlayer() {
		return nbPlayer;
	}

	/**
	 * Setter nbPlayer
	 * @param nbPlayer the nbPlayer to set
	 */
	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	/**
	 * Getter nbVirus
	 * @return the nbVirus
	 */
	public int getNbVirus() {
		return nbVirus;
	}

	/**
	 * Setter nbVirus
	 * @param nbVirus the nbVirus to set
	 */
	public void setNbVirus(int nbVirus) {
		this.nbVirus = nbVirus;
	}
			
		
	
}
