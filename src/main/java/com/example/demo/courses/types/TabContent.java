package com.example.demo.courses.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabContent {
    private String name;
    private String type;
    private ContentData content;
}
