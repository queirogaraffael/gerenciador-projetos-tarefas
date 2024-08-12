package org.unifacisa.model.DTOs;


public class ProjetoDTO {
    private Long id;
    private String titulo;

    public ProjetoDTO() {
    }

    public ProjetoDTO(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
