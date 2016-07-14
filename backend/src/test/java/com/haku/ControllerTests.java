package com.haku;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.haku.controller.SearchController;
import com.haku.eka.Eka;
import com.haku.service.SearchService;
import com.haku.toka.Toka;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

public class ControllerTests {
	
	  @InjectMocks
	  SearchController mockController;
	  
	  @Mock
	  SearchService mockService;
	  
	  private MockMvc mockMvc;
	
	  @Before
	  public void setUp() {
	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();
	  }
	  
	  @Test
	  public void restIsWorking() throws Exception {
		  
		  Eka eka = new Eka();
		  eka.setInfo("infoFirst");
		  eka.setXinfo("xinfoFirst");
		  
		  Toka toka = new Toka();
		  toka.setInfo("infoSecond");
		  toka.setXinfo("xinfoSecond");
		  		  		  
		  List<Object> list = new ArrayList<Object>();
		  list.add(eka);
		  list.add(toka);
		  
		  when(mockService.wrapSearchQueries("test")).thenReturn(list);
		  
	        
		  mockMvc.perform(get("/?s=test"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.[0].info").value("infoFirst"))
          .andExpect(jsonPath("$.[0].xinfo").value("xinfoFirst"))
          .andExpect(jsonPath("$.[1].info").value("infoSecond"))
          .andExpect(jsonPath("$.[1].xinfo").value("xinfoSecond"));
	                
	    }

}
