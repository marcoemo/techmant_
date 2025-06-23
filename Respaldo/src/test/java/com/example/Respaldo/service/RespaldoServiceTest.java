package com.example.Respaldo.service;

import com.example.Respaldo.model.Respaldo;
import com.example.Respaldo.repository.RespaldoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RespaldoServiceTest {

    @Mock
    private RespaldoRepository respaldoRepository;

    @InjectMocks
    private RespaldoService respaldoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generarRespaldo_guardaYRetornaRespaldo() throws Exception {
        // Arrange
        Respaldo respaldoMock = new Respaldo();
        respaldoMock.setId(1L);
        respaldoMock.setNombreArchivo("respaldo_test.sql");
        respaldoMock.setExito(true);

        // Simula que el repositorio retorna el respaldo guardado
        when(respaldoRepository.save(any(Respaldo.class))).thenReturn(respaldoMock);

        // Simula el proceso externo para evitar realmente ejecutarlo
        try (MockedConstruction<ProcessBuilder> ignored = mockConstruction(ProcessBuilder.class, (pb, context) -> {
            Process processMock = mock(Process.class);
            when(processMock.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(new byte[0]));
            when(processMock.waitFor()).thenReturn(0);
            when(pb.start()).thenReturn(processMock);
        })) {
            // Act
            Respaldo result = respaldoService.generarRespaldo();

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.isExito()).isTrue();
            assertThat(result.getNombreArchivo()).contains("respaldo_");
            verify(respaldoRepository).save(any(Respaldo.class));
        }
    }
}