package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;

/**
 * Reads UK gov bank holiday data from a JSON file resource.
 */
public class JsonFileBankHolidayRepository implements BankHolidayRepository {

	// File content from https://www.gov.uk/bank-holidays.json
	// TODO - investigate dynamic loading options
	private static final String JSON_FILE = "bank-holidays.json";

	@Override
	public BankHolidayEvents getBankHolidays(Division division) {
		ObjectMapper mapper = objectMapper();
		MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, BankHolidayEvents.class);
		try {
			InputStream jsonFile = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE);
			HashMap<String, BankHolidayEvents> map = mapper.readValue(jsonFile, mapType);
			return map.get(division.getValue());
		} catch (IOException e) {
			throw new IllegalStateException("Unable to read JSON from file");
		}
	}

	private ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

}
