package co.wethinkcode.healthsafe;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

class WardParser {

    public String normalizeWardId(String wardId) {
        String upper = wardId.trim().toUpperCase();
        return upper;
    }

    public String sameDepartmentCasing(String department) {
        String casing = department.substring(0, 1).toUpperCase()
                + department.substring(1).toLowerCase();

        if (casing.equals("Pediatrics")) {
            return "Paediatrics";
        }

        if (casing.equals("Icu")) {
            return "ICU";
        }


        return casing;
    }

    public String normalizeWing(String wing) {
        String cleaned = wing.trim().replaceAll("\\s+", " ");

        String spaceBetween = WordUtils.capitalize(cleaned);
        return spaceBetween;
    }

    public String normalizeMissingValue(String value) {
        String upper = value.toUpperCase();

        if (upper.equals("N/A")
                || upper.equals("TBD")
                || upper.equals("UNKNOWN")
                || upper.equals("-")
                || upper.equals("NAN")
                || value.trim().equals("")) {
            return null;
        }

        return value;
    }

    public Integer normalizeBedsAvailable(String beds) {
        try {
            int number = Integer.parseInt(beds);

            if (number < 0 || number > 100) {
                return null;
            }

            return number;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Ward parseWard(String row) {
        String[] columns = row.split(",");

        String wardId = normalizeWardId(columns[0]);
        String wing = normalizeWing(columns[1]);
        String department = sameDepartmentCasing(columns[2]);
        Integer bedsAvailable = normalizeBedsAvailable(columns[3]);

        String notes = null;

        return new Ward(wardId, wing, department, bedsAvailable, notes);
    }

    public List<Ward> parseWards(List<String> rows) {
        Map<String, Ward> uniqueWards = new LinkedHashMap<>();

        int startIndex = 0;

        if (rows.get(0).toLowerCase().startsWith("ward_id")) {
            startIndex = 1;
        }

        for (int i = startIndex; i < rows.size(); i++) {

            String row = rows.get(i);
            String[] columns = row.split(",");

            // Skip malformed rows
            if (columns.length != 4) {
                continue;
            }

            Ward ward = parseWard(row);

            if (!uniqueWards.containsKey(ward.getWardId())) {
                uniqueWards.put(ward.getWardId(), ward);
            }
        }

        return new ArrayList<>(uniqueWards.values());
    }


}