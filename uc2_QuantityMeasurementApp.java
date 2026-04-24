public class uc2_QuantityMeasurementApp {
    private final double value;
    private final boolean isInches; // Used to simulate a separate class conceptually

    // Constructor representing Feet
    public static uc2_QuantityMeasurementApp createFeet(double value) {
        return new uc2_QuantityMeasurementApp(value, false);
    }

    // Constructor representing Inches
    public static uc2_QuantityMeasurementApp createInches(double value) {
        return new uc2_QuantityMeasurementApp(value, true);
    }

    private uc2_QuantityMeasurementApp(double value, boolean isInches) {
        this.value = value;
        this.isInches = isInches;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        uc2_QuantityMeasurementApp other = (uc2_QuantityMeasurementApp) obj;
        // Strictly matching unit conceptual type, simulating two distinct classes
        if (this.isInches != other.isInches) return false;
        return Double.compare(other.value, value) == 0;
    }

    public static void main(String[] args) {
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        uc2_QuantityMeasurementApp i2 = uc2_QuantityMeasurementApp.createInches(1.0);
        System.out.println("Input: " + i1.getValue() + " inch and " + i2.getValue() + " inch");
        System.out.println("Output: Equal (" + i1.equals(i2) + ")");

        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp f2 = uc2_QuantityMeasurementApp.createFeet(1.0);
        System.out.println("Input: " + f1.getValue() + " ft and " + f2.getValue() + " ft");
        System.out.println("Output: Equal (" + f1.equals(f2) + ")");

        System.out.println("\nRunning Tests...");
        testEquality_SameValue();
        testEquality_DifferentValue();
        testEquality_NullComparison();
        testEquality_NonNumericInput();
        testEquality_SameReference();
    }

    private static void testEquality_SameValue() {
        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp f2 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        uc2_QuantityMeasurementApp i2 = uc2_QuantityMeasurementApp.createInches(1.0);
        boolean pass = f1.equals(f2) && i1.equals(i2);
        System.out.println("testEquality_SameValue: " + (pass ? "PASS" : "FAIL"));
    }

    private static void testEquality_DifferentValue() {
        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp f2 = uc2_QuantityMeasurementApp.createFeet(2.0);
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        uc2_QuantityMeasurementApp i2 = uc2_QuantityMeasurementApp.createInches(2.0);
        boolean pass = !f1.equals(f2) && !i1.equals(i2);
        System.out.println("testEquality_DifferentValue: " + (pass ? "PASS" : "FAIL"));
    }

    private static void testEquality_NullComparison() {
        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        boolean pass = !f1.equals(null) && !i1.equals(null);
        System.out.println("testEquality_NullComparison: " + (pass ? "PASS" : "FAIL"));
    }

    private static void testEquality_NonNumericInput() {
        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        boolean pass = !f1.equals("1.0 ft") && !i1.equals("1.0 inch");
        System.out.println("testEquality_NonNumericInput: " + (pass ? "PASS" : "FAIL"));
    }

    private static void testEquality_SameReference() {
        uc2_QuantityMeasurementApp f1 = uc2_QuantityMeasurementApp.createFeet(1.0);
        uc2_QuantityMeasurementApp i1 = uc2_QuantityMeasurementApp.createInches(1.0);
        boolean pass = f1.equals(f1) && i1.equals(i1);
        System.out.println("testEquality_SameReference: " + (pass ? "PASS" : "FAIL"));
    }
}
