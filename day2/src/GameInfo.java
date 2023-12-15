import java.util.List;

public record GameInfo(
        int id,
        List<SetInfo> sets
) {}