package chap08;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

public class CreatingCollections {
    public static void main(String[] args) {
        // 리스트
        List<String> friendsList1 = Arrays.asList("Raphael", "Olivia", "Thibaut");
        try {
            friendsList1.set(0, "Richard"); // 수정 가능
            friendsList1.remove("Thibaut");
//            friendsList1.add("Chih-Chun"); // 추가/삭제 불가능
        } catch (UnsupportedOperationException e) {
            System.out.println("Error 1");
            e.printStackTrace();
        } finally {
            System.out.println(friendsList1); // [Richard, Olivia, Thibaut]
        }

        List<String> friendsList2 = List.of("Raphael", "Olivia", "Thibaut");
        try {
            friendsList2.set(0, "Richard"); // 수정 불가능
            friendsList2.add("Chih-Chun"); // 추가/삭제 불가능
        } catch (UnsupportedOperationException e) {
            System.out.println("Error 2");
            e.printStackTrace();
        } finally {
            System.out.println(friendsList2); // [Raphael, Olivia, Thibaut]
        }

        // 집합
        Set<String> friendsSet = Set.of("Raphael", "Olivia", "Thibaut");
        try {
            friendsSet.add("Richard"); // 추가/삭제 불가능
            friendsSet.remove("Raphael");
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        try {
            Set<String> friendsSet2 = Set.of("Raphael", "Olivia", "Thibaut", "Thibaut"); // 중복 요소 포함된 집합 생성 불가
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // 맵
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        // 10개 이상의 맵 생성할 때는 ofEntries 사용하는게 좋음
        Map<String, Integer> ageOfFriends2 = Map.ofEntries(entry("Raphael", 30),
                                                            entry("Olivia", 25),
                                                            entry("Thibaut", 26));

    }
}
