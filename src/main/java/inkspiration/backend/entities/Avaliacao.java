package inkspiration.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvaliacao;
    
    @Column(nullable = false, length = 1000)
    private String descricao;
    
    @Column(nullable = false)
    private Integer rating; // Avaliação de 1 a 5 estrelas
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;
    
    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento; // Vincula a avaliação a um agendamento específico
    
    public Avaliacao() {}
    
    // Getters e Setters
    public Long getIdAvaliacao() {
        return idAvaliacao;
    }
    
    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Profissional getProfissional() {
        return profissional;
    }
    
    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
    
    public Agendamento getAgendamento() {
        return agendamento;
    }
    
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
} 