package inkspiration.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;
    
    @Column(nullable = false)
    private String tipoServico;
    
    @Column(nullable = false)
    private LocalDateTime dtInicio;
    
    @Column(nullable = false)
    private LocalDateTime dtFim;
    
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    public Agendamento() {}
    
    // Getters e Setters
    public Long getIdAgendamento() {
        return idAgendamento;
    }
    
    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
    
    public String getTipoServico() {
        return tipoServico;
    }
    
    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
    
    public LocalDateTime getDtInicio() {
        return dtInicio;
    }
    
    public void setDtInicio(LocalDateTime dtInicio) {
        this.dtInicio = dtInicio;
    }
    
    public LocalDateTime getDtFim() {
        return dtFim;
    }
    
    public void setDtFim(LocalDateTime dtFim) {
        this.dtFim = dtFim;
    }
    
    public Profissional getProfissional() {
        return profissional;
    }
    
    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
} 