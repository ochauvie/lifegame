package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Virus implements Parcelable {

	public static final String VIRUS_ID_A = "A";
	public static final String VIRUS_ID_B = "B";
	public static final String VIRUS_ID_C = "C";
	public static final String VIRUS_ID_D = "D";
	public static final String VIRUS_ID_E = "E";
	
	private String id;		// Virus id
	private String name; 	// Virus name
	private int range; 		// Range of virus effect (0 only this cell, 1 for fist ring neighbors, 2 for the second ring, ...) 
	private int duration; 	// Turn duration
	private int effect; 	// Infected cell status 
	
	/**
	 * Constructor
	 * @param name
	 * @param range
	 * @param duration
	 * @param effect
	 */
	public Virus(String id, String name, int range, int duration, int effect) {
		super();
		this.id = id;
		this.name = name;
		this.range = range;
		this.duration = duration;
		this.effect = effect;
	}
	
	
	public Virus(Parcel parcel) {
		this.id = parcel.readString();
		this.name = parcel.readString();
		this.range = parcel.readInt();
		this.duration = parcel.readInt();
		this.effect = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Virus> CREATOR = new Parcelable.Creator<Virus>()
		{
		    @Override
		    public Virus createFromParcel(Parcel source)
		    { return new Virus(source);}

		    @Override
		    public Virus[] newArray(int size)
		    { return new Virus[size];}
		};
		
	/**
	 * Getter id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter id
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Getter name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter range
	 * @return the range
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Setter range
	 * @param range the range to set
	 */
	public void setRange(int range) {
		this.range = range;
	}
	
	/**
	 * Getter duration
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Setter duration
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Getter effect
	 * @return the effect
	 */
	public int getEffect() {
		return effect;
	}
	
	/**
	 * Setter effect
	 * @param effect the effect to set
	 */
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(id);
		parcel.writeString(name);
		parcel.writeInt(range); 
		parcel.writeInt(duration);
		parcel.writeInt(effect);
	}
	
	
	
}
