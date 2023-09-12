package br.com.fiap.gsjava.models;

import br.com.fiap.gsjava.controllers.ChatGPTController;
import lombok.*;
import jakarta.persistence.*;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name="tb_gpt_chat")
public class ChatGPT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chatgpt")
    private Long id;
    @Column(name = "cl_pergunta")
    private String pergunta;
    @Column(name = "cl_resposta")
    private String resposta;

    public ChatGPT(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }
    public EntityModel<ChatGPT> toModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ChatGPTController.class).show(id)).withSelfRel(),
                linkTo(methodOn(ChatGPTController.class).destroy(id)).withRel("delete"),
                linkTo(methodOn(ChatGPTController.class).index(Pageable.unpaged())).withRel("all")
        );
    }
}