package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEditBookDto {
    @NotNull
    private String title;

    @NotNull
    private String author;

    private String description;

    private String image;

    @NotNull
    private boolean enabled;
    
}
