package com.sample.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = -1;
    private UUID id;
    private String message;

}
