package com.yaini.adapter.in.batch.item;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class StudentReadItem {
    private String studentNumber;
    private String name;
    private String gender;
    private String graduation;
    private String admission;
}
