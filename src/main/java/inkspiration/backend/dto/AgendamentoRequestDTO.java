package inkspiration.backend.dto;

import java.time.LocalDateTime;

public class AgendamentoRequestDTO {
    private String tipoServico;
    private LocalDateTime dtInicio;
    private LocalDateTime dtFim;
    private Long idProfissional;
    private Long idUsuario;

    public AgendamentoRequestDTO() {}

    public AgendamentoRequestDTO(String tipoServico, LocalDateTime dtInicio, LocalDateTime dtFim, 
                               Long idProfissional, Long idUsuario) {
        this.tipoServico = tipoServico;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.idProfissional = idProfissional;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
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