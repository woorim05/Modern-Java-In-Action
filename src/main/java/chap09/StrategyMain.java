package chap09;

public class StrategyMain {
    public static void main(String[] args) {
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase());
        System.out.println(v1.validate("bbbb"));

        // refactoring
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v1.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v1.validate("bbbb"));
    }

    public interface ValidationStrategy {
        boolean execute(String s);
    }

    public static class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    public static class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public static class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

}
