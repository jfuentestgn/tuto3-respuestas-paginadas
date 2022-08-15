package net.jfuentestgn.tutorials.pagedresponse;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PagedResponse<DTO, ENTITY> implements Serializable {

    private PageMeta meta;
    private List<DTO> data;

    public PagedResponse(Page<ENTITY> content, Function<ENTITY, DTO> mapper) {
        this.setMeta(content);
        this.data = content
                .getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private void setMeta(Page<?> content) {
        this.meta = new PageMeta();
        meta.setCurrentPage(content.getNumber() + 1);
        meta.setTotalElements(content.getTotalElements());
        meta.setTotalPages(content.getTotalPages());
        meta.setSize(content.getSize());
        meta.setNumberOfElements(content.getNumberOfElements());
    }

    public PageMeta getMeta() {
        return meta;
    }


    public List<DTO> getData() {
        return data;
    }

}
