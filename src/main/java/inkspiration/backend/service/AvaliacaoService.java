package inkspiration.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inkspiration.backend.dto.AvaliacaoDTO;
import inkspiration.backend.entities.Agendamento;
import inkspiration.backend.entities.Avaliacao;
import inkspiration.backend.entities.Profissional;
import inkspiration.backend.entities.Usuario;
import inkspiration.backend.repository.AgendamentoRepository;
import inkspiration.backend.repository.AvaliacaoRepository;
import inkspiration.backend.repository.ProfissionalRepository;
import inkspiration.backend.repository.UsuarioRepository;

@Service
public class AvaliacaoService {
    
    private final AvaliacaoRepository avaliacaoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    
    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AgendamentoRepository agendamentoRepository,
            ProfissionalRepository profissionalRepository,
            UsuarioRepository usuarioRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.profissionalRepository = profissionalRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    @Transactional
    public Avaliacao criarAvaliacao(Long idUsuario, Long idAgendamento, String descricao, Integer rating) {
        // Validar se o usuário existe
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Validar se o agendamento existe
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        // Validar se o agendamento pertence ao usuário
        if (!agendamento.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("Usuário não autorizado a avaliar este agendamento");
        }
        
        // Validar se já existe uma avaliação para este agendamento
        if (avaliacaoRepository.existsByAgendamento(agendamento)) {
            throw new RuntimeException("Este agendamento já foi avaliado");
        }
        
        // Validar rating (1-5)
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("A avaliação deve ser entre 1 e 5 estrelas");
        }
        
        // Criar a avaliação
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUsuario(usuario);
        avaliacao.setProfissional(agendamento.getProfissional());
        avaliacao.setAgendamento(agendamento);
        avaliacao.setDescricao(descricao);
        avaliacao.setRating(rating);
        
        // Salvar a avaliação
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        
        // Atualizar a nota média do profissional
        atualizarNotaProfissional(agendamento.getProfissional().getIdProfissional());
        
        return avaliacaoSalva;
    }
    
    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }
    
    public List<Avaliacao> listarPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return avaliacaoRepository.findByUsuario(usuario);
    }
    
    public Page<Avaliacao> listarPorUsuario(Long idUsuario, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return avaliacaoRepository.findByUsuario(usuario, pageable);
    }
    
    public List<Avaliacao> listarPorProfissional(Long idProfissional) {
        Profissional profissional = profissionalRepository.findById(idProfissional)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        return avaliacaoRepository.findByProfissional(profissional);
    }
    
    public Page<Avaliacao> listarPorProfissional(Long idProfissional, Pageable pageable) {
        Profissional profissional = profissionalRepository.findById(idProfissional)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        return avaliacaoRepository.findByProfissional(profissional, pageable);
    }
    
    @Transactional
    public Avaliacao atualizarAvaliacao(Long id, String descricao, Integer rating) {
        Avaliacao avaliacao = buscarPorId(id);
        
        // Validar rating (1-5)
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("A avaliação deve ser entre 1 e 5 estrelas");
        }
        
        // Atualizar dados
        avaliacao.setDescricao(descricao);
        avaliacao.setRating(rating);
        
        // Salvar alterações
        Avaliacao avaliacaoAtualizada = avaliacaoRepository.save(avaliacao);
        
        // Atualizar a nota média do profissional
        atualizarNotaProfissional(avaliacao.getProfissional().getIdProfissional());
        
        return avaliacaoAtualizada;
    }
    
    @Transactional
    public void excluirAvaliacao(Long id) {
        Avaliacao avaliacao = buscarPorId(id);
        Long idProfissional = avaliacao.getProfissional().getIdProfissional();
        
        avaliacaoRepository.delete(avaliacao);
        
        // Atualizar a nota média do profissional
        atualizarNotaProfissional(idProfissional);
    }
    
    @Transactional
    public void atualizarNotaProfissional(Long idProfissional) {
        // Obter a média das avaliações para o profissional
        Double mediaAvaliacoes = avaliacaoRepository.calculateAverageRatingByProfissional(idProfissional);
        
        // Se não houver avaliações, definir como zero
        if (mediaAvaliacoes == null) {
            mediaAvaliacoes = 0.0;
        }
        
        // Arredondar para uma casa decimal
        BigDecimal notaMedia = BigDecimal.valueOf(mediaAvaliacoes).setScale(1, BigDecimal.ROUND_HALF_UP);
        
        // Atualizar a nota do profissional
        Profissional profissional = profissionalRepository.findById(idProfissional)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        profissional.setNota(notaMedia);
        profissionalRepository.save(profissional);
    }
    
    // Métodos que retornam DTOs
    public AvaliacaoDTO buscarPorIdDTO(Long id) {
        Avaliacao avaliacao = buscarPorId(id);
        return new AvaliacaoDTO(avaliacao);
    }
    
    public List<AvaliacaoDTO> listarPorUsuarioDTO(Long idUsuario) {
        return listarPorUsuario(idUsuario).stream()
                .map(AvaliacaoDTO::new)
                .collect(Collectors.toList());
    }
    
    public Page<AvaliacaoDTO> listarPorUsuarioDTO(Long idUsuario, Pageable pageable) {
        Page<Avaliacao> avaliacoesPage = listarPorUsuario(idUsuario, pageable);
        List<AvaliacaoDTO> avaliacoesDTO = avaliacoesPage.getContent().stream()
                .map(AvaliacaoDTO::new)
                .collect(Collectors.toList());
        
        return new PageImpl<>(avaliacoesDTO, pageable, avaliacoesPage.getTotalElements());
    }
    
    public List<AvaliacaoDTO> listarPorProfissionalDTO(Long idProfissional) {
        return listarPorProfissional(idProfissional).stream()
                .map(AvaliacaoDTO::new)
                .collect(Collectors.toList());
    }
    
    public Page<AvaliacaoDTO> listarPorProfissionalDTO(Long idProfissional, Pageable pageable) {
        Page<Avaliacao> avaliacoesPage = listarPorProfissional(idProfissional, pageable);
        List<AvaliacaoDTO> avaliacoesDTO = avaliacoesPage.getContent().stream()
                .map(AvaliacaoDTO::new)
                .collect(Collectors.toList());
        
        return new PageImpl<>(avaliacoesDTO, pageable, avaliacoesPage.getTotalElements());
    }
} 