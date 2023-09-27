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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

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
    public void testGetMusicRecordByAlbumYear() throws Exception {
        MusicRecord musicRecord
                = new MusicRecord(102L,
                "Album 102",
                "Artist 102",
                2021,
                30L,
                MusicGenre.Jazz);

        when(mockRecordManagerServiceImpl.getMusicRecordByReleaseYear(
                musicRecord.getReleaseYear())).thenReturn(musicRecord);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/record/" + musicRecord.getReleaseYear()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Album 102"));
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