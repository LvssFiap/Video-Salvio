package br.com.fiap.gsjava.repositories;

import br.com.fiap.gsjava.models.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PessoaRepository extends JpaRepository<PessoaFisica, Long> {

}
