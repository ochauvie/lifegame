package com.lifegame.model;

public class Cell {

	public static final int CEL_EMPTY = 0;
	public static final int CEL_IN_LIFE = 1;
	public static final int CEL_DEAD = 2;
	public static final int CEL_NEW = 3;
	
	private int status;

	/**
	 * Constructor
	 * @param status
	 */
	public Cell(int status) {
		super();
		this.status = status;
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
	
	
	
}
