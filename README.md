----
# alexa-skills-bank-holidays
### Basic Amazon Alexa Skill lambda written in Java to inform when the next UK bank holiday is
Alexa Skills can be configured to invoke either a lambda or a web service. This project serves as a simple example of how to write an AWS lambda for an Alexa Skill.

##### This Project
In order to produce a clean simple example, this project uses only Amazon Alexa and Lambda compile dependencies. Because of this, there are no dependency injection or IOC patterns in use, meaning that some classes are new'ed up inline where ordinarily one might choose to inject them.  
The purpose of this project is to provide a simple example of a Amazon Alexa Skill Lambda, rather than a production ready artifact.

#### Basic Process
The Alexa Skill can be configured to invoke a lambda to perform the skill action.
* For a java lambda, Alexa will instantiate the configured BankHolidaySpeechletRequestStreamHandler.
* The default no-arg constructor calls the superclass constructor, passing in an implementation of [SpeechletV2](https://developer.amazon.com/public/binaries/content/assets/javadoc/ask-java-library-3/com/amazon/speech/speechlet/Speechlet.html) and the supported application id.
* The `SpeechletV2` implements the lifecycle methods `onLauch` and `onIntent`, returning instances of [SpeechletResponse](https://developer.amazon.com/public/binaries/content/assets/javadoc/ask-java-library-3/com/amazon/speech/speechlet/speechletresponse.html).

----


