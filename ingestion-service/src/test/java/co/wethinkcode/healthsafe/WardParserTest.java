package co.wethinkcode.healthsafe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class WardParserTest{
    @Test
        void shouldNormalizeWardIdToUpperCase()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeWardId("w-05");

        assertEquals("W-05", result);
    }

    @Test
    void shouldNotHaveSpaces()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeWardId("W-03 ");

        assertEquals("W-03", result);

    }

    @Test
    void sameDepartments()
    {
        WardParser parser = new WardParser();

        String result = parser.sameDepartmentCasing("PAEDIATRICS");

        assertEquals("Paediatrics", result);
    }


    @Test
    void shouldNormalizePediatricsToPaediatrics()
    {
        WardParser parser = new WardParser();

        String result = parser.sameDepartmentCasing("Pediatrics");

        assertEquals("Paediatrics", result);
    }


    @Test
    void shouldNormalizeWingSpaces()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeWing("South  Wing");

        assertEquals("South Wing", result);
    }


    @Test
    void shouldNormalizeWingCasing()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeWing("east wing ");

        assertEquals("East Wing", result);
    }


    @Test
    void shouldConvertMissingValueToNull()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("N/A");

        assertEquals(null, result);
    }


    @Test
    void shouldHandleMissingValueRegardlessOfCasing()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("Unknown");

        assertEquals(null, result);
    }

    @Test
    void shouldConvertBlankValueToNull()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("");

        assertEquals(null, result);
    }

    @Test
    void shouldConvertWhitespaceValueToNull()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("   ");

        assertEquals(null, result);
    }






}