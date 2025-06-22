package com.example.Soporte;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoporteApplicationTests {

	@org.springframework.boot.test.mock.mockito.MockBean
	private com.example.Soporte.repository.TicketRepository ticketRepository;

	@Test
	void contextLoads() {
	}
}
