package driverFactory;

import org.testng.annotations.Test;

public class AppTest {
	@Test
	public void kickstart() throws Throwable {
		DriverScript dc = new DriverScript();
		dc.startTest();
	}

}
