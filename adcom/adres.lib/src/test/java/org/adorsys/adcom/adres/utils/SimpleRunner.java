package org.adorsys.adcom.adres.utils;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class SimpleRunner extends BlockJUnit4ClassRunner {
	private final Class<?> clazz;
	private static Weld bootloader;
	private static WeldContainer weldContainer;

	private static volatile int counter = 0;

	public SimpleRunner(final Class<?> clazz) throws InitializationError {
		super(clazz);
		this.clazz = clazz;

		if (counter == 0) {
			bootloader = new Weld();
			weldContainer = bootloader.initialize();
		}
		counter++;
	}

	@Override
	protected Object createTest() throws Exception {
		return weldContainer.instance().select(clazz).get();
	}

	@Override
	public void run(RunNotifier notifier) {
		try {
			super.run(notifier);
		} finally {
			counter--;
			if (counter == 0) {
				bootloader.shutdown();
			}
		}
	}
}