package com.unifacisa.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {

    private Long id;
    private String titulo;

    @Override
    public String toString() {
        return titulo;
    }


}
