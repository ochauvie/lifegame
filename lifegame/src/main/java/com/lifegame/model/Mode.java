package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mode  implements Parcelable {
	public static final String MODE = "MODE";
	public static final String MODE_AUTO = "AUTO";
	public static final String MODE_STEP = "STEP";
	
	private String mode; // Automatic or step by step
	private int nbPlayer; // Number of player

	
	public Mode(String mode, int nbPlayer) {
		super();
		this.mode = mode;
		this.nbPlayer = nbPlayer;
	}
	
	public Mode(Parcel parcel) {
		this.mode = parcel.readString();
		this.nbPlayer = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Mode> CREATOR = new Parcelable.Creator<Mode>()
		{
		    @Override
		    public Mode createFromParcel(Parcel source)
		    { return new Mode(source);}

		    @Override
		    public Mode[] newArray(int size)
		    { return new Mode[size];}
		};


	/**
	 * Getter mode
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Setter mode
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mode);
		parcel.writeInt(nbPlayer);
	}
	
	
	
}
