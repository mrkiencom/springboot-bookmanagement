package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookFromStoreDTO {

    @NotNull()
    private String title;

    @NotNull()
    private String subtitle;

    @NotNull()
    private String isbn13;

    @NotNull()
    private String price;

    @NotNull()
    private String image;

    @NotNull()
    private String url;

}
