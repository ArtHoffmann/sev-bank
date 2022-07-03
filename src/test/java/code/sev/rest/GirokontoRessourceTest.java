package code.sev.rest;

import code.sev.model.GirokontoDO;
import code.sev.service.GirokontoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GirokontoRessourceTest {

    @InjectMock
    GirokontoService girokontoService;

    @Inject
    GirokontoRessource girokontoRessource;

    private GirokontoDO girokontoDO;

    @BeforeEach
    void setUp() {
        girokontoDO = new GirokontoDO();
        girokontoDO.setDispolimit(BigDecimal.ZERO);
        girokontoDO.setGuthaben(BigDecimal.ZERO);
        girokontoDO.setPin(1234);
        girokontoDO.setKontonummer(123456789L);
        girokontoDO.setName("Artur");
        girokontoDO.setOpeningDate(LocalDate.now());
    }


    @Test
    void getAll() {
        List<GirokontoDO> gk = new ArrayList<>();
        Mockito.when(girokontoService.findAll()).thenReturn(gk);
        Response response = girokontoRessource.findAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertNotNull(response.getEntity());
        List<GirokontoDO> entity = (List<GirokontoDO>) response.getEntity();
        assertTrue(entity.isEmpty());
    }

    @Test
    void findGiroKontoById() {
        List<GirokontoDO> gk = new ArrayList<>();
        gk.add(girokontoDO);
        Mockito.when(girokontoService.findGirokontoById(1L)).thenReturn(girokontoDO);
        Response response = girokontoRessource.findGiroKontoById(1L);
        assertNotNull(response);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertNotNull(response.getEntity());
        GirokontoDO entity = (GirokontoDO) response.getEntity();
        assertNotNull(entity);
        assertEquals("Artur", entity.getName());
    }
}