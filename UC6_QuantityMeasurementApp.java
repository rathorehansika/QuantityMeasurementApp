import java.util.Objects;

/**
 * UC6: Addition of Two Length Units (Same Category)
 */

// --- 1. THE ENUM ---
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

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
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = Objects.requireNonNull(unit);
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }

    /**
     * Logic for UC6 Addition
     */
    public Length add(Length other) {
        Objects.requireNonNull(other);
        // Normalize both to base unit (FEET)
        double thisInBase = this.value * this.unit.getConversionFactor();
        double otherInBase = other.value * other.unit.getConversionFactor();

        // Sum and convert back to the unit of the FIRST operand
        double sumInBase = thisInBase + otherInBase;
        double resultValue = sumInBase / this.unit.getConversionFactor();

        return new Length(resultValue, this.unit);
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
    public String toString() {
        return value + " " + unit;
    }
}

// --- 3. THE MAIN APP CLASS ---
public class UC6_QuantityMeasurementApp {

    /**
     * Requirement: Returns Length object to satisfy assertTrue in UC6 tests
     */
    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        Length sum = l1.add(l2);
        System.out.println("Input: add(" + l1 + ", " + l2 + ") -> Output: " + sum);
        return sum;
    }

    public static double demonstrateLengthConversion(double value, LengthUnit source, LengthUnit target) {
        double valueInBase = value * source.getConversionFactor();
        double convertedValue = valueInBase / target.getConversionFactor();
        System.out.println("Input: convert(" + value + ", " + source + ", " + target + ") -> Output: " + convertedValue);
        return convertedValue;
    }

    public static double demonstrateLengthConversion(Length sourceLength, LengthUnit target) {
        return demonstrateLengthConversion(sourceLength.getValue(), sourceLength.getUnit(), target);
    }

    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        boolean result = length1.equals(length2);
        System.out.println(result ? "The two length measurements are equal."
                                  : "The two length measurements are not equal.");
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Running UC6 Demonstrations...");

        // 1 Foot + 12 Inches = 2 Feet
        Length f1 = new Length(1.0, LengthUnit.FEET);
        Length i12 = new Length(12.0, LengthUnit.INCHES);
        demonstrateLengthAddition(f1, i12);

        // 12 Inches + 1 Foot = 24 Inches
        demonstrateLengthAddition(i12, f1);

        // Equality Check for UC5 compliance
        demonstrateLengthEquality(new Length(1.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET));
    }
}