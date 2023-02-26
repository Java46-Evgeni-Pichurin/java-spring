package telran.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import telran.util.model.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class JsonGenerator {
    private static final int N_ACCOUNTS = 50;
    private static final int PASS_LENGTH = 8;
    private static final int ASCII_FROM = 33;
    private static final int ASCII_TO = 126;
    private static final int TIME_RANGE_FROM = -100;
    private static final int TIME_RANGE_TO = 400;
    private static final String[] roles = {"ADMIN", "STATIST", "USER", "APPL_ADMIN"};



    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> accs = new ArrayList<>();
        IntStream.range(0, N_ACCOUNTS).forEach(acc ->
                {
                    try {
                        accs.add(
                                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                            new Account(
                                    String.format("account%d", acc+1),
                                    String.valueOf(getChars()),
                                    LocalDateTime.now().plus(getRandomNumber(TIME_RANGE_FROM, TIME_RANGE_TO + 1), ChronoUnit.HOURS)
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss")),
                                    getRoles(getRandomNumber(1, roles.length + 1))
                                    )
                                )
                        );
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        System.out.println(accs);
    }

    private static String[] getRoles(int n_roles) {
        Set<String> res = new HashSet<>();
        while (res.size() < n_roles) {
            res.add(roles[getRandomNumber(0, roles.length)]);
        }
        return res.toArray(String[]::new);
    }

    private static char[] getChars() {
        char[] res = new char[PASS_LENGTH];
        IntStream.range(0, PASS_LENGTH).forEach(c -> res[c] = (char) getRandomNumber(ASCII_FROM, ASCII_TO + 1));
        return res;
    }

    private static int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
