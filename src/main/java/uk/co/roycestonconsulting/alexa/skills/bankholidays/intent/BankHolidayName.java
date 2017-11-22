package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static java.util.Arrays.stream;

/**
 * Represents a bank holiday's colloquial name and official name. Includes Easter Sunday, even though it's technically not a bank holiday.
 */
public enum BankHolidayName {

	NEW_YEARS_DAY("New Year's Day", "New Year's Day"),
	GOOD_FRIDAY("Good Friday", "Good Friday"),
	EASTER_SUNDAY("Easter Sunday", "Easter Sunday"),
	EASTER("Easter", "Easter Sunday"),
	EASTER_MONDAY("Easter Monday", "Easter Monday"),
	EARLY_MAY_BANK("Early May bank holiday", "Early May bank holiday"),
	FIRST_MAY_BANK("First May bank holiday", "Early May bank holiday"),
	MAY_BANK_HOLIDAY("May bank holiday", "Early May bank holiday"),
	SPRING_BANK_HOLIDAY("Spring bank holiday", "Spring bank holiday"),
	LAST_MAY_BANK_HOLIDAY("Last May bank holiday", "Spring bank holiday"),
	SECOND_MAY_BANK_HOLIDAY("Second May bank holiday", "Spring bank holiday"),
	SUMMER_BANK_HOLIDAY("Summer bank holiday", "Summer bank holiday"),
	AUGUST_BANK_HOLIDAY("August bank holiday", "Summer bank holiday"),
	CHRISTMAS_DAY("Christmas Day", "Christmas Day"),
	BOXING_DAY("Boxing Day", "Boxing Day");

	private final String name;

	private final String officialName;

	BankHolidayName(String name, String officialName) {
		this.name = name;
		this.officialName = officialName;
	}

	/**
	 * @return The name of this bank holiday.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The officialName of this bank holiday.
	 */
	public String getOfficialName() {
		return officialName;
	}

	/**
	 * Looks up a {@link BankHolidayName} using it's name.
	 *
	 * @param name The name of the bank holiday, which may not be the official name.
	 * @return The matching {@link BankHolidayName}.
	 */
	public static BankHolidayName fromName(String name) {
		return stream(values())
				.filter(bankHolidayName -> bankHolidayName.name.equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unable to find bank holiday: " + name));
	}
}
