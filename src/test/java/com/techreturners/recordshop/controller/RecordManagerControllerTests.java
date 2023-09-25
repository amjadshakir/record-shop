package com.techreturners.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.recordshop.model.MusicGenre;
import com.techreturners.recordshop.model.MusicRecord;
import com.techreturners.recordshop.service.RecordManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RecordManagerControllerTests {

    @Mock
    private RecordManagerServiceImpl mockRecordManagerServiceImpl;

    @InjectMocks
    private RecordManagerController recordManagerController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(recordManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testPostMappingAddAMusicRecord() throws Exception {

        MusicRecord musicRecord =
                new MusicRecord(4L, "Album One", "Artist One", 1992, 100L, MusicGenre.Country);

        when(mockRecordManagerServiceImpl.insertMusicRecord(musicRecord)).thenReturn(musicRecord);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/record/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(musicRecord)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockRecordManagerServiceImpl,
                times(1)).insertMusicRecord(musicRecord);
    }

}