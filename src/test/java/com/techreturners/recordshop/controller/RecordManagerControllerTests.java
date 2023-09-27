package com.techreturners.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.recordshop.exception.RecordNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;


import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void setup() {
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

    @Test
    public void testGetMusicRecordsByAlbumYearSingleRecord() throws Exception {
        MusicRecord musicRecord
                = new MusicRecord(102L,
                "Album 102",
                "Artist 102",
                2021,
                30L,
                MusicGenre.Jazz);

        when(mockRecordManagerServiceImpl.getMusicRecordByReleaseYear(
                musicRecord.getReleaseYear())).thenReturn(List.of(musicRecord));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record/releaseYear/" + musicRecord.getReleaseYear()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(List.of(musicRecord))));
    }

    @Test
    public void testGetMusicRecordsByAlbumYearMultipleRecords() throws Exception {
        MusicRecord musicRecordFirst
                = new MusicRecord(102L,
                "Album 102",
                "Artist 102",
                2021,
                30L,
                MusicGenre.Jazz);
        MusicRecord musicRecordSecond
                = new MusicRecord(103L,
                "Album 103",
                "Artist 103",
                2023,
                40L,
                MusicGenre.Country);

        when(mockRecordManagerServiceImpl.getMusicRecordByReleaseYear(
                musicRecordFirst.getReleaseYear())).thenReturn(List.of(musicRecordFirst, musicRecordSecond));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record/releaseYear/" + musicRecordFirst.getReleaseYear()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(List.of(musicRecordFirst, musicRecordSecond))));
    }

    @Test
    public void testGetMusicRecordNotFoundByReleaseYear() {
        MusicRecord musicRecord
                = new MusicRecord(103L, "Album 103",
                "Artist 103", 2000, 100L, MusicGenre.Rock);
        when(mockRecordManagerServiceImpl.getMusicRecordByReleaseYear(
                musicRecord.getReleaseYear())).thenThrow(RecordNotFoundException.class);

        assertThrows(RecordNotFoundException.class, () -> mockRecordManagerServiceImpl.getMusicRecordByReleaseYear(
                musicRecord.getReleaseYear()));

    }

    @Test
    public void testDeleteMusicRecordByValidId() throws Exception {
        MusicRecord musicRecord
                = new MusicRecord(103L, "Album 103",
                "Artist 103", 2000, 100L, MusicGenre.Rock);

        when(mockRecordManagerServiceImpl.insertMusicRecord(musicRecord)).thenReturn(musicRecord);
        when(mockRecordManagerServiceImpl.deleteRecordById(musicRecord.getId())).thenReturn(true);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.delete("/api/v1/record/" + musicRecord.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void testDeleteMusicRecordByInvalidId() {
        MusicRecord musicRecord
                = new MusicRecord(103L, "Album 103",
                "Artist 103", 2000, 100L, MusicGenre.Rock);

        when(mockRecordManagerServiceImpl.deleteRecordById(
                musicRecord.getId())).thenThrow(RecordNotFoundException.class);

        assertThrows(RecordNotFoundException.class, () -> mockRecordManagerServiceImpl.deleteRecordById(musicRecord.getId()));
    }

    @Test
    public void testGetAllRecordsInStock() throws Exception {
        List<MusicRecord> records = new ArrayList<>();
        records.add(new MusicRecord(1L, "Album 1",
                "Artist 1", 2000, 100L, MusicGenre.Rock));
        records.add(new MusicRecord(2L, "Album 2",
                "Artist 2", 2000, 200L, MusicGenre.Jazz));
        when(mockRecordManagerServiceImpl.getAllRecordsInStock()).thenReturn(records);
        ResponseEntity<List<MusicRecord>> response = recordManagerController.getAllRecordsInStock();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(records, response.getBody());
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record/stock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Album 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Artist 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].releaseYear").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stock").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("Rock"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Album 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Artist 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseYear").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stock").value(200L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("Jazz"));
    }

    @Test
    public void testGetAllRecordsByGenre() throws Exception {
        List<MusicRecord> records = new ArrayList<>();
        records.add(new MusicRecord(1L, "Album 1",
                "Artist 1", 2000, 100L, MusicGenre.Rock));
        records.add(new MusicRecord(2L, "Album 2",
                "Artist 2", 2000, 200L, MusicGenre.Rock));
        when(mockRecordManagerServiceImpl.getAllRecordsByGenre(MusicGenre.Rock)).thenReturn(records);
        ResponseEntity<List<MusicRecord>> response = recordManagerController.getAllRecordsByGenre(MusicGenre.Rock);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(records, response.getBody());
        assertThat(response.getBody()).hasSize(2);
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record/genre/Rock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Album 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Artist 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].releaseYear").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stock").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("Rock"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Album 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Artist 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseYear").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stock").value(200L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("Rock"));
    }

    @Test
    public void testReplaceExistingRecordDetails() throws Exception {
        MusicRecord musicRecord =
                new MusicRecord(4L, "Album One", "Artist One", 1992, 100L, MusicGenre.Country);
        when(mockRecordManagerServiceImpl.replaceExistingRecord(4L, musicRecord)).thenReturn(musicRecord);
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/record/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(musicRecord)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGetAllAlbumsByArtist() throws Exception {
        MusicRecord musicRecord =
                new MusicRecord(1L, "Album One", "Artist One", 1992, 100L, MusicGenre.Country);

        when(mockRecordManagerServiceImpl.getAllAlbumsByArtist("Artist One")).thenReturn(List.of(musicRecord));

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/record/artist/Artist One/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(List.of(musicRecord))));
    }

    @Test
    public void testGetAllAlbumsByArtist_NoAlbumsFound() throws Exception {
        when(mockRecordManagerServiceImpl.getAllAlbumsByArtist("Artist One")).thenReturn(Collections.emptyList());

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/record/artist/Artist One/albums"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAllAlbumsByArtist_MultipleAlbums() throws Exception {
        MusicRecord musicRecord1 = new MusicRecord(1L, "Album One", "Artist One", 1992, 100L, MusicGenre.Country);
        MusicRecord musicRecord2 = new MusicRecord(2L, "Album Two", "Artist One", 1995, 120L, MusicGenre.Rock);

        when(mockRecordManagerServiceImpl.getAllAlbumsByArtist("Artist One")).thenReturn(List.of(musicRecord1, musicRecord2));

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/record/artist/Artist One/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(List.of(musicRecord1, musicRecord2))));
    }

}