import java.util.List;

public class Day1 {
    static List<String> args = List.of(Input.input.split("\n"));

    static char[][] writtenNumbers = {
            "one".toCharArray(),
            "two".toCharArray(),
            "three".toCharArray(),
            "four".toCharArray(),
            "five".toCharArray(),
            "six".toCharArray(),
            "seven".toCharArray(),
            "eight".toCharArray(),
            "nine".toCharArray()
    };

    public static void main(String[] a) {
        int sum = 0;
        for (String s : args) {
            int calibrationValue = formNumber(findFirstDigit(s), findLastDigit(s));
            sum += calibrationValue;
        }
        System.out.println(sum);
    }

    static int formNumber(int tensPlace, int unitsPlace) {
        return 10 * tensPlace + unitsPlace;
    }

    static int findFirstDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            int numberCheckResult = runCharacterCheckForString(s, i);
            if (numberCheckResult != 0) return numberCheckResult;
        }
        return 0;
    }


    static int findLastDigit(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            int numberCheckResult = runCharacterCheckForString(s, i);
            if (numberCheckResult != 0) return numberCheckResult;
        }
        return 0;
    }

    static int runCharacterCheckForString(String s, int index) {
        char c = s.charAt(index);
        int number;
        boolean isDigit = Character.isDigit(c);
        if (isDigit) {
            number = Integer.parseInt(String.valueOf(c));
        } else {
            number = checkForWrittenNumber(s, index);
        }
        return number;
    }

    private static int checkForWrittenNumber(String s, int index) {
        boolean numberFound = false;
        int number = 1;
        for (char[] writtenNumber : writtenNumbers) {
            int i = index;
            for (char c : writtenNumber) {
                if (i == s.length() || s.charAt(i) != c) break;
                i++;
                if (writtenNumber.length == i - index) numberFound = true;
            }
            if (numberFound)
                return number;
            number++;
        }
        return 0;
    }

}