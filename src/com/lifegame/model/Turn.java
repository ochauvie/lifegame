package com.lifegame.model;

public class Turn {

	public static final int STEP_LIFE = 0;
	public static final int STEP_MAJ = 1;
	
	private int turn;
	private int step;
	
	
	
	public Turn() {
		super();
		this.turn = 1;
		this.step = STEP_LIFE;
	}
	/**
	 * Getter turn
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * Setter turn
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	/**
	 * Getter step
	 * @return the step
	 */
	public int getStep() {
		return step;
	}
	/**
	 * Setter step
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	public void endTurn() {
		this.step=Turn.STEP_LIFE;
		this.turn++;
	}
}
