package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDTO {

    @NotNull
    private String total;

    @NotNull
    private List<BookFromStoreDTO> books;
}
