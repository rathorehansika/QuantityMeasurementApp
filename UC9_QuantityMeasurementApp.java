import java.util.Objects;

/**
 * UC9: Multimodal Quantity Measurement System
 * Supporting Length (UC1-UC8) and Weight (UC9) categories.
 */

// --- 1. LENGTH UNIT ENUM ---
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;
    LengthUnit(double factor) { this.factor = factor; }
    public double convertToBase(double val) { return val * factor; }
    public double convertFromBase(double baseVal) { return baseVal / factor; }
}

// --- 2. WEIGHT UNIT ENUM (New for UC9) ---
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592); // Standard: 1 lb ≈ 0.453592 kg

    private final double factor;
    WeightUnit(double factor) { this.factor = factor; }
    public double convertToBase(double val) { return val * factor; }
    public double convertFromBase(double baseVal) { return baseVal / factor; }
}

// --- 3. LENGTH CLASS ---
class Length {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-4;

    public Length(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = Objects.requireNonNull(unit);
    }

    public Length convertTo(LengthUnit target) {
        return new Length(target.convertFromBase(unit.convertToBase(value)), target);
    }

    public Length add(Length other, LengthUnit target) {
        double sum = unit.convertToBase(value) + other.unit.convertToBase(other.value);
        return new Length(target.convertFromBase(sum), target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        return Math.abs(unit.convertToBase(value) - that.unit.convertToBase(that.value)) < EPSILON;
    }

    @Override
    public String toString() { return String.format("%.3f %s", value, unit); }
}

// --- 4. WEIGHT CLASS (Mirroring Length Design) ---
class Weight {
    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-4;

    public Weight(double value, WeightUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = Objects.requireNonNull(unit);
    }

    public Weight convertTo(WeightUnit target) {
        return new Weight(target.convertFromBase(unit.convertToBase(value)), target);
    }

    public Weight add(Weight other, WeightUnit target) {
        double sum = unit.convertToBase(value) + other.unit.convertToBase(other.value);
        return new Weight(target.convertFromBase(sum), target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight that = (Weight) o;
        return Math.abs(unit.convertToBase(value) - that.unit.convertToBase(that.value)) < EPSILON;
    }

    @Override
    public String toString() { return String.format("%.3f %s", value, unit); }
}

// --- 5. MAIN APPLICATION ---
public class UC9_QuantityMeasurementApp {

    public static void main(String[] args) {
        System.out.println("=== UC9: Weight & Length Measurement System ===");

        // Weight Equality
        Weight kg1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight g1000 = new Weight(1000.0, WeightUnit.GRAM);
        System.out.println("1 kg equals 1000 g: " + kg1.equals(g1000));

        // Weight Conversion
        Weight lb1 = new Weight(1.0, WeightUnit.POUND);
        System.out.println("1 lb in Grams: " + lb1.convertTo(WeightUnit.GRAM));

        // Weight Addition
        Weight sumKg = kg1.add(g1000, WeightUnit.KILOGRAM);
        System.out.println("1 kg + 1000 g = " + sumKg);

        // Category Incompatibility Check
        Length ft1 = new Length(1.0, LengthUnit.FEET);
        System.out.println("Does 1 kg equal 1 foot? " + kg1.equals(ft1));
    }

    // Static helpers for JUnit compatibility
    public static boolean demonstrateWeightEquality(Weight w1, Weight w2) { return w1.equals(w2); }
    public static Weight demonstrateWeightAddition(Weight w1, Weight w2, WeightUnit target) { return w1.add(w2, target); }
}