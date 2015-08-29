package org.adorsys.adbase;

import org.junit.Test;

public class SubStringTest {

	/**
	 * @see OrgUnitIdEJB#subStringParentId(String, Integer)
	 */
	@Test
	public void testSubstr() {
		String randomid = "abcdef12345";
		int sizeToRemove = 4;
		int remainingSize = randomid.length() - sizeToRemove;
		String remainingStr = randomid.substring(0,remainingSize);
		String expected = "abcdef1";
		assert remainingStr.equals(expected);
	}
}
