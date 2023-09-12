package br.com.fiap.gsjava.repositories;

import br.com.fiap.gsjava.models.ChatGPT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGPTRepository extends JpaRepository<ChatGPT, Long> {
}
