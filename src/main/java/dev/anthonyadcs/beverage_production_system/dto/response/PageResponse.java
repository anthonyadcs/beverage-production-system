package dev.anthonyadcs.beverage_production_system.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        Integer page,
        Integer pageSize,
        Long totalElements,
        Integer totalElementsInActualPage,
        Integer totalPages,
        Boolean first,
        Boolean last
) {
    public static <T> PageResponse<T> fromPage(Page<T> page){
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
