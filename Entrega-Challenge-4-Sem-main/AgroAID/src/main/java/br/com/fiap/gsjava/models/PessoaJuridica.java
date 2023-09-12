package br.com.fiap.gsjava.models;
import jakarta.persistence.*;

@Entity
@Table(
        name = "TB_PESSOA_JURIDICA",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_CNPJ", columnNames = "NR_CNPJ")
        }
)
@DiscriminatorValue("PJ")
public class PessoaJuridica extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PESSOAJURIDICA")
    @SequenceGenerator(
            name = "SQ_PESSOAJURIDICA",
            sequenceName = "SQ_PESSOAJURIDICA",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "ID_PESSOA")
    protected Long id;
    @Column(name = "NR_CNPJ")
    private String CNPJ;


    public String getCNPJ() {
        return CNPJ;
    }

    public PessoaJuridica setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
        return this;
    }



    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ",  nome='" + nome + '\'' +
                ",  nascimento=" + nascimento + '\'' +
                ",  CNPJ='" + CNPJ + '\'' +
                " } ";
    }
}