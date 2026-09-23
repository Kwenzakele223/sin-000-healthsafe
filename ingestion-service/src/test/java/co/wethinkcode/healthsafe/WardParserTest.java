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

    @Test
    void shouldConvertDashToNull()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("-");

        assertEquals(null, result);
    }

    @Test
    void shouldConvertNaNToNull()
    {
        WardParser parser = new WardParser();

        String result = parser.normalizeMissingValue("NaN");

        assertEquals(null, result);
    }

    @Test
    void shouldParseValidBedsAvailable()
    {
        WardParser parser = new WardParser();

        int result = parser.normalizeBedsAvailable("5");

        assertEquals(5, result);
    }

    @Test
    void shouldConvertNegativeBedsToNull()
    {
        WardParser parser = new WardParser();

        Integer result = parser.normalizeBedsAvailable("-1");

        assertEquals(null, result);
    }


    @Test
    void shouldConvertNonNumericBedsToNull()
    {
        WardParser parser = new WardParser();

        Integer result = parser.normalizeBedsAvailable("five");

        assertEquals(null, result);
    }

    @Test
    void shouldConvertFullToNull()
    {
        WardParser parser = new WardParser();

        Integer result = parser.normalizeBedsAvailable("full");

        assertEquals(null, result);
    }

    @Test
    void shouldConvertUnrealisticBedsToNull()
    {
        WardParser parser = new WardParser();

        Integer result = parser.normalizeBedsAvailable("2023");

        assertEquals(null, result);
    }

}