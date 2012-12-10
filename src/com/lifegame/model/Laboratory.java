package com.lifegame.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.lifegame.R;

public class Laboratory {

	private List<Pharmacy> pharmacy;
	
	
	public Laboratory(Activity activity, int nbVirus, int player) {
		pharmacy = new ArrayList<Pharmacy>();
		pharmacy.add(new Pharmacy(nbVirus, new Virus(Virus.VIRUS_ID_A, activity.getString(R.string.virus_A), 2, 1, Cell.CEL_EMPTY, player)));
		pharmacy.add(new Pharmacy(nbVirus, new Virus(Virus.VIRUS_ID_B, activity.getString(R.string.virus_B), 1, 2, Cell.CEL_EMPTY, player)));
		pharmacy.add(new Pharmacy(nbVirus, new Virus(Virus.VIRUS_ID_C, activity.getString(R.string.virus_C), 3, 1, Cell.CEL_IN_LIFE, player)));
		pharmacy.add(new Pharmacy(nbVirus, new Virus(Virus.VIRUS_ID_D, activity.getString(R.string.virus_D), 1, 3, Cell.CEL_IN_LIFE, player)));
		pharmacy.add(new Pharmacy(nbVirus, new Virus(Virus.VIRUS_ID_E, activity.getString(R.string.virus_E), 2, 3, Cell.CEL_FROZEN, player)));
	}

	public Pharmacy getPharmacyByVirusId(String virusId) {
		Pharmacy result = null;
		for (Pharmacy ph:pharmacy) {
			if (ph.getVirus().getId().equals(virusId)) {
				result = ph;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Getter pharmacy
	 * @return the pharmacy
	 */
	public List<Pharmacy> getPharmacy() {
		return pharmacy;
	}

	/**
	 * Setter pharmacy
	 * @param pharmacy the pharmacy to set
	 */
	public void setPharmacy(List<Pharmacy> pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
	
}
