package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WardClientTest {

    @Test
    void shouldGetWardsFromIngestionService() {

        WardClient client = new WardClient();

        List<Ward> wards = client.getWards();

        assertEquals(17, wards.size());

        assertEquals("W-01", wards.get(0).getWardId());
        assertEquals("East Wing", wards.get(0).getWing());
        assertEquals("Cardiology", wards.get(0).getDepartment());
        assertEquals(3, wards.get(0).getBedsAvailable());
    }
}

