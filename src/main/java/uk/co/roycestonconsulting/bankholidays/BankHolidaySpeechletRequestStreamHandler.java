package uk.co.roycestonconsulting.bankholidays;

import java.util.HashSet;
import java.util.Set;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

/**
 * Main {@link RequestStreamHandler} for the Bank Holiday Alexa Skill.
 */
public final class BankHolidaySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

	private static final Set<String> supportedApplicationIds = new HashSet<>();

	static {
		/*
		 * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
		supportedApplicationIds.add("amzn1.ask.skill.78974677-d932-4abe-b540-57d3dd3f307a");
	}

	public BankHolidaySpeechletRequestStreamHandler() {
		super(new BankHolidaySpeechlet(), supportedApplicationIds);
	}
}
