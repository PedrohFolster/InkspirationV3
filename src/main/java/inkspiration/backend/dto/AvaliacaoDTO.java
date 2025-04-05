package inkspiration.backend.dto;

import inkspiration.backend.entities.Avaliacao;

public class AvaliacaoDTO {
    private Long idAvaliacao;
    private String descricao;
    private Integer rating;
    private Long idUsuario;
    private Long idProfissional;
    private Long idAgendamento;

    // Construtor vazio
    public AvaliacaoDTO() {
    }
    
    // Construtor para converter da entidade
    public AvaliacaoDTO(Avaliacao avaliacao) {
        this.idAvaliacao = avaliacao.getIdAvaliacao();
        this.descricao = avaliacao.getDescricao();
        this.rating = avaliacao.getRating();
        this.idUsuario = avaliacao.getUsuario().getIdUsuario();
        this.idProfissional = avaliacao.getProfissional().getIdProfissional();
        this.idAgendamento = avaliacao.getAgendamento().getIdAgendamento();
    }

    // Construtor completo
    public AvaliacaoDTO(Long idAvaliacao, String descricao, Integer rating, Long idUsuario, Long idProfissional, Long idAgendamento) {
        this.idAvaliacao = idAvaliacao;
        this.descricao = descricao;
        this.rating = rating;
        this.idUsuario = idUsuario;
        this.idProfissional = idProfissional;
        this.idAgendamento = idAgendamento;
    }

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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Long getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
} 