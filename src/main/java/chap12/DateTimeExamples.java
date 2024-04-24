package chap12;

import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeExamples {
    public static void main(String[] args) {
        // 날짜
        LocalDate today = LocalDate.now();          // 오늘 날짜(YYYY-MM-dd)
        LocalDate dateFrom = LocalDate.from(today); // 오늘 날짜(YYYY-MM-dd)
        LocalDate date = LocalDate.of(2024, 4, 19); // 2024-04-19
        int year = date.getYear();                  // 2024
        Month month = date.getMonth();              // APRIL
        int day = date.getDayOfMonth();             // 19
        DayOfWeek dow = date.getDayOfWeek();        // FRIDAY
        int len = date.lengthOfMonth();             // 30 (월의 일수)
        boolean leap = date.isLeapYear();           // true(윤년여부)

        JapaneseDate japaneseDate = JapaneseDate.from(today);
        System.out.println(japaneseDate);
        // 시간
        LocalTime time = LocalTime.of(13, 45, 20);  // 13:45:20
        int hour = time.getHour();                  // 13
        int minute = time.getMinute();              // 45
        int second = time.getSecond();              // 20

        // 날짜 시간 한번에
        LocalDateTime dt1 = LocalDateTime.of(2024, Month.APRIL, 19, 13, 45, 20); // 2024-04-19T13:45:20
        LocalDateTime dt2 = LocalDateTime.of(date, time); // 2024-04-19T13:45:20
        LocalDateTime dt3 = time.atDate(date); // 2024-04-19T13:45:20
        LocalDateTime dt4 = date.atTime(13, 45, 20); // 2024-04-19T13:45:20
        LocalDateTime dt5 = date.atTime(time); // 2024-04-19T13:45:20

        LocalDate date1 = dt1.toLocalDate(); // 2024-04-19
        LocalTime time1 = dt1.toLocalTime(); // 13:45:20

        // 날짜 조정
        date1 = date1.plusWeeks(1); // 2024-04-26
        date1 = date1.minusYears(2); // 2022-04-26
        date1 = date1.plus(3, ChronoUnit.MONTHS); //  2022-07-26

        LocalDate date2 = date1.withYear(2024);     // 2024-07-26
        LocalDate date3 = date2.withMonth(4);       // 2024-04-26
        LocalDate date4 = date3.withDayOfMonth(19); // 2024-04-19


        // 아래 Instant는 모두 같음
        Instant.ofEpochSecond(3);
        Instant.ofEpochSecond(3, 0);
        Instant.ofEpochSecond(2, 1000000000);  // 10억 = 1초
        Instant.ofEpochSecond(4, -1000000000);

        // format & parse
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20240419
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2024-04-19
        LocalDate date5 = LocalDate.parse("20240422", DateTimeFormatter.BASIC_ISO_DATE); // 20240422
        LocalDate date6 = LocalDate.parse("2024-04-22", DateTimeFormatter.ISO_LOCAL_DATE); // 2024-04-22

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date5.format(formatter); // 22/04/2024
        LocalDate date7 = LocalDate.parse(formattedDate, formatter); // 2024-04-22
    }
}
