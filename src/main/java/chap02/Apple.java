package chap02;

public class Apple {
    private int weight = 0;
    private Color color;

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
        return String.format("Apple{color=%s, weight=%d}", color, weight);
    }
}

enum Color {
    RED,
    GREEN
}

// 전략 디자인 패턴: 전략 알고리즘(기능을 수행할 인터페이스를 상속받은 클래스)을
//                캡슐화하는 알고리즘 패밀리(인터페이스)를 정의해둔 다음 런타임에 알고리즘을 선택하는 기법
// predicate: 선택 조건을 결정하는 인터페이스
interface ApplePredicate {
    boolean test(Apple a);
}

class AppleWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

class AppleColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor() == Color.GREEN;
    }
}

class AppleRedAndHeavyPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor() == Color.RED && apple.getWeight() > 150;
    }
}