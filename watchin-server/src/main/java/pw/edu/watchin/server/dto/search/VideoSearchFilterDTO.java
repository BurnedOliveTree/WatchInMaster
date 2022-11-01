package pw.edu.watchin.server.dto.search;

import lombok.Value;

@Value
public class VideoSearchFilterDTO {
    String phrase;
    String channel;
    DateFilter date;
    DurationFilter duration;
    SortOrder sort;

    public enum DateFilter { HOUR, DAY, WEEK, MONTH, YEAR }
    public enum DurationFilter { SHORT, MEDIUM, LONG }
    public enum SortOrder { RELEVANCE, DATE, VIEWS }
}
