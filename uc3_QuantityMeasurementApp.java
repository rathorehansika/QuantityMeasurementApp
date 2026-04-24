public class uc3_QuantityMeasurementApp {
    private final double value;
    private final String unit; // Using String instead of Enum to guarantee 1 class file

    public uc3_QuantityMeasurementApp(double value, String unit) {
        if (unit == null || (!unit.equals("FEET") && !unit.equals("INCHES"))) {
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
        return 1.0;
    }

    private double getBaseValue() {
        return value * getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        uc3_QuantityMeasurementApp that = (uc3_QuantityMeasurementApp) obj;
        return Math.abs(this.getBaseValue() - that.getBaseValue()) < 0.00001;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", \"" + unit.toLowerCase() + "\")";
    }

    public static void main(String[] args) {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(12.0, "INCHES");
        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        uc3_QuantityMeasurementApp q3 = new uc3_QuantityMeasurementApp(1.0, "INCHES");
        uc3_QuantityMeasurementApp q4 = new uc3_QuantityMeasurementApp(1.0, "INCHES");
        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        System.out.println("\nRunning Tests...");
        testEquality_FeetToFeet_SameValue();
        testEquality_InchToInch_SameValue();
        testEquality_FeetToInches_EquivalentValue();
        testEquality_InchToFeet_EquivalentValue();
        testEquality_FeetToFeet_DifferentValue();
        testEquality_InchToInch_DifferentValue();
        testEquality_SameReference();
        testEquality_NullComparison();
        try {
            testEquality_NullUnit();
        } catch (IllegalArgumentException e) {
            System.out.println("testEquality_NullUnit: PASS (Exception thrown)");
        }
    }

    private static void testEquality_FeetToFeet_SameValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        System.out.println("testEquality_FeetToFeet_SameValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_InchToInch_SameValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "INCHES");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(1.0, "INCHES");
        System.out.println("testEquality_InchToInch_SameValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_FeetToInches_EquivalentValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(12.0, "INCHES");
        System.out.println("testEquality_FeetToInches_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_InchToFeet_EquivalentValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(12.0, "INCHES");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        System.out.println("testEquality_InchToFeet_EquivalentValue: " + (q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_FeetToFeet_DifferentValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(2.0, "FEET");
        System.out.println("testEquality_FeetToFeet_DifferentValue: " + (!q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_InchToInch_DifferentValue() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "INCHES");
        uc3_QuantityMeasurementApp q2 = new uc3_QuantityMeasurementApp(2.0, "INCHES");
        System.out.println("testEquality_InchToInch_DifferentValue: " + (!q1.equals(q2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_NullUnit() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, null);
    }

    private static void testEquality_SameReference() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        System.out.println("testEquality_SameReference: " + (q1.equals(q1) ? "PASS" : "FAIL"));
    }

    private static void testEquality_NullComparison() {
        uc3_QuantityMeasurementApp q1 = new uc3_QuantityMeasurementApp(1.0, "FEET");
        System.out.println("testEquality_NullComparison: " + (!q1.equals(null) ? "PASS" : "FAIL"));
    }
}
