/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * <p>
 * This class represents a set of static methods on a polynomial function - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe
 */
public class Ex1 {
    /**
     * Epsilon value for numerical computation, it serves as a "close enough" threshold.
     */
    public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
    /**
     * The zero polynomial function is represented as an array with a single (0) entry.
     */
    public static final double[] ZERO = {0};

    /**
     * Computes the f(x) value of the polynomial function at x.
     *
     * @param poly - polynomial function
     * @param x    - value at which to evaluate
     * @return f(x) - the polynomial function value at x.
     */
    public static double f(double[] poly, double x) {
        double ans = 0;
        for (int i = 0; i < poly.length; i++) {
            double c = Math.pow(x, i);
            ans += c * poly[i];
        }
        return ans;
    }

    /**
     * Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
     * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, (i.e. finding the root)
     * assuming p(x1)*p(x2) <= 0. (i.e. one is above y=0 and one is below)
     * This function should be implemented recursively.
     *
     * @param p   - the polynomial function
     * @param x1  - minimal value of the range
     * @param x2  - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
     */
    public static double root_rec(double[] p, double x1, double x2, double eps) {
        double f1 = f(p, x1);
        double x12 = (x1 + x2) / 2; //midpoint
        double f12 = f(p, x12);
        if (Math.abs(f12) < eps) {
            return x12;
        }
        if (f12 * f1 <= 0) { //we can search between x1 and midpoint
            return root_rec(p, x1, x12, eps);
        } else { //we search between midpoint and x2
            return root_rec(p, x12, x2, eps);
        }
    }

    /**
     * This function computes a polynomial representation from a set of 2D points on the polynom.
     * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
     * Note: this function only works for a set of points containing up to 3 points, else returns null.
     *
     * @param xx x values of the points
     * @param yy y values of the points
     * @return an array of doubles representing the coefficients of the polynom.
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        if (xx == null || yy == null) {
            return null;
        }

        int lx = xx.length, ly = yy.length;
        if (lx != ly) {
            return null;
        }

        if (lx == 2) {
            double b = (yy[1] - yy[0]) / (xx[1] - xx[0]);
            double a = yy[0] - b * xx[0];
            return new double[]{a, b};
        }

        if (lx == 3) {
            double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]);
            double a = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
            double b = (xx[2] * xx[2] * (yy[0] - yy[1]) + xx[1] * xx[1] * (yy[2] - yy[0]) + xx[0] * xx[0] * (yy[1] - yy[2])) / denom;
            double c = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;

            return new double[]{a, b, c};
        }

        return null;

    }

    /**
     * Two polynomial functions are equal if and only if they have the same values f(x) for n+1 values of x,
     * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     *
     * @param p1 first polynomial function
     * @param p2 second polynomial function
     * @return true iff p1 represents the same polynomial function as p2.
     */
    public static boolean equals(double[] p1, double[] p2) {
        if (p1 == null || p2 == null) {
            return p1 == null && p2 == null;
        }

        int n = Math.max(p1.length, p2.length);

        for (int i = 0; i < n; i++) {
            double y1 = f(p1, i);
            double y2 = f(p2, i);

            if (Math.abs(y1 - y2) > EPS) {
                return false;
            }
        }

        return true;
    }

