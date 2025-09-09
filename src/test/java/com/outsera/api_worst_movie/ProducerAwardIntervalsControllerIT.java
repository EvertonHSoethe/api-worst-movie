package com.outsera.api_worst_movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProducerAwardIntervalsControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void returnMinAndMaxProducerAwardIntervals() throws Exception {
		mockMvc.perform(get("/v1/producers/award-intervals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min[0].producers").value("Joel Silver"))
				.andExpect(jsonPath("$.min[0].interval").value(1))
				.andExpect(jsonPath("$.min[0].previousWin").value(1990))
				.andExpect(jsonPath("$.min[0].followingWin").value(1991))
				.andExpect(jsonPath("$.max[0].producers").value("Matthew Vaughn"))
				.andExpect(jsonPath("$.max[0].interval").value(13))
				.andExpect(jsonPath("$.max[0].previousWin").value(2002))
				.andExpect(jsonPath("$.max[0].followingWin").value(2015));
	}

}
