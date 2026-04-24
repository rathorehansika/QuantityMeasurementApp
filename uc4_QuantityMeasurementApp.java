public class uc4_QuantityMeasurementApp {
    private final double value;
    private final String unit; // Using String instead of Enum to guarantee 1 class file

    public uc4_QuantityMeasurementApp(double value, String unit) {
        if (unit == null || (!unit.equals("FEET") && !unit.equals("INCHES") && !unit.equals("YARDS") && !unit.equals("CENTIMETERS"))) {
            throw new IllegalArgumentException("Unit cannot be null or invalid");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    private double getConversionFactor() {
        if ("FEET".equals(unit)) return 1.0;
        if ("INCHES".equals(unit)) return 1.0 / 12.0;
        if ("YARDS".equals(unit)) return 3.0;
        if ("CENTIMETERS".equals(unit)) return 0.393701 / 12.0;
        return 1.0;
    }

    private double getBaseValue() {
        return value * getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        uc4_QuantityMeasurementApp that = (uc4_QuantityMeasurementApp) obj;
        return Math.abs(this.getBaseValue() - that.getBaseValue()) < 0.00001;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", \"" + unit.toLowerCase() + "\")";
    }

    public static void main(String[] args) {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(3.0, "FEET");
        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        uc4_QuantityMeasurementApp q3 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q4 = new uc4_QuantityMeasurementApp(36.0, "INCHES");
        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        uc4_QuantityMeasurementApp q5 = new uc4_QuantityMeasurementApp(2.0, "YARDS");
        uc4_QuantityMeasurementApp q6 = new uc4_QuantityMeasurementApp(2.0, "YARDS");
        System.out.println("Input: " + q5 + " and " + q6);
        System.out.println("Output: Equal (" + q5.equals(q6) + ")");

        uc4_QuantityMeasurementApp q7 = new uc4_QuantityMeasurementApp(2.0, "CENTIMETERS");
        uc4_QuantityMeasurementApp q8 = new uc4_QuantityMeasurementApp(2.0, "CENTIMETERS");
        System.out.println("Input: " + q7 + " and " + q8);
        System.out.println("Output: Equal (" + q7.equals(q8) + ")");

        uc4_QuantityMeasurementApp q9 = new uc4_QuantityMeasurementApp(1.0, "CENTIMETERS");
        uc4_QuantityMeasurementApp q10 = new uc4_QuantityMeasurementApp(0.393701, "INCHES");
        System.out.println("Input: " + q9 + " and " + q10);
        System.out.println("Output: Equal (" + q9.equals(q10) + ")");

        System.out.println("\nRunning Tests...");
        testEquality_YardToYard_SameValue();
        testEquality_YardToYard_DifferentValue();
        testEquality_YardToFeet_EquivalentValue();
        testEquality_FeetToYard_EquivalentValue();
        testEquality_YardToInches_EquivalentValue();
        testEquality_InchesToYard_EquivalentValue();
        testEquality_YardToFeet_NonEquivalentValue();
        testEquality_centimetersToInches_EquivalentValue();
        testEquality_centimetersToFeet_NonEquivalentValue();
        testEquality_MultiUnit_TransitiveProperty();
        testEquality_YardSameReference();
        testEquality_YardNullComparison();
        try { testEquality_YardWithNullUnit(); } catch (IllegalArgumentException e) { System.out.println("testEquality_YardWithNullUnit: PASS (Exception thrown)"); }
        testEquality_CentimetersSameReference();
        testEquality_CentimetersNullComparison();
        try { testEquality_CentimetersWithNullUnit(); } catch (IllegalArgumentException e) { System.out.println("testEquality_CentimetersWithNullUnit: PASS (Exception thrown)"); }
        testEquality_AllUnits_ComplexScenario();
    }

    private static void testEquality_YardToYard_SameValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        System.out.println("testEquality_YardToYard_SameValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardToYard_DifferentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(2.0, "YARDS");
        System.out.println("testEquality_YardToYard_DifferentValue: " + (!q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardToFeet_EquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(3.0, "FEET");
        System.out.println("testEquality_YardToFeet_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_FeetToYard_EquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(3.0, "FEET");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        System.out.println("testEquality_FeetToYard_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardToInches_EquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(36.0, "INCHES");
        System.out.println("testEquality_YardToInches_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_InchesToYard_EquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(36.0, "INCHES");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        System.out.println("testEquality_InchesToYard_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardToFeet_NonEquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(2.0, "FEET");
        System.out.println("testEquality_YardToFeet_NonEquivalentValue: " + (!q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_centimetersToInches_EquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "CENTIMETERS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(0.393701, "INCHES");
        System.out.println("testEquality_centimetersToInches_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_centimetersToFeet_NonEquivalentValue() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "CENTIMETERS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(1.0, "FEET");
        System.out.println("testEquality_centimetersToFeet_NonEquivalentValue: " + (!q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_MultiUnit_TransitiveProperty() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(3.0, "FEET");
        uc4_QuantityMeasurementApp q3 = new uc4_QuantityMeasurementApp(36.0, "INCHES");
        boolean pass = q1.equals(q2) && q2.equals(q3) && q1.equals(q3);
        System.out.println("testEquality_MultiUnit_TransitiveProperty: " + (pass ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardWithNullUnit() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, null);
    }

    private static void testEquality_YardSameReference() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        System.out.println("testEquality_YardSameReference: " + (q1.equals(q1) ? "PASS" : "FAIL"));
    }

    private static void testEquality_YardNullComparison() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "YARDS");
        System.out.println("testEquality_YardNullComparison: " + (!q1.equals(null) ? "PASS" : "FAIL"));
    }

    private static void testEquality_CentimetersWithNullUnit() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, null);
    }

    private static void testEquality_CentimetersSameReference() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "CENTIMETERS");
        System.out.println("testEquality_CentimetersSameReference: " + (q1.equals(q1) ? "PASS" : "FAIL"));
    }

    private static void testEquality_CentimetersNullComparison() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(1.0, "CENTIMETERS");
        System.out.println("testEquality_CentimetersNullComparison: " + (!q1.equals(null) ? "PASS" : "FAIL"));
    }

    private static void testEquality_AllUnits_ComplexScenario() {
        uc4_QuantityMeasurementApp q1 = new uc4_QuantityMeasurementApp(2.0, "YARDS");
        uc4_QuantityMeasurementApp q2 = new uc4_QuantityMeasurementApp(6.0, "FEET");
        uc4_QuantityMeasurementApp q3 = new uc4_QuantityMeasurementApp(72.0, "INCHES");
        boolean pass = q1.equals(q2) && q2.equals(q3) && q1.equals(q3);
        System.out.println("testEquality_AllUnits_ComplexScenario: " + (pass ? "PASS" : "FAIL"));
    }
}
