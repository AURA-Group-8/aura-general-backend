package com.aura8.general_backend.service;

import com.aura8.general_backend.infraestructure.entities.SchedulingSettings;
import com.aura8.general_backend.infraestructure.repository.SchedulingSettingsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulingSettingsServiceTest {

    @InjectMocks
    SchedulingSettingsService service;

    @Mock
    SchedulingSettingsRepository repository;

    @Test
    @DisplayName("Testa patch com dados validos e todos os campos preenchidos")
    void TestaUpdateComDadosValidos() {

        LocalTime time = LocalTime.now();
        SchedulingSettings initialSettings = new SchedulingSettings();
        initialSettings.setId(1);
        initialSettings.setWorkStart(time);
        initialSettings.setWorkEnd(time);
        initialSettings.setBreakStart(time);
        initialSettings.setBreakEnd(time);

        time = time.plusHours(2);
        SchedulingSettings settings = new SchedulingSettings();
        settings.setId(1);
        settings.setWorkStart(time);
        settings.setWorkEnd(time);
        settings.setBreakStart(time);
        settings.setBreakEnd(time);

        when(repository.findById(anyInt())).thenReturn(Optional.of(initialSettings));
        when(repository.save(settings)).thenReturn(settings);

        SchedulingSettings updatedSettings = service.updatePatch(settings);

        assertNotNull(updatedSettings);
        assertNotNull(updatedSettings.getBreakEnd());
        assertNotNull(updatedSettings.getWorkStart());
        assertNotNull(updatedSettings.getBreakStart());
        assertNotNull(updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getWorkStart());
        assertEquals(time, updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getBreakStart());
        assertEquals(time, updatedSettings.getBreakEnd());
        verify(repository,times(1)).save(settings);
    }

    @Test
    @DisplayName("Testa patch com dados validos e alguns preenchidos")
    void TestaUpdateComDadosFaltantes() {

        LocalTime time = LocalTime.now();
        SchedulingSettings initialSettings = new SchedulingSettings();
        initialSettings.setId(1);
        initialSettings.setWorkStart(time);
        initialSettings.setWorkEnd(time);
        initialSettings.setBreakStart(time);
        initialSettings.setBreakEnd(time);

        time = time.plusHours(2);
        SchedulingSettings settings = new SchedulingSettings();
        settings.setId(1);
        settings.setWorkStart(null);
        settings.setWorkEnd(time);
        settings.setBreakStart(null);
        settings.setBreakEnd(time);

        SchedulingSettings expectedResult = new SchedulingSettings();
        expectedResult.setId(1);
        expectedResult.setWorkStart(time.minusHours(2));
        expectedResult.setWorkEnd(time);
        expectedResult.setBreakStart(time.minusHours(2));
        expectedResult.setBreakEnd(time);

        when(repository.findById(anyInt())).thenReturn(Optional.of(initialSettings));
        when(repository.save(settings)).thenReturn(expectedResult);

        SchedulingSettings updatedSettings = service.updatePatch(settings);

        assertNotNull(updatedSettings);
        assertNotNull(updatedSettings.getBreakEnd());
        assertNotNull(updatedSettings.getWorkStart());
        assertNotNull(updatedSettings.getBreakStart());
        assertNotNull(updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getBreakEnd());
        assertEquals(time.minusHours(2), updatedSettings.getWorkStart());
        assertEquals(time.minusHours(2), updatedSettings.getBreakStart());
        verify(repository,times(1)).save(settings);

    }

    @Test
    @DisplayName("Testa patch com nenhum dado preenchido")
    void TestaUpdateComTodosOsDadosFaltantes() {

        LocalTime time = LocalTime.now();
        SchedulingSettings initialSettings = new SchedulingSettings();
        initialSettings.setId(1);
        initialSettings.setWorkStart(time);
        initialSettings.setWorkEnd(time);
        initialSettings.setBreakStart(time);
        initialSettings.setBreakEnd(time);

        SchedulingSettings settings = new SchedulingSettings();
        settings.setId(1);
        settings.setWorkStart(null);
        settings.setWorkEnd(null);
        settings.setBreakStart(null);
        settings.setBreakEnd(null);

        when(repository.findById(anyInt())).thenReturn(Optional.of(initialSettings));
        when(repository.save(settings)).thenReturn(initialSettings);

        SchedulingSettings updatedSettings = service.updatePatch(settings);

        assertNotNull(updatedSettings);
        assertNotNull(updatedSettings.getBreakEnd());
        assertNotNull(updatedSettings.getWorkStart());
        assertNotNull(updatedSettings.getBreakStart());
        assertNotNull(updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getWorkStart());
        assertEquals(time, updatedSettings.getWorkEnd());
        assertEquals(time, updatedSettings.getBreakStart());
        assertEquals(time, updatedSettings.getBreakEnd());

        verify(repository,times(1)).save(settings);

    }
}