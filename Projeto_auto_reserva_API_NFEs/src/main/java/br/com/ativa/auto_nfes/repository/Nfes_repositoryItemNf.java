package br.com.ativa.auto_nfes.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ativa.auto_nfes.model.ItemNfe;

@Repository()
public interface Nfes_repositoryItemNf extends JpaRepository<ItemNfe, UUID> {
    List<ItemNfe> findAllByChaveAcessoNFe(String chaveAcessoNFe);

    Optional<ItemNfe> findLastByChaveAcessoNFe(String chaveAcessoNFe);
}
