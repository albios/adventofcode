import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) {
        String input = Input.input;
        List<GameInfo> gameInfoList = parseGameInfoFromInput(input);
        List<GameInfo> gameInfoFiltered = filter(gameInfoList);

        int result1 = gameInfoFiltered.stream()
                .map(GameInfo::id)
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(result1);

        int result2 = findPart2Answer(gameInfoList);
        System.out.println(result2);
    }

    private static Map<Colours, Integer> getDefaultFewestPossibleNumberForColour() {
        return Arrays.stream(Colours.values())
                .collect(Collectors.toMap(colours -> colours, colours -> 0));
    }


    private static int findPart2Answer(List<GameInfo> gameInfoList) {
        int sum = 0;
        for (GameInfo gameInfo : gameInfoList) {
            sum += findPart2AnswerForGame(gameInfo);
        }
        return sum;
    }

    private static int findPart2AnswerForGame(GameInfo gameInfo) {
        Map<Colours, Integer> fewestPossibleNumberForColour = getDefaultFewestPossibleNumberForColour();
        for (SetInfo set : gameInfo.sets()) {
            for (Map.Entry entry : set.coloursQuantityMap().entrySet()) {
                Colours colour = (Colours) entry.getKey();
                Integer colourQuantity = (Integer) entry.getValue();
                if (fewestPossibleNumberForColour.get(colour).intValue() < colourQuantity)
                    fewestPossibleNumberForColour.put(colour, colourQuantity);
            }
        }
        int result = 1;
        for (Integer value : fewestPossibleNumberForColour.values()) {
            result = result * value;
        }
        return result;
    }

    private static List<GameInfo> filter(List<GameInfo> gameInfoList) {
        return gameInfoList.stream()
                .filter(Day2::checkGameInfoAgainstMaxColourNumbers)
                .toList();
    }

    private static boolean checkGameInfoAgainstMaxColourNumbers(GameInfo gameInfo) {
        return gameInfo.sets().stream().allMatch(Day2::checkSetInfoAgainstMaxColourNumbers);
    }

    private static boolean checkSetInfoAgainstMaxColourNumbers(SetInfo setInfo) {
        return setInfo.coloursQuantityMap().entrySet().stream()
                .allMatch(entry -> entry.getKey().getMaxPossible() >= entry.getValue());
    }

    private static List<GameInfo> parseGameInfoFromInput(String input) {
        List<GameInfo> gameInfoList = new ArrayList<>();
        List<String> inputSplitByGame = List.of(input.split("\n"));
        for (String gameInfoString : inputSplitByGame) {
            int id = Integer.parseInt(gameInfoString.substring(5, gameInfoString.indexOf(':')));

            gameInfoString = gameInfoString.split(":")[1];
            List<SetInfo> inputSplitByGameAndSet = parseSetsInfo(gameInfoString);

            GameInfo gameInfo = new GameInfo(id, inputSplitByGameAndSet);
            gameInfoList.add(gameInfo);
        }
        return gameInfoList;
    }

    private static List<SetInfo> parseSetsInfo(String gameInfo) {
        List<SetInfo> sets = new ArrayList<>();
        for (String setInfoString : gameInfo.split(";")) {
            SetInfo setInfo = new SetInfo();
            for (String colourInfo : setInfoString.split(",")) {
                Colours colour = Colours.parseColourByNumber(colourInfo);
                int colourQuantity = parseColourQuantity(colourInfo);
                setInfo.coloursQuantityMap().put(colour, colourQuantity);
            }
            sets.add(setInfo);
        }
        return sets;
    }

    private static int parseColourQuantity(String colourInfo) {
        return Integer.parseInt(colourInfo.split(" ")[1]);
    }
}
