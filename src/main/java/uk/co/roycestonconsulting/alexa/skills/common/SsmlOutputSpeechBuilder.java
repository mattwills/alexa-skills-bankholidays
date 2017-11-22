package uk.co.roycestonconsulting.alexa.skills.common;

public class SsmlOutputSpeechBuilder {

	private StringBuilder ssmlOutput = new StringBuilder("<speak>");

	private SsmlOutputSpeechBuilder() {
	}

	public static SsmlOutputSpeechBuilder aSsmlOutputSpeechBuilder() {
		return new SsmlOutputSpeechBuilder();
	}

	public SsmlOutputSpeechBuilder with(String text) {
		ssmlOutput.append(text);
		return this;
	}

	public SsmlOutputSpeechBuilder with(String text, String value) {
		ssmlOutput.append(text);
		ssmlOutput.append(value);
		return this;
	}

	public SsmlOutputSpeechBuilder withBreak(int milliseconds) {
		ssmlOutput.append("<break time=\"");
		ssmlOutput.append(String.valueOf(milliseconds));
		ssmlOutput.append("ms\"/>");
		return this;
	}

	public SsmlOutputSpeechBuilder withSayAs(String interpretAs, String format, String value) {
		ssmlOutput.append("<say-as interpret-as=\"");
		ssmlOutput.append(interpretAs);
		ssmlOutput.append("\"");
		if (format != null) {
			ssmlOutput.append(" format=\"");
			ssmlOutput.append(format);
			ssmlOutput.append("\"");
		}
		ssmlOutput.append(">");
		ssmlOutput.append(value);
		ssmlOutput.append("</say-as>");
		return this;
	}

	public String build() {
		ssmlOutput.append("</speak>");
		return ssmlOutput.toString();
	}
}
