package co.wethinkcode.healthsafe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

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

    @Test
    void shouldCreateWardWithCorrectValues()
    {
        Ward ward = new Ward(
                "W-05",
                "East Wing",
                "Paediatrics",
                5,
                null
        );

        assertEquals("W-05", ward.getWardId());
        assertEquals("East Wing", ward.getWing());
        assertEquals("Paediatrics", ward.getDepartment());
        assertEquals(5, ward.getBedsAvailable());
        assertEquals(null, ward.getNotes());
    }

    @Test
    void shouldParseValidWardRow()
    {
        WardParser parser = new WardParser();

        Ward ward = parser.parseWard("W-01, East Wing ,Cardiology,3");

        assertEquals("W-01", ward.getWardId());
        assertEquals("East Wing", ward.getWing());
        assertEquals("Cardiology", ward.getDepartment());
        assertEquals(3, ward.getBedsAvailable());
    }

    @Test
    void shouldParseAndCleanMessyWardRow()
    {
        WardParser parser = new WardParser();

        Ward ward = parser.parseWard("w-02,West Wing,paediatrics,N/A");

        assertEquals("W-02", ward.getWardId());
        assertEquals("West Wing", ward.getWing());
        assertEquals("Paediatrics", ward.getDepartment());
        assertEquals(null, ward.getBedsAvailable());
    }

    @Test
    void shouldHandleInvalidBedsWithoutCrashing()
    {
        WardParser parser = new WardParser();

        Ward ward = parser.parseWard("W-04,North Wing,Oncology,-1");

        assertEquals("W-04", ward.getWardId());
        assertEquals("Oncology", ward.getDepartment());
        assertEquals(null, ward.getBedsAvailable());
    }

    @Test
    void shouldHandleNonNumericBedsWithoutCrashing()
    {
        WardParser parser = new WardParser();

        Ward ward = parser.parseWard("w-05,east wing,PAEDIATRICS,five");

        assertEquals("W-05", ward.getWardId());
        assertEquals("East Wing", ward.getWing());
        assertEquals("Paediatrics", ward.getDepartment());
        assertEquals(null, ward.getBedsAvailable());
    }

    @Test
    void shouldParseMultipleWardRows()
    {
        WardParser parser = new WardParser();

        List<Ward> wards = parser.parseWards(List.of(
                "ward_id, Wing ,department,beds_available",
                "W-01, East Wing ,Cardiology,3",
                "w-02,West Wing,paediatrics,N/A",
                "W-03 ,east wing,Cardiology,0"
        ));

        assertEquals(3, wards.size());

        assertEquals("W-01", wards.get(0).getWardId());
        assertEquals("W-02", wards.get(1).getWardId());
        assertEquals("W-03", wards.get(2).getWardId());
    }

    @Test
    void shouldSkipCsvHeader()
    {
        WardParser parser = new WardParser();

        List<Ward> wards = parser.parseWards(List.of(
                "ward_id, Wing ,department,beds_available",
                "W-01, East Wing ,Cardiology,3"
        ));

        assertEquals(1, wards.size());
        assertEquals("W-01", wards.get(0).getWardId());
    }

    @Test
    void shouldHandleDuplicateWardIds()
    {
        WardParser parser = new WardParser();

        List<Ward> wards = parser.parseWards(List.of(
                "W-05,East Wing,Paediatrics,5",
                "w-05,east wing,PAEDIATRICS,five"
        ));

        assertEquals(1, wards.size());
        assertEquals("W-05", wards.get(0).getWardId());
        assertEquals(5, wards.get(0).getBedsAvailable());
    }

    @Test
    void shouldSkipMalformedRowsWithoutCrashing()
    {
        WardParser parser = new WardParser();

        List<Ward> wards = parser.parseWards(List.of(
                "ward_id, Wing ,department,beds_available",
                "W-01,East Wing,Cardiology,3",
                "this row is malformed"
        ));

        assertEquals(1, wards.size());
        assertEquals("W-01", wards.get(0).getWardId());
    }

    @Test
    void shouldParseActualCsvFile() throws Exception
    {
        CsvReader reader = new CsvReader();
        WardParser parser = new WardParser();

        List<String> rows = reader.readLines("wards-outdated.csv");
        List<Ward> wards = parser.parseWards(rows);

        assertEquals(17, wards.size());
    }
}