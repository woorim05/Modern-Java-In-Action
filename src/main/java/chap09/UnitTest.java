package chap09;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static chap02.FilteringApple.filter;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTest {

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public Point moveRightBy(int x) {
            return new Point(this.x + x, this.y);
        }

        // 람다를 필드에 저장해서 재사용 및 테스트
        public static final Comparator<Point> comapareByXAndThenY = Comparator.comparing(Point::getX).thenComparing(Point::getY);

        public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
            return points.stream()
                    .map(p -> new Point(p.getX() + x, p.getY()))
                    .collect(toList());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }
    }

    @Test
    public void testMoveRightBy() throws Exception {
        // Given
        Point p1 = new Point(5, 5);

        // When
        Point p2 = p1.moveRightBy(10);

        // Then
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        // Given
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);

        // When
        int result = Point.comapareByXAndThenY.compare(p1, p2);

        // Then
        assertTrue(result < 0);
    }

    @Test
    public void testMoveAllPointsRightBy() throws Exception {
        // Given
        List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));

        // When
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);

        // Then
        List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
        assertEquals(expectedPoints, newPoints);
    }

    @Test
    public void testFilter() throws Exception{
        // Given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        // When
        List<Integer> even = filter(numbers, i -> i % 2 == 0);
        List<Integer> smallerThanThree = filter(numbers, i -> i < 3);

        // Then
        assertEquals(Arrays.asList(2, 4), even);
        assertEquals(Arrays.asList(1, 2), smallerThanThree);
    }
}
