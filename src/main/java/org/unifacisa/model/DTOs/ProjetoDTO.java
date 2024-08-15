package org.unifacisa.model.DTOs;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetoDTO that = (ProjetoDTO) o;
        return Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(titulo);
    }

    @Override
    public String toString() {
        return titulo;
    }


}
