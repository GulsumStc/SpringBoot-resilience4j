package com.deg.country;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Country {

    private Long id;
    private String countryName;
}
