package com.lifegame.model;

public class Virus {

	private String name;
	private int range;
	private int duration;
	private int effect;
	
	
	
	public Virus(String name, int range, int duration, int effect) {
		super();
		this.name = name;
		this.range = range;
		this.duration = duration;
		this.effect = effect;
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
	
	
	
}
