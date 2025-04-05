package inkspiration.backend.dto;

import java.time.LocalDateTime;

import inkspiration.backend.entities.Agendamento;

public class AgendamentoDTO {
    private Long idAgendamento;
    private String tipoServico;
    private LocalDateTime dtInicio;
    private LocalDateTime dtFim;
    private Long idProfissional;
    private Long idUsuario;

    // Construtor vazio
    public AgendamentoDTO() {
    }
    
    // Construtor para converter da entidade
    public AgendamentoDTO(Agendamento agendamento) {
        this.idAgendamento = agendamento.getIdAgendamento();
        this.tipoServico = agendamento.getTipoServico();
        this.dtInicio = agendamento.getDtInicio();
        this.dtFim = agendamento.getDtFim();
        this.idProfissional = agendamento.getProfissional().getIdProfissional();
        this.idUsuario = agendamento.getUsuario().getIdUsuario();
    }

    // Construtor completo
    public AgendamentoDTO(Long idAgendamento, String tipoServico, LocalDateTime dtInicio, LocalDateTime dtFim, Long idProfissional, Long idUsuario) {
        this.idAgendamento = idAgendamento;
        this.tipoServico = tipoServico;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.idProfissional = idProfissional;
        this.idUsuario = idUsuario;
    }

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

    public Long getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}