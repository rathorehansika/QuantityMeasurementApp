import java.util.Objects;

/**
 * UC5: Unit-to-Unit Conversion System
 * Updated to return 'double' to satisfy JUnit Assertions while keeping console output.
 */

// --- 1. THE ENUM ---
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48); // 1 foot = 30.48 cm

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}

// --- 2. THE LENGTH CLASS ---
class Length {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-4;

    public Length(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        this.value = value;
        this.unit = Objects.requireNonNull(unit, "Unit cannot be null.");
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        double thisInBase = this.value * this.unit.getConversionFactor();
        double thatInBase = that.value * that.unit.getConversionFactor();
        return Math.abs(thisInBase - thatInBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value * unit.getConversionFactor());
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

// --- 3. THE MAIN APP CLASS ---
public class QuantityMeasurementApp {

    /**
     * Matches terminal output requirement
     */
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        boolean result = length1.equals(length2);
        if (result) {
            System.out.println("The two length measurements are equal.");
        } else {
            System.out.println("The two length measurements are not equal.");
        }
        return result;
    }

    /**
     * UC5 REQUIREMENT: Returns double to satisfy JUnit tests
     */
    public static double demonstrateLengthConversion(double value, LengthUnit source, LengthUnit target) {
        double valueInBase = value * source.getConversionFactor();
        double convertedValue = valueInBase / target.getConversionFactor();

        // Print for terminal visibility
        System.out.printf("%.1f %s to %s: %.4f %s%n", value, source, target, convertedValue, target);

        return convertedValue;
    }

    /**
     * Overloaded version for existing Length objects
     */
    public static double demonstrateLengthConversion(Length sourceLength, LengthUnit target) {
        return demonstrateLengthConversion(sourceLength.getValue(), sourceLength.getUnit(), target);
    }

    public static void main(String[] args) {
        System.out.println("Running UC5 Demonstrations...");

        // Test 1: Feet to Inches
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);

        // Test 2: Yards to Inches
        Length yardObj = new Length(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(yardObj, LengthUnit.INCHES);

        // Test 3: Equality
        demonstrateLengthEquality(new Length(1.0, LengthUnit.YARDS), new Length(36.0, LengthUnit.INCHES));
    }
}
