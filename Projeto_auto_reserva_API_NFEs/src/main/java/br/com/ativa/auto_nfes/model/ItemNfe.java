package br.com.ativa.auto_nfes.model;

import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity(name = "DadosItemNfe")
public class ItemNfe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idNota;
    @Column(nullable = false)
    private String chaveAcessoNFe;
    @Column(nullable = false, unique = true)
    private String codProduto;
    private String dsItem;
    private String vlrItem;
    private String qtdItem;
    private String qtdLote;
    @ManyToAny
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
}
