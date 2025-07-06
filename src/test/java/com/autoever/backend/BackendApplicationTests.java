package com.autoever.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
	}

	@Test
	void applicationContextLoadsSuccessfully() {
		// ApplicationContext가 정상적으로 로드되는지 확인
		assert(context != null);
	}
}
