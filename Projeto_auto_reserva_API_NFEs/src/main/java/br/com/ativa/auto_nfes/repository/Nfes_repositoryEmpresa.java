package br.com.ativa.auto_nfes.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ativa.auto_nfes.model.Empresa;

@Repository()
public interface Nfes_repositoryEmpresa extends JpaRepository<Empresa, UUID> {

    Optional<Empresa> findByCnpj(String cnpj);
}
