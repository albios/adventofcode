import java.util.HashMap;
import java.util.Map;

public record SetInfo(
        Map<Colours, Integer> coloursQuantityMap) {

    public SetInfo() {
      this(new HashMap<>());
   }

}
