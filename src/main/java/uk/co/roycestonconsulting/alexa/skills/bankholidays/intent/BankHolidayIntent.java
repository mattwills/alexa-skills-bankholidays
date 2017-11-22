package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum for Bank Holiday intents.
 */
public enum BankHolidayIntent {

	NEXT_BANK_HOLIDAY_INTENT("nextBankHoliday", new NextBankHolidayIntentHandler()),

	NEXT_SPECIFIC_BANK_HOLIDAY_INTENT("nextSpecificBankHoliday", new NextSpecificBankHolidayIntentHandler()),

	SPECIFIC_BANK_HOLIDAY_FOR_YEAR_INTENT("specificBankHolidayForYear", new SpecificBankHolidayIntentHandler()),

	SPECIFIC_BANK_HOLIDAY_OCCURRED_THIS_YEAR_INTENT("specificBankHolidayOccurredThisYear", new SpecificBankHolidayIntentHandler()),

	AMAZON_HELP_INTENT("AMAZON.HelpIntent", new HelpIntentHandler());

	private final String value;

	private final IntentHandler intentHandler;

	BankHolidayIntent(String value, IntentHandler intentHandler) {
		this.value = value;
		this.intentHandler = intentHandler;
	}

	public IntentHandler getIntentHandler() {
		return intentHandler;
	}

	public static Optional<BankHolidayIntent> from(String value) {
		return Stream.of(BankHolidayIntent.values())
				.filter(intent -> intent.value.equals(value))
				.findFirst();
	}
}
