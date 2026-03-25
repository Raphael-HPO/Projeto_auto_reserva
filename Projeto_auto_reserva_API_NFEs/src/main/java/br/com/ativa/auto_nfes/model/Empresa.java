package br.com.ativa.auto_nfes.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(name = "DadosEmpresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idEmpresa;
    @Column(nullable = false, unique = true)
    private String cnpj;
    private String uf;

    public Empresa(String cnpj, String uf) {
        this.cnpj = cnpj;
        this.uf = uf;
    }

}
