package chap09;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainOfResponsibilityMain {
    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2); // 두 작업 처리 객체를 연결
        String result = p1.handle("labdas");
        System.out.println(result);

        // refactoring
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("labdas");
        System.out.println(result2);
    }

    private static abstract class ProcessingObject<T> {
        public ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    public static class HeaderTextProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return "From Raoul, Mario and Alan: " + input;
        }
    }

    public static class SpellCheckerProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String input) {
            return input.replaceAll("labda", "lambda");
        }
    }



}
