package uk.co.roycestonconsulting.alexa.skills.bankholidays.model;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.EASTER_MONDAY;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.EASTER_SUNDAY;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a bank holiday.
 */
@Data
@Builder
public class BankHoliday {

	private String title;

	private LocalDate date;

	private final String notes;

	private final boolean bunting;

	@JsonCreator
	public BankHoliday(@JsonProperty("title") String title,
			@JsonProperty("date") @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd") LocalDate date,
			@JsonProperty("notes") String notes,
			@JsonProperty("bunting") boolean bunting) {
		this.title = title;
		this.date = date;
		this.notes = notes;
		this.bunting = bunting;
	}

	/**
	 * Forwards the date and title of the bank holiday from Easter Sunday to Easter Monday.
	 */
	public void forwardToEasterMonday() {
		title = EASTER_MONDAY.getOfficialName();
		date = date.plusDays(1);
	}

	/**
	 * Reverts the date and title of the bank holiday from Easter Monday to Easter Sunday.
	 */
	public void revertToEasterSunday() {
		title = EASTER_SUNDAY.getOfficialName();
		date = date.minusDays(1);
	}
}
