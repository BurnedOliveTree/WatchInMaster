package pw.edu.watchin.server.dto.pagination;

import lombok.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Value
public class PageRequest<T> {
    T filter;
    int page;
    int size;

    public Pageable toPageable() {
        return org.springframework.data.domain.PageRequest.of(page, size);
    }

    public Pageable toPageable(Sort sort) {
        return org.springframework.data.domain.PageRequest.of(page, size, sort);
    }
}
