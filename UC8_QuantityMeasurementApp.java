import java.util.Objects;

/**
 * UC8: Refactored Design
 * Standalone Enum for Conversion Responsibility and simplified Quantity class.
 */

// --- 1. STANDALONE ENUM (New Responsibility) ---
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Responsibility 1: Convert a value in THIS unit to the base unit (FEET).
     */
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    /**
     * Responsibility 2: Convert a value FROM the base unit (FEET) to THIS unit.
     */
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}

// --- 2. REFACTORED LENGTH CLASS (Delegation Pattern) ---
class Length {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-4;

    public Length(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
        this.unit = Objects.requireNonNull(unit, "Unit cannot be null.");
        this.value = value;
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }

    /**
     * UC5: Delegation to LengthUnit
     */
    public Length convertTo(LengthUnit targetUnit) {
        double baseValue = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new Length(convertedValue, targetUnit);
    }

    /**
     * UC6/UC7: Addition via Base Unit Normalization
     */
    public Length add(Length other, LengthUnit targetUnit) {
        Objects.requireNonNull(other, "Operand cannot be null.");
        Objects.requireNonNull(targetUnit, "Target unit cannot be null.");

        double sumInBase = this.unit.convertToBaseUnit(this.value) +
                other.unit.convertToBaseUnit(other.value);

        return new Length(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    // Default addition (UC6 style)
    public Length add(Length other) {
        return add(other, this.unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;

        // Delegate conversion logic to the unit
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double thatInBase = that.unit.convertToBaseUnit(that.value);

        return Math.abs(thisInBase - thatInBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}

// --- 3. MAIN APP (Maintains Backward Compatibility) ---
public class UC8_QuantityMeasurementApp {

    public static void main(String[] args) {
        System.out.println("=== UC8 Refactored Application ===");

        // Demonstration of unit-level conversion responsibility
        double feetValue = LengthUnit.INCHES.convertToBaseUnit(12.0);
        System.out.println("LengthUnit.INCHES.convertToBaseUnit(12.0) -> " + feetValue + " Feet");

        // Demonstration of refactored Length class
        Length oneFoot = new Length(1.0, LengthUnit.FEET);
        Length twelveInches = new Length(12.0, LengthUnit.INCHES);

        System.out.println("\n--- Arithmetic & Equality ---");
        System.out.println("1 Foot equals 12 Inches: " + oneFoot.equals(twelveInches));

        Length sumInYards = oneFoot.add(twelveInches, LengthUnit.YARDS);
        System.out.println("1 Foot + 12 Inches in Yards: " + sumInYards);
    }

    // API Helpers for test case compatibility
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static Length demonstrateLengthConversion(double val, LengthUnit from, LengthUnit to) {
        return new Length(val, from).convertTo(to);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit target) {
        return l1.add(l2, target);
    }
}