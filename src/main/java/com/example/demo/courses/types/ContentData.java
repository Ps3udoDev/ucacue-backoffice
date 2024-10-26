package com.example.demo.courses.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentData {
    private String id;
    private String src;
    private String content;
}
