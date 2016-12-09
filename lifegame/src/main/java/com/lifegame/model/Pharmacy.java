package com.lifegame.model;

public class Pharmacy {

	private int quantity;
	private Virus virus;
	
	public Pharmacy(int quantity, Virus virus) {
		super();
		this.quantity = quantity;
		this.virus = virus;
	}
	/**
	 * Getter quantity
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * Setter quantity
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	
	
}
