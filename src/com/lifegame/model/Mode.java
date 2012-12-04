package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mode  implements Parcelable {
	public static final String MODE = "MODE";
	public static final String MODE_AUTO = "AUTO";
	public static final String MODE_STEP = "STEP";
	
	private String mode;

	
	public Mode(String mode) {
		super();
		this.mode = mode;
	}
	
	public Mode(Parcel parcel) {
		this.mode = parcel.readString();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mode);
	}
	
	
	
}
