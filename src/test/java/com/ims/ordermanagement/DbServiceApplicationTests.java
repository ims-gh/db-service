package com.ims.ordermanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.ims.ordermanagement")
class DbServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
