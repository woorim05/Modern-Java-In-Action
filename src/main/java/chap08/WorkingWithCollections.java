package chap08;

import chap05.Practice;
import chap05.Transaction;

import java.util.*;

import static java.util.Map.entry;

public class WorkingWithCollections {
    public static void main(String[] args) {
        workingWithLists();
        workingWithMaps();
        computingOnMaps();
        removingFromMaps();
        replacingInMaps();
        mergingMaps();
    }

    private static void mergingMaps() {
        System.out.println("====================== mergingMaps  ======================");
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bod")
        );
        Map<String, String> friends = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix")
        );

        Map<String, String> everyone = new HashMap<>(family); // family와 동일한 맵 생성
        everyone.putAll(friends);
        System.out.println("map1에 map2 넣기 :: " + everyone); // friends의 Cristina 값으로 덮어짐

        Map<String, String> everyone2 = new HashMap<>(family);
                                                        // movie1이 family의 value고 movie2가 v
        friends.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println("merge :: " + everyone2);
    }

    private static void replacingInMaps() {
        System.out.println("====================== replacingInMaps ======================");
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Star Wars");
        favouriteMovies.put("Olivia", "james bond");

        favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
        System.out.println("replaceAll :: " + favouriteMovies); // 모두 대문자로 변경

        favouriteMovies.replace("Raphael", "Jack Reacher 2");
        System.out.println("replace :: " + favouriteMovies); // Raphael 값 변경

        favouriteMovies.replace("Raphael", "Jack Reacher 2", "Jack Reacher 1");
        System.out.println("replace2 :: " + favouriteMovies); // Raphael 값이 Jack Reacher 2 이면 Jack Reacher 1 로 변경
    }

    private static void removingFromMaps() {
        System.out.println("====================== removingFromMaps ======================");
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Jack Reacher 2");
        favouriteMovies.put("Cristina", "Matrix");
        favouriteMovies.put("Olivia", "James Bond");

        // key 가 존재하고 그에 해당하는 value 가 내가 원하는 value 이면 삭제
        String key = "Raphael";
        String value = "Jack Reacher 2";
        if (favouriteMovies.containsKey(key)
                && Objects.equals(favouriteMovies.get(key), value)) { // Object.equals(a, b) a 값 null 체크 해줌
            favouriteMovies.remove(key);
        }
        System.out.println("# 기존코드 :: " + favouriteMovies);

        favouriteMovies.put("Raphael", "Jack Reacher 1");
        favouriteMovies.remove(key, value); // 이렇게 쓰면 해결
        System.out.println("# remove(k,v) :: " + favouriteMovies);
    }

    private static void computingOnMaps() {
        System.out.println("====================== computingOnMaps ======================");
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        // key로 조회한 value가 없으면 추가
        System.out.println("# 기존 코드");

        String friend = "Raphael";
        List<String> movies = friendsToMovies.get(friend);
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("Star Wars");
        System.out.println(friendsToMovies);

        System.out.println("# 인터페이스 메서드");
        friendsToMovies.computeIfAbsent("Olivia", name -> new ArrayList<>()).add("Star Wars"); // 없으면 추가
        System.out.println("computeIfAbsent :: " + friendsToMovies);

        friendsToMovies.computeIfPresent("Olivia", (key, value) -> new ArrayList<>()).add("Begin Again"); // 있으면 변경
        System.out.println("computeIfPresent :: " + friendsToMovies);

        friendsToMovies.compute("Olivia", (key, value) -> new ArrayList<>()).add("Spider Man"); // 존재 여부 상관없이 추가 or 변경
        System.out.println("compute :: " + friendsToMovies);
    }

    private static void workingWithMaps() {
        System.out.println("====================== workingWithMaps ======================");
        Map<String, Integer> ageOfFriends = Map.of("Ella", 40,"Paul", 34,"Neil", 38,"Raphael", 30, "Olivia", 25, "Thibaut", 26);

        System.out.println("====================== 키, 값 얻기 ======================");
        System.out.println("# 기존 코드");
        for (Map.Entry<String, Integer> entry : ageOfFriends.entrySet()) {
            String name = entry.getKey();
            int age = entry.getValue();
            System.out.println(name + " is " + age + " years old");
        }

        System.out.println("# 인터페이스 메서드");
        ageOfFriends.forEach((name, age) -> System.out.println(name + " is " + age + " years old"));

        System.out.println("=================== 정렬 ===================");
        ageOfFriends.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(f -> System.out.println(f.getKey() + " is " + f.getValue() + " years old"));

        System.out.println("=================== getOrDefault ===================");
        if (ageOfFriends.get("Matrix") != null) {
            System.out.println("# 기존 코드 :: " + ageOfFriends.get("Matrix"));
        } else {
            System.out.println("# 기존 코드 :: " + 0);
        }

        System.out.println("# getOrDefault :: " + ageOfFriends.getOrDefault("Matrix", 0));
        System.out.println("# getOrDefault :: " + ageOfFriends.getOrDefault("Paul", 0));
    }

    private static void workingWithLists() {
        System.out.println("====================== workingWithLists ======================");
        System.out.println("# 기존 코드");
        List<Transaction> transactions = new ArrayList<>(Practice.transactions);
        System.out.println("origin transactions :: " + transactions);

        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction t = iterator.next();
            if (Character.isDigit(t.getReferenceCode().charAt(0))) {
                iterator.remove();
            }
        }
        System.out.println("remove :: " +transactions);

        for (Transaction t : transactions) {
            t.setReferenceCode(Character.toUpperCase(t.getReferenceCode().charAt(0)) + t.getReferenceCode().substring(1));
        }
        System.out.println("replace :: " + transactions);


        System.out.println("# 인터페이스 메서드");
        transactions = new ArrayList<>(Practice.transactions); // 초기화
        System.out.println("origin transactions :: " + transactions);

        // removeIf
        transactions.removeIf(t -> Character.isDigit(t.getReferenceCode().charAt(0)));
        System.out.println("removeIf :: " + transactions);

        // replaceAll
        transactions.replaceAll(t -> {
            t.setReferenceCode(Character.toUpperCase(t.getReferenceCode().charAt(0)) + t.getReferenceCode().substring(1));
            return new Transaction(t);
        });
        System.out.println("replaceAll :: " + transactions);

        transactions.sort(Comparator.comparing(Transaction::getReferenceCode));
    }

}
