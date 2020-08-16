package com.storage.jwdnd.cloud.cloudstorage;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CloudStorageApplicationTests {
	Service service;

	@Test
	public void test() {
		assertEquals(5, service.returnInt());
	}
}