    /**
     * Computes a String representing the polynomial function.
     * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
     *
     * @param poly the polynomial function represented as an array of doubles
     * @return String representing the polynomial function:
     */
    public static String poly(double[] poly) {
        if (poly == null || poly.length == 0) {
            return "0";
        }

        String[] parts = new String[poly.length];
        for (int i = 0; i < poly.length; i++) {
            double coef = poly[i];
            if (coef == 1) {
                parts[i] = " +";
            } else if (coef > 0) {
                parts[i] = " +" + coef;
            } else if (coef == -1) {
                parts[i] = " -";
            } else if (coef < 0) {
                parts[i] = " " + coef;
            } else {
                parts[i] = "";
            }
        }

        if (parts.length > 1 && !parts[1].isEmpty()) {
            parts[1] += "x";
        }

        for (int i = 2; i < parts.length; i++) {
            String coef = parts[i];
            if (!coef.isEmpty()) {
                parts[i] += "x^" + i;
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int i = parts.length - 1; i >= 0; i--) {
            ans.append(parts[i]);
        }

        if (ans.isEmpty()) {
            return "0";
        }

        if (ans.charAt(1) == '+') {
            return ans.substring(2);
        }

        return ans.substring(1);
    }

    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
     * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
     *
     * @param p1  - first polynomial function
     * @param p2  - second polynomial function
     * @param x1  - minimal value of the range
     * @param x2  - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
     */
    public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        if (p1 == null || p2 == null) {
            return -1;
        }

        double[] p = add(p1, neg(p2));
        return root_rec(p, x1, x2, eps);
    }

    /**
     * Given a polynomial function (p), a range [x1,x2] and an integer representing the number (n) of sample points.
     * This function computes an approximation of the length of the function between f(x1) and f(x2)
     * using n inner sample points and computing the segment-path between them.
     * assuming x1 < x2.
     * This function should be implemented iteratively (none recursive).
     *
     * @param p                - the polynomial function
     * @param x1               - minimal value of the range
     * @param x2               - maximal value of the range
     * @param numberOfSegments - (A positive integer value (1,2,...).
     * @return the length approximation of the function between f(x1) and f(x2).
     */
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = 0;

        double width = (x2 - x1) / numberOfSegments;
        double widthSquared = width * width;

        for (int i = 0; i < numberOfSegments; i++) {
            double startX = x1 + width * i;
            double endX = startX + width;
            double height = f(p, endX) - f(p, startX);

            ans += Math.sqrt(widthSquared + height * height);
        }

