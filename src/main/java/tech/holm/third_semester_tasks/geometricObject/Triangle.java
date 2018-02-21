package tech.holm.red01_wordCounter.geometricObject;

public class Triangle extends GeometricObject {

    private Double side1;
    private Double side2;
    private Double side3;

    public Triangle(String color, boolean filled, Double side1, Double side2, Double side3) {
        super(color, filled);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public double getArea() {

        double halfPerimiter = getPerimiter()/2;

        double area = Math.sqrt(halfPerimiter*(halfPerimiter-side1)*(halfPerimiter-side2)*(halfPerimiter-side3));

        return area;
    }

    @Override
    public double getPerimiter() {

        return side1 + side2 + side3;
    }
}
