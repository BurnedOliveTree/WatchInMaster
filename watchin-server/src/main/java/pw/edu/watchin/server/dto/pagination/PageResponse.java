package pw.edu.watchin.server.dto.pagination;

import lombok.Value;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    int currentPage;
    int totalPages;
    long totalElements;
}
