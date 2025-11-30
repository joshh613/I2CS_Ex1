import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * * Introduction to Computer Science 2026, Ariel University,
 * * Ex1: arrays, static functions and JUnit
 * <p>
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
    static final double[] P1 = {2, 0, 3, -1, 0}, P2 = {0.1, 0, 1, 0.1, 3};
    static double[] po1 = {2, 2}, po2 = {-3, 0.61, 0.2};
    static double[] po3 = {2, 1, -0.7, -0.02, 0.02};
    static double[] po4 = {-3, 0.61, 0.2};

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testF() {
        double fx0 = Ex1.f(po1, 0);
        double fx1 = Ex1.f(po1, 1);
        double fx2 = Ex1.f(po1, 2);
        assertEquals(fx0, 2, Ex1.EPS);
        assertEquals(fx1, 4, Ex1.EPS);
        assertEquals(fx2, 6, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1(x) + p2(x) == (p1+p2)(x)
     */
    void testF2() {
        double x = Math.PI;
        double[] po12 = Ex1.add(po1, po2);
        double f1x = Ex1.f(po1, x);
        double f2x = Ex1.f(po2, x);
        double f12x = Ex1.f(po12, x);
        assertEquals(f1x + f2x, f12x, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testAdd() {
        double[] p12 = Ex1.add(po1, po2);
        double[] minus1 = {-1};
        double[] pp2 = Ex1.mul(po2, minus1);
        double[] p1 = Ex1.add(p12, pp2);
        assertTrue(Ex1.equals(p1, po1));
    }

    @Test
    /**
     * Tests that p1+p2 == p2+p1
     */
    void testAdd2() {
        double[] p12 = Ex1.add(po1, po2);
        double[] p21 = Ex1.add(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }

    @Test
    /**
     * Tests that p1+0 == p1
     */
    void testAdd3() {
        double[] p1 = Ex1.add(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, po1));
    }

    @Test
    /**
     * Tests that p1*0 == 0
     */
    void testMul1() {
        double[] p1 = Ex1.mul(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, Ex1.ZERO));
    }

    @Test
    /**
     * Tests that p1*p2 == p2*p1
     */
    void testMul2() {
        double[] p12 = Ex1.mul(po1, po2);
        double[] p21 = Ex1.mul(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }

    @Test
    /**
     * Tests that p1(x) * p2(x) = (p1*p2)(x),
     */
    void testMulDoubleArrayDoubleArray() {
        double[] xx = {0, 1, 2, 3, 4.1, -15.2222};
        double[] p12 = Ex1.mul(po1, po2);
        for (int i = 0; i < xx.length; i = i + 1) {
            double x = xx[i];
            double f1x = Ex1.f(po1, x);
            double f2x = Ex1.f(po2, x);
            double f12x = Ex1.f(p12, x);
            assertEquals(f12x, f1x * f2x, Ex1.EPS);
        }
    }

    @Test
    /**
     * Tests a simple derivative examples - till ZERO.
     */
    void testDerivativeArrayDoubleArray() {
        double[] p = {1, 2, 3}; // 3X^2+2x+1
        double[] pt = {2, 6}; // 6x+2
        double[] dp1 = Ex1.derivative(p); // 2x + 6
        double[] dp2 = Ex1.derivative(dp1); // 2
        double[] dp3 = Ex1.derivative(dp2); // 0
        double[] dp4 = Ex1.derivative(dp3); // 0
        assertTrue(Ex1.equals(dp1, pt));
        assertTrue(Ex1.equals(Ex1.ZERO, dp3));
        assertTrue(Ex1.equals(dp4, dp3));
    }

    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */ public void testFromString() {
        double[] p = {-1.1, 2.3, 3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if (!isSame1) {
            fail();
        }
        if (!isSame2) {
            fail();
        }
        assertEquals(sp, Ex1.poly(p1));
    }

    @Test
    /**
     * Tests the equality of pairs of arrays.
     */ public void testEquals() {
        double[][] d1 = {{0}, {1}, {1, 2, 0, 0}};
        double[][] d2 = {Ex1.ZERO, {1 + Ex1.EPS / 2}, {1, 2}};
        double[][] xx = {{-2 * Ex1.EPS}, {1 + Ex1.EPS * 1.2}, {1, 2, Ex1.EPS / 2}};
        for (int i = 0; i < d1.length; i = i + 1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for (int i = 0; i < d1.length; i = i + 1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */ public void testSameValue2() {
        double x1 = -4, x2 = 0;
        double rs1 = Ex1.sameValue(po1, po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2, po1, x1, x2, Ex1.EPS);
        assertEquals(rs1, rs2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function - it should be symmetric.
     */ public void testArea() {
        double x1 = -4, x2 = 0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1, a2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */ public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0, 1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a, po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a, po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a, po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a, po_b, x1, x2, 100);
        double area = 2.5;
        assertEquals(a1, area, Ex1.EPS);
        assertEquals(a2, area, Ex1.EPS);
        assertEquals(a3, area, Ex1.EPS);
        assertEquals(a100, area, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function.
     */ public void testArea3() {
        double[] po_a = {2, 1, -0.7, -0.02, 0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a, po_b, -10, -5, Ex1.EPS);
        double a1 = Ex1.area(po_a, po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(a1, area, Ex1.EPS);
    }

    //MY TESTS

    @Test
    void f() {
        double[] poly = {0, 0, 1};
        double x = 4;
        double ans = Ex1.f(poly, x);
        assertEquals(x * x, ans, Ex1.EPS);

        poly = new double[]{3, 5, 0};
        x = 2;
        ans = Ex1.f(poly, x);
        assertEquals(3 + x * poly[1], ans, Ex1.EPS);
    }

    @Test
    void root_rec() {
        double[] poly = {0, 0, 1};
        double x1 = -4;
        double x2 = 4;
        double ans = Ex1.root_rec(poly, x1, x2, Ex1.EPS);
        assertEquals(0, ans, Ex1.EPS);

        poly = new double[]{-8, 0, 2};
        x1 = 1;
        x2 = 10;
        ans = Ex1.root_rec(poly, x1, x2, Ex1.EPS);
        assertEquals(2, ans, Ex1.EPS);
    }

    @Test
    void polynomFromPoints() {
        double[] xx = {1, 2, 3};
        double[] yy = {1, 4, 9};
        double[] expected = {0, 0, 1};
        double[] ans = Ex1.PolynomFromPoints(xx, yy);
        assertArrayEquals(expected, ans, Ex1.EPS);

        xx = new double[]{0, 1};
        yy = new double[]{1, 6};
        expected = new double[]{1, 5};
        ans = Ex1.PolynomFromPoints(xx, yy);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void equals() {
        double[] p1 = {1, 2, 3};
        double[] p2 = {4, 5, 6};
        boolean ans = Ex1.equals(p1, p2);
        assertFalse(ans);

        p1 = new double[]{1, 1, 1};
        p2 = new double[]{0, 0};
        ans = Ex1.equals(p1, p2);
        assertFalse(ans);

        p1 = new double[]{2, 3, 2};
        p2 = new double[]{2, 3, 2};
        ans = Ex1.equals(p1, p2);
        assertTrue(ans);

        p1 = new double[]{3, 2, 1};
        p2 = new double[]{6, 4, 2};
        ans = Ex1.equals(p1, p2);
        assertFalse(ans);
    }

    @Test
    void poly() {
        double[] p = {2, 0, 3.1, -1.2};
        String expected = "-1.2x^3 +3.1x^2 +2.0";
        String ans = Ex1.poly(p);
        assertEquals(expected, ans);

        p = new double[]{3, 2, 1};
        expected = "x^2 +2.0x +3.0";
        ans = Ex1.poly(p);
        assertEquals(expected, ans);
    }

    @Test
    void sameValue() {
        double[] p1 = {0, 1};
        double[] p2 = {0, 0, 1};
        double x1 = -1;
        double x2 = 1;
        double expected = 0;
        double ans = Ex1.sameValue(p1, p2, x1, x2, Ex1.EPS);
        assertEquals(expected, ans, Ex1.EPS);

        p1 = new double[]{1, 0, 1};
        p2 = new double[]{0, -1, 1};
        x1 = -5;
        x2 = 0;
        expected = -1;
        ans = Ex1.sameValue(p1, p2, x1, x2, Ex1.EPS);
        assertEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void length() {
        double[] p = {0, 1};
        double x1 = 0;
        double x2 = 1;
        int segments = 1;
        double expected = Math.sqrt(2);
        double ans = Ex1.length(p, x1, x2, segments);
        assertEquals(expected, ans, Ex1.EPS);

        p = new double[]{1, 0, 1};
        x1 = 1;
        x2 = 3;
        segments = 2;
        expected = Math.sqrt(10) + Math.sqrt(26);
        ans = Ex1.length(p, x1, x2, segments);
        assertEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void area() {
        double[] p1 = {1};
        double[] p2 = Ex1.ZERO;
        double x1 = 0;
        double x2 = 5;
        int segments = 10;
        double expected = 5;
        double ans = Ex1.area(p1, p2, x1, x2, segments);
        assertEquals(expected, ans, Ex1.EPS);

        p1 = new double[]{0, 0, 1};
        p2 = new double[]{0, 0, -1};
        x1 = -2;
        x2 = 2;
        segments = 1_000;
        expected = 1.0 / 3 * (4 * 4) * 2;
        ans = Ex1.area(p1, p2, x1, x2, segments);
        assertEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void getPolynomFromString() {
        String p = "-1.0x^2 +3.0x +2.0";
        double[] expected = {2, 3, -1};
        double[] ans = Ex1.getPolynomFromString(p);
        assertArrayEquals(expected, ans, Ex1.EPS);

        p = "3x^3 -x";
        expected = new double[]{0, -1, 0, 3};
        ans = Ex1.getPolynomFromString(p);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void add() {
        double[] p1 = {1, 2, 3};
        double[] p2 = {4, 5, 6};
        double[] expected = {5, 7, 9};
        double[] ans = Ex1.add(p1, p2);
        assertArrayEquals(expected, ans, Ex1.EPS);

        p1 = Ex1.ZERO;
        p2 = Ex1.ZERO;
        expected = Ex1.ZERO;
        ans = Ex1.add(p1, p2);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void mul() {
        double[] p1 = {0, 1};
        double[] p2 = {1, 2, 3};
        double[] expected = {0, 1, 2, 3};
        double[] ans = Ex1.mul(p1, p2);
        assertArrayEquals(expected, ans, Ex1.EPS);

        p1 = Ex1.ZERO;
        p2 = new double[]{1, 1, 1};
        expected = Ex1.ZERO;
        ans = Ex1.mul(p1, p2);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void derivative() {
        double[] p = {1, 1, 1, 1};
        double[] expected = {1, 2, 3};
        double[] ans = Ex1.derivative(p);
        assertArrayEquals(expected, ans, Ex1.EPS);

        p = Ex1.ZERO;
        expected = Ex1.ZERO;
        ans = Ex1.derivative(p);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void neg() {
        double[] p = {1, 2, 3};
        double[] expected = {-1, -2, -3};
        double[] ans = Ex1.neg(p);
        assertArrayEquals(expected, ans, Ex1.EPS);
    }

    @Test
    void ensureDigit() {
        String s = "+3.5x";
        String expected = "+3.5x";
        String ans = Ex1.ensureDigit(s);
        assertEquals(expected, ans);

        s = "+x^2";
        expected = "+1.0x^2";
        ans = Ex1.ensureDigit(s);
        assertEquals(expected, ans);
    }

    @Test
    void lastCharIs() {
        String s = "abcde";
        char c = 'e';
        boolean ans = Ex1.lastCharIs(s, c);
        assertTrue(ans);

        s = "test";
        c = 'x';
        ans = Ex1.lastCharIs(s, c);
        assertFalse(ans);
    }

    @Test
    void dropLastChar() {
        String s = "hello";
        String expected = "hell";
        String ans = Ex1.dropLastChar(s);
        assertEquals(expected, ans);

        s = "1234";
        expected = "123";
        ans = Ex1.dropLastChar(s);
        assertEquals(expected, ans);
    }

    @Test
    void changeOfSign() {
        double[] p = {0, 1};
        double x1 = -2;
        double x2 = 2;
        boolean ans = Ex1.changeOfSign(p, x1, x2);
        assertTrue(ans);

        p = new double[]{0, 0, 1};
        x1 = 1;
        x2 = 3;
        ans = Ex1.changeOfSign(p, x1, x2);
        assertFalse(ans);
    }

    @Test
    void calcArea() {
        double[] p = {0, 1};
        double x1 = 0;
        double x2 = 2;
        double expected = 2;
        double ans = Ex1.calcArea(p, x1, x2, x2 - x1);
        assertEquals(expected, ans, Ex1.EPS);

        p = new double[]{1, 1};
        x1 = -5;
        x2 = 5;
        expected = 8 + 18;
        ans = Ex1.calcArea(p, x1, x2, x2 - x1);
        assertEquals(expected, ans, Ex1.EPS);

        p = new double[]{1, 2};
        x1 = 0;
        x2 = 10;
        expected = (1 + 21) * 5;
        ans = Ex1.calcArea(p, x1, x2, x2 - x1);
        assertEquals(expected, ans, Ex1.EPS);
    }

    //EDGE CASES

    @Test
    void f_edgeCases() {
        //ZERO
        assertEquals(0.0, Ex1.f(Ex1.ZERO, 10), Ex1.EPS);
        assertEquals(0.0, Ex1.f(Ex1.ZERO, -3.5), Ex1.EPS);

        //Higher degrees
        double[] p = {1, -1, 1, -1, 1};
        double x = 2;
        double expected = 1 - 2 + 4 - 8 + 16;
        assertEquals(expected, Ex1.f(p, x), Ex1.EPS);
    }

    @Test
    void root_rec_edgeCases() {
        //At endpoint
        double[] p = {-4, 1};
        double ans = Ex1.root_rec(p, 0, 4, Ex1.EPS);
        assertEquals(4.0, ans, Ex1.EPS);

        //Other side
        ans = Ex1.root_rec(p, 4, 10, Ex1.EPS);
        assertEquals(4.0, ans, Ex1.EPS);

        //Two roots
        p = new double[]{-4, 0, 1}; //roots at -2 and 2
        ans = Ex1.root_rec(p, -3, 3, Ex1.EPS);
        double fAtAns = Ex1.f(p, ans);
        assertTrue(Math.abs(fAtAns) < Ex1.EPS);

        //Linear
        p = new double[]{2, -3}; //root at -3x + 2 = 0
        ans = Ex1.root_rec(p, 0, 1, Ex1.EPS);
        assertEquals(2.0 / 3.0, ans, Ex1.EPS);
    }

    @Test
    void polynomFromPoints_edgeCases() {
        //null
        assertNull(Ex1.PolynomFromPoints(null, new double[]{1, 2}));
        assertNull(Ex1.PolynomFromPoints(new double[]{1, 2}, null));

        //Length mismatch
        assertNull(Ex1.PolynomFromPoints(new double[]{1, 2}, new double[]{1}));

        //Three points of linear function (y = 2x + 1)
        double[] xx = {0, 1, 2};
        double[] yy = {1, 3, 5};
        double[] expected = {1, 2, 0};
        double[] ans = Ex1.PolynomFromPoints(xx, yy);
        assertArrayEquals(expected, ans, Ex1.EPS);

        //More than three points
        xx = new double[]{0, 1, 2, 3};
        yy = new double[]{1, 2, 3, 4};
        assertNull(Ex1.PolynomFromPoints(xx, yy));
    }

    @Test
    void equals_edgeCases() {
        //Trailing zeros
        double[] p1 = {1, 2, 3, 0, 0};
        double[] p2 = {1, 2, 3};
        assertTrue(Ex1.equals(p1, p2));

        //null
        assertTrue(Ex1.equals(null, null));
        assertFalse(Ex1.equals(null, new double[]{0}));
        assertFalse(Ex1.equals(new double[]{0}, null));
    }

    @Test
    void poly_edgeCases() {
        //ZERO
        assertEquals("0", Ex1.poly(Ex1.ZERO));

        //Higher degree zero
        double[] p = {0, 0, 0};
        assertEquals("0", Ex1.poly(p));

        //Constant positive/negative
        p = new double[]{5};
        assertEquals("5.0", Ex1.poly(p));
        p = new double[]{-2};
        assertEquals("-2.0", Ex1.poly(p));

        //Single x term
        p = new double[]{0, 3};
        assertEquals("3.0x", Ex1.poly(p));

        //Single x^k term
        p = new double[]{0, 0, -4};
        assertEquals("-4.0x^2", Ex1.poly(p));
    }

    @Test
    void sameValue_edgeCases() {
        //Identical polynomials
        double[] p1 = {1, 2, 3};
        double[] p2 = {1, 2, 3};
        double x1 = -10;
        double x2 = 10;
        double ans = Ex1.sameValue(p1, p2, x1, x2, Ex1.EPS);
        assertTrue(Double.isFinite(ans)); //some answer was reached

        //null
        assertEquals(-1.0, Ex1.sameValue(null, p2, 0, 1, Ex1.EPS));
        assertEquals(-1.0, Ex1.sameValue(p1, null, 0, 1, Ex1.EPS));
    }

    @Test
    void length_edgeCases() {
        //ZERO
        double[] p = Ex1.ZERO;
        assertEquals(5.0, Ex1.length(p, 0, 5, 10), Ex1.EPS);
        assertEquals(2.0, Ex1.length(p, -1, 1, 4), Ex1.EPS);

        //Linear function
        p = new double[]{0, 2};
        double length1 = Ex1.length(p, 0, 1, 1);
        double length10 = Ex1.length(p, 0, 1, 10);
        assertEquals(Math.sqrt(5), length1, Ex1.EPS);
        assertEquals(Math.sqrt(5), length10, Ex1.EPS);

        //Small interval
        p = new double[]{1, 1, 1};
        double ans = Ex1.length(p, 1.0, 1.001, 10);
        assertTrue(ans > 0);
    }

    @Test
    void area_edgeCases() {
        //Same polynomial
        double[] p1 = {1, -2, 3};
        assertEquals(0.0, Ex1.area(p1, p1, -5, 5, 100), Ex1.EPS);

        //Symmetry
        double[] p2 = {0, 1};
        double a1 = Ex1.area(p1, p2, -2, 3, 100);
        double a2 = Ex1.area(p2, p1, -2, 3, 100);
        assertEquals(a1, a2, Ex1.EPS);

        //Multiple intersections
        p1 = new double[]{0, 0, 0, 1};
        p2 = Ex1.ZERO;
        double area = Ex1.area(p1, p2, -2, 2, 1000);
        assertTrue(area > 0);
    }

    @Test
    void getPolynomFromString_edgeCases() {
        //constant
        String s = "5.0";
        double[] expected = {5.0};
        assertArrayEquals(expected, Ex1.getPolynomFromString(s), Ex1.EPS);

        //single x, implicit +
        s = "3x";
        expected = new double[]{0, 3};
        assertArrayEquals(expected, Ex1.getPolynomFromString(s), Ex1.EPS);

        //single x, negative
        s = "-x";
        expected = new double[]{0, -1};
        assertArrayEquals(expected, Ex1.getPolynomFromString(s), Ex1.EPS);

        //leading +
        s = "+2.0x^2 -3.0x +1.0";
        expected = new double[]{1, -3, 2};
        assertArrayEquals(expected, Ex1.getPolynomFromString(s), Ex1.EPS);
    }

    @Test
    void add_edgeCases() {
        //different lengths
        double[] p1 = {1, 2};
        double[] p2 = {3, 4, 5};
        double[] expected = {4, 6, 5};
        assertArrayEquals(expected, Ex1.add(p1, p2), Ex1.EPS);

        //null
        assertNull(Ex1.add(null, p2));
        assertNull(Ex1.add(p1, null));
    }

    @Test
    void mul_edgeCases() {
        //constant
        double[] p1 = {2};
        double[] p2 = {1, 1, 1};
        double[] expected = {2, 2, 2};
        assertArrayEquals(expected, Ex1.mul(p1, p2), Ex1.EPS);

        //different lengths
        p1 = new double[]{1, 0, -1};
        p2 = new double[]{1, 2};
        expected = new double[]{1, 2, -1, -2};
        assertArrayEquals(expected, Ex1.mul(p1, p2), Ex1.EPS);

        //null
        assertNull(Ex1.mul(null, p2));
        assertNull(Ex1.mul(p1, null));
        assertNull(Ex1.mul(new double[]{}, new double[]{1}));
    }

    @Test
    void derivative_edgeCases() {
        //null
        assertNull(Ex1.derivative(null));
        assertNull(Ex1.derivative(new double[]{}));

        //zero coeff
        double[] p = {1, 0, 2, 0, 3};
        double[] expected = {0, 4, 0, 12};
        assertArrayEquals(expected, Ex1.derivative(p), Ex1.EPS);
    }
}