package com.atmecs.glooko.testfunction;

public class TrimString
{
	String medicineQuntityByHistory;
	String medicineQuntityFromHistory;

	public String trimString(String foodAndMedicineQuntity)
	{
		medicineQuntityByHistory = foodAndMedicineQuntity;
		int spacePos = medicineQuntityByHistory.indexOf(" ");
		if (spacePos > 0) {
			medicineQuntityFromHistory = medicineQuntityByHistory.substring(0,spacePos);

	}
		return medicineQuntityFromHistory;
	}
}
