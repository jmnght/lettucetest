package com.example.lettucetest.config;

import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Parent implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String name;
    
    private List<Child> childList;

}
