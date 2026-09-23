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
}