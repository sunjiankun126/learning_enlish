package com.yuning.learning.english.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    private String name;
    private List<String> trans;
    private String ukphone;
    private String usphone;
}
