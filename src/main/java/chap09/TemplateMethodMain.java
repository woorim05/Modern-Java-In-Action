package chap09;

import java.util.function.Consumer;

public class TemplateMethodMain {
    public static void main(String[] args) {
        OnlineBanking onlineBanking = new OnlineBanking() {
            @Override
            void makeCustomerHappy(Customer c) {
                System.out.println("Hello!");
            }
        };
        onlineBanking.processCustomer(1);

        // refactoring
        processCustomerLambda(1, (Customer c) -> System.out.println("Hello!"));
    }

    abstract static class OnlineBanking {
        public void processCustomer(int id) {
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }

        // 추상 메서드 정의 필요
        abstract void makeCustomerHappy(Customer c);
    }

    public static void processCustomerLambda(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // 더미 Customer 클래스
    public static class Customer {
    }

    // 더미 Database 클래스
    public static class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
