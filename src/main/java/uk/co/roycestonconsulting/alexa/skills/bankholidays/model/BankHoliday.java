package uk.co.roycestonconsulting.alexa.skills.bankholidays.model;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents a bank holiday.
 */
public class BankHoliday {

	private String title;

	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;

	private String notes;

	private boolean bunting;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isBunting() {
		return bunting;
	}

	public void setBunting(boolean bunting) {
		this.bunting = bunting;
	}
}
