public class uc1_QuantityMeasurementApp {
    private final double value;

    public uc1_QuantityMeasurementApp(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        uc1_QuantityMeasurementApp feet = (uc1_QuantityMeasurementApp) obj;
        return Double.compare(feet.value, value) == 0;
    }

    public static void main(String[] args) {
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        uc1_QuantityMeasurementApp f2 = new uc1_QuantityMeasurementApp(1.0);
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
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        uc1_QuantityMeasurementApp f2 = new uc1_QuantityMeasurementApp(1.0);
        System.out.println("testEquality_SameValue: " + (f1.equals(f2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_DifferentValue() {
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        uc1_QuantityMeasurementApp f2 = new uc1_QuantityMeasurementApp(2.0);
        System.out.println("testEquality_DifferentValue: " + (!f1.equals(f2) ? "PASS" : "FAIL"));
    }

    private static void testEquality_NullComparison() {
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        System.out.println("testEquality_NullComparison: " + (!f1.equals(null) ? "PASS" : "FAIL"));
    }

    private static void testEquality_NonNumericInput() {
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        System.out.println("testEquality_NonNumericInput: " + (!f1.equals("1.0 ft") ? "PASS" : "FAIL"));
    }

    private static void testEquality_SameReference() {
        uc1_QuantityMeasurementApp f1 = new uc1_QuantityMeasurementApp(1.0);
        System.out.println("testEquality_SameReference: " + (f1.equals(f1) ? "PASS" : "FAIL"));
    }
}
