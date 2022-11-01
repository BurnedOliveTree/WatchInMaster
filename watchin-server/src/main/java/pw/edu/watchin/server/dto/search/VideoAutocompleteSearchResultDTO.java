package pw.edu.watchin.server.dto.search;

import lombok.Value;

import java.util.List;

@Value
public class VideoAutocompleteSearchResultDTO {
    List<String> options;
}
