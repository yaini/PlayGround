package com.yaini.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Student {
    private Long id;
    private String name;
    private String email;
}