        return ans;
    }

    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples on each polynom).
     * This function computes an approximation of the area between the polynomial functions within the x-range.
     * The area is computed using a Riemann-like integral (https://en.wikipedia.org/wiki/Riemann_integral)
     *
     * @param p1                - first polynomial function
     * @param p2                - second polynomial function
     * @param x1                - minimal value of the range
     * @param x2                - maximal value of the range
     * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
     * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     */
    public static double area(double[] p1, double[] p2, double x1, double x2, int numberOfTrapezoid) {
        double[] p = add(p1, neg(p2));
        double width = (x2 - x1) / numberOfTrapezoid;

        double ans=0, left=x1, right=x1+width;
        for ( ; right<x2; right+=width, left+=width) {
            ans += calcArea(p, left, right, width);
        }

        ans += calcArea(p, left, x2, width);

        return ans;
    }

    /**
     * This function computes the array representation of a polynomial function from a String
     * representation. Note: given a polynomial function represented as a double array,
     * getPolynomFromString(poly(p)) should return an array equals to p.
     *
     * @param p - a String representing polynomial function.
     * @return the array representing polynomial p
     */
    public static double[] getPolynomFromString(String p) {
        String[] parts = p.split(" ");

        if (parts[0].charAt(0) != '-') {
            parts[0] = "+" + parts[0];
        }

        int highestPower = 0;
        for (String part : parts) {
            String[] temp = part.split("\\^");
            if (temp.length > 1) {
                highestPower = Math.max(highestPower, Integer.parseInt(temp[1]));
            } else {
                if (lastCharIs(temp[0], 'x')) {
                    highestPower = Math.max(highestPower, 1);
                }
            }
        }

        double[] ans = new double[highestPower + 1];

        for (String part : parts) {
            String[] temp = part.split("\\^");

            if (temp.length > 1) {
                temp[0] = dropLastChar(ensureDigit(temp[0]));
                ans[Integer.parseInt(temp[1])] = Double.parseDouble(temp[0]);
            } else {
                if (lastCharIs(temp[0], 'x')) {
                    temp[0] = dropLastChar(ensureDigit(temp[0]));
                    ans[1] = Double.parseDouble(temp[0]);
                } else {
                    ans[0] = Double.parseDouble(temp[0]);
                }
            }
        }

        return ans;
    }

    /**
     * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     *
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return p1+p2
     */
    public static double[] add(double[] p1, double[] p2) {
        if (p1 == null || p2 == null) {
            return null;
        }

        double[] ans = new double[Math.max(p1.length, p2.length)];
        System.arraycopy(p1, 0, ans, 0, p1.length);
        for (int i = 0; i < p2.length; i++) {
            ans[i] += p2[i];
        }

        return ans;
    }

    /**
     * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
     *
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return p1*p2
     */
    public static double[] mul(double[] p1, double[] p2) {
        if (p1 == null || p2 == null || p1.length == 0 || p2.length == 0) {
            return null;
        }

        if (p1 == ZERO || p2 == ZERO) {
            return ZERO;
        }


        double[] ans = new double[p1.length + p2.length - 1];

        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                ans[i + j] += p1[i] * p2[j];
            }
        }

        return ans;
    }

    /**
     * This function computes the derivative of the p0 polynomial function.
     *
     * @param po polynomial to calculate its derivative
     * @return d/dx (po)
     */
    public static double[] derivative(double[] po) {
        if (po == null || po.length == 0) {
            return null;
        }

        if (po.length == 1) {
            return ZERO;
        }

        double[] ans = new double[po.length - 1];

        for (int i = 1; i < po.length; i++) {
            ans[i - 1] = po[i] * i;
        }

        return ans;
    }

    /**
     * Negates a given polynomial (i.e. multiplies by -1.)
     * @param p polynomial array
     * @return -p (the negative of the input polynomial)
     */
    public static double[] neg(double[] p) {
        if (p == null || p.length == 0) {
            return null;
        }

        double[] ans = new double[p.length];
        for (int i = 0; i < p.length; i++) {
            ans[i] = -p[i];
        }

        return ans;
    }

    /**
     * If the coefficient is '+x' or '-x' (etc.), we want to add '1' to the beginning (i.e. '-1x' or '+1x'.)
     * @param s coefficient string (of the form 'sign'+'x'+'^power (optional)')
     * @return string that is guaranteed to have a digit as its second character
     */
    public static String ensureDigit(String s) {
        if (s.charAt(1) == 'x') {
            return s.charAt(0) + "1.0" + s.substring(1);
        }

        return s;
    }

    /**
     * Checks if the last character of a string is equal to a given char
     * @param s string to check
     * @param c char to check against
     * @return true if the last character is indeed 'c'
     */
    public static boolean lastCharIs(String s, char c) {
        return s.charAt(s.length() - 1) == c;
    }

    /**
     * Removes the last character of a string
     * @param s string (at least length 1)
     * @return a new string which terminates one character earlier than the input string
     */
    public static String dropLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    /**
     * Indicates whether the function crosses the x-axis in a given interval.
     * @param p polynomial (lowest power to highest)
     * @param left start point
     * @param right end point
     * @return true if there is a change of sign
     */
    public static boolean changeOfSign(double[] p, double left, double right) {
        boolean signL = f(p, left) > 0;
        boolean signR = f(p, right) > 0;

        return signL != signR;
    }

    /**
     * Calculates the area (trapezoid or two triangles) under a curve, based on whether there is a change of sign.
     * @param p polynomial (lowest power to highest)
     * @param left start point
     * @param right end point
     * @param width equal to 'right minus left'
     * @return area between left and right
     */
    public static double calcArea(double[] p, double left, double right, double width) {
        double area = 0;

        if (changeOfSign(p, left, right)) {
            double root = root_rec(p, left, right, EPS);

            double b = left - root;
            double h = f(p, left);
            area += Math.abs(b * h / 2);

            b = root - right;
            h = f(p, right);
            area += Math.abs(b * h / 2);

        } else {
            double sum = f(p, left) + f(p, right);
            area += Math.abs(sum * width / 2);
        }

        return area;
    }
}
