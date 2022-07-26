package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {
    @NotNull
    private String title;

    @NotNull
    private String author;

    private String description;

    private String image;
}
