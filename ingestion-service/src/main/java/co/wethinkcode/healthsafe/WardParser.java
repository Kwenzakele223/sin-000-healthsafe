package co.wethinkcode.healthsafe;
import org.apache.commons.text.WordUtils;

class WardParser{

    public String normalizeWardId(String wardId)
    {
        String upper = wardId.trim().toUpperCase();
        return upper;
    }

    public String sameDepartmentCasing(String department)
    {
        String casing = department.substring(0,1).toUpperCase() + department.substring(1).toLowerCase();

        if(casing.equals("Pediatrics")){return "Paediatrics";}
        return casing;
    }

    public String normalizeWing(String wing)
    {
        String cleaned = wing.trim().replace("  "," ");

        String spaceBetween = WordUtils.capitalize(cleaned);
        return spaceBetween;
    }

    public String normalizeMissingValue(String value)
    {
       String upper = value.toUpperCase();
        if(upper.equals("N/A") || upper.equals("TBD") || upper.equals("UNKNOWN") || upper.trim().equals(""))
        {return null;}

        return value;
    }
}