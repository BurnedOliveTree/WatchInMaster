package pw.edu.watchin.server.dto.search;

import lombok.Value;

@Value
public class VideoTableFilterDTO {
    String phrase;
    String sortField;
    String sortDirection;
}
