import java.util.Objects;

/**
 * consolidated Quantity Measurement System (UC3 - UC7)
 * Implements Unit Conversion, Equality, and Addition with Target Unit Specification.
 */

// --- 1. THE ENUM ---
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48); // Standard: 1 foot = 30.48 cm

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}

// --- 2. THE VALUE OBJECT ---
class Length {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-4;

    public Length(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
        this.value = value;
        this.unit = Objects.requireNonNull(unit, "Unit cannot be null.");
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }

    /**
     * UC6: Addition defaulting to the unit of the first operand
     */
    public Length add(Length other) {
        return add(other, this.unit);
    }

    /**
     * UC7: Addition with Explicit Target Unit Specification
     */
    public Length add(Length other, LengthUnit targetUnit) {
        Objects.requireNonNull(other, "Operand cannot be null.");
        Objects.requireNonNull(targetUnit, "Target unit cannot be null.");

        // Normalize both to base unit (FEET)
        double firstInBase = this.value * this.unit.getConversionFactor();
        double secondInBase = other.value * other.unit.getConversionFactor();

        // Sum and convert to target
        double sumInBase = firstInBase + secondInBase;
        double finalValue = sumInBase / targetUnit.getConversionFactor();

        return new Length(finalValue, targetUnit);
    }

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
        return String.format("%.3f %s", value, unit);
    }
}

// --- 3. THE MAIN APPLICATION ---
public class UC7_QuantityMeasurementApp {

    /**
     * UC7: Static API for Addition with Target Unit
     */
    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit target) {
        Length result = l1.add(l2, target);
        System.out.println("Input: add(" + l1 + ", " + l2 + ", Target: " + target + ") -> Output: " + result);
        return result;
    }

    /**
     * UC6: Static API for Addition (Defaulting to first operand unit)
     */
    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        Length result = l1.add(l2);
        System.out.println("Input: add(" + l1 + ", " + l2 + ") -> Output: " + result);
        return result;
    }

    /**
     * UC5: Static API for Unit Conversion
     */
    public static double demonstrateLengthConversion(double value, LengthUnit source, LengthUnit target) {
        double result = (value * source.getConversionFactor()) / target.getConversionFactor();
        System.out.println("Input: convert(" + value + ", " + source + ", " + target + ") -> Output: " + result);
        return result;
    }

    /**
     * UC4: Static API for Equality Check
     */
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        boolean isEqual = l1.equals(l2);
        System.out.println(isEqual ? "The measurements are equal." : "The measurements are NOT equal.");
        return isEqual;
    }

    public static void main(String[] args) {
        System.out.println("=== Running UC7 Demonstrations ===");

        Length oneFoot = new Length(1.0, LengthUnit.FEET);
        Length twelveInches = new Length(12.0, LengthUnit.INCHES);

        // Test 1: Addition to Feet (UC7)
        demonstrateLengthAddition(oneFoot, twelveInches, LengthUnit.FEET);

        // Test 2: Addition to Yards (UC7)
        demonstrateLengthAddition(oneFoot, twelveInches, LengthUnit.YARDS);

        // Test 3: Addition to Inches (UC7)
        demonstrateLengthAddition(oneFoot, twelveInches, LengthUnit.INCHES);

        // Test 4: Basic Equality (UC4)
        demonstrateLengthEquality(new Length(1.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET));
    }
}