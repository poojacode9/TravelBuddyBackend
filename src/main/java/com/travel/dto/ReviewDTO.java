package com.travel.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 5, message = "Maximum rating is 5")
    private Integer rating;

    @NotBlank(message = "Comment is required")
    private String comment;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Package ID is required")
    private Long packageId;
}