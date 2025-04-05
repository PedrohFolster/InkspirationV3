package inkspiration.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import inkspiration.backend.entities.Agendamento;
import inkspiration.backend.entities.Avaliacao;
import inkspiration.backend.entities.Profissional;
import inkspiration.backend.entities.Usuario;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByUsuario(Usuario usuario);
    List<Avaliacao> findByProfissional(Profissional profissional);
    Page<Avaliacao> findByUsuario(Usuario usuario, Pageable pageable);
    Page<Avaliacao> findByProfissional(Profissional profissional, Pageable pageable);
    Optional<Avaliacao> findByAgendamento(Agendamento agendamento);
    boolean existsByAgendamento(Agendamento agendamento);
    
    // Calcular a média de avaliações de um profissional
    @Query("SELECT AVG(a.rating) FROM Avaliacao a WHERE a.profissional.idProfissional = :profissionalId")
    Double calculateAverageRatingByProfissional(@Param("profissionalId") Long profissionalId);
    
    // Contar o número de avaliações de um profissional
    @Query("SELECT COUNT(a) FROM Avaliacao a WHERE a.profissional.idProfissional = :profissionalId")
    Long countByProfissionalId(@Param("profissionalId") Long profissionalId);
} 