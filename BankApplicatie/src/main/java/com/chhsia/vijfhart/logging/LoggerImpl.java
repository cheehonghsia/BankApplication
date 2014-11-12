package com.chhsia.vijfhart.logging;

import org.springframework.stereotype.Component;

@Component
public class LoggerImpl implements Logger {

	public void log(String logstring) {
		java.util.logging.Logger.getLogger("BankLogger").info(logstring);
	}

}
