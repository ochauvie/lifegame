package com.lifegame.model;

public class Mode {
	public static final String MODE = "MODE";
	public static final String MODE_AUTO = "AUTO";
	public static final String MODE_STEP = "STEP";
	
	private String mode;

	
	public Mode(String mode) {
		super();
		this.mode = mode;
	}

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
	
	
	
}
