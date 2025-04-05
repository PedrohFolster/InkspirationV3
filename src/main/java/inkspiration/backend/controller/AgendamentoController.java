package inkspiration.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inkspiration.backend.dto.AgendamentoDTO;
import inkspiration.backend.entities.Agendamento;
import inkspiration.backend.service.AgendamentoService;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    
    private final AgendamentoService agendamentoService;
    
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }
    
    @PostMapping
    public ResponseEntity<?> criarAgendamento(
            @RequestParam Long idUsuario,
            @RequestParam Long idProfissional,
            @RequestParam String tipoServico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtFim) {
        
        try {
            Agendamento agendamento = agendamentoService.criarAgendamento(
                    idUsuario, idProfissional, tipoServico, dtInicio, dtFim);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AgendamentoDTO(agendamento));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            
            // Melhorar mensagens de erro para o cliente
            if (errorMessage.contains("não está trabalhando nesse horário")) {
                return ResponseEntity.badRequest().body(
                        "O profissional não está disponível para atendimento nesse horário. " +
                        "Por favor, consulte os horários de atendimento do profissional.");
            } else if (errorMessage.contains("já possui outro agendamento")) {
                return ResponseEntity.badRequest().body(
                        "O profissional já possui outro agendamento nesse horário. " +
                        "Por favor, selecione outro horário disponível.");
            }
            
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AgendamentoDTO agendamentoDTO = agendamentoService.buscarPorIdDTO(id);
            return ResponseEntity.ok(agendamentoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(
            @PathVariable Long idUsuario,
            @RequestParam(defaultValue = "0") int page) {
        
        try {
            Pageable pageable = PageRequest.of(page, 10);
            Page<AgendamentoDTO> agendamentosPage = agendamentoService.listarPorUsuarioDTO(idUsuario, pageable);
            return ResponseEntity.ok(agendamentosPage.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/profissional/{idProfissional}")
    public ResponseEntity<?> listarPorProfissional(
            @PathVariable Long idProfissional,
            @RequestParam(defaultValue = "0") int page) {
        
        try {
            Pageable pageable = PageRequest.of(page, 10);
            Page<AgendamentoDTO> agendamentosPage = agendamentoService.listarPorProfissionalDTO(idProfissional, pageable);
            return ResponseEntity.ok(agendamentosPage.getContent());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/profissional/{idProfissional}/periodo")
    public ResponseEntity<?> listarPorProfissionalEPeriodo(
            @PathVariable Long idProfissional,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        try {
            List<AgendamentoDTO> agendamentoDTOs = agendamentoService.listarPorProfissionalEPeriodoDTO(
                    idProfissional, inicio, fim);
            return ResponseEntity.ok(agendamentoDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAgendamento(
            @PathVariable Long id,
            @RequestParam String tipoServico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtFim) {
        
        try {
            Agendamento agendamento = agendamentoService.atualizarAgendamento(
                    id, tipoServico, dtInicio, dtFim);
            return ResponseEntity.ok(new AgendamentoDTO(agendamento));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            
            // Melhorar mensagens de erro para o cliente
            if (errorMessage.contains("não está trabalhando nesse horário")) {
                return ResponseEntity.badRequest().body(
                        "O profissional não está disponível para atendimento nesse horário. " +
                        "Por favor, consulte os horários de atendimento do profissional.");
            } else if (errorMessage.contains("já possui outro agendamento")) {
                return ResponseEntity.badRequest().body(
                        "O profissional já possui outro agendamento nesse horário. " +
                        "Por favor, selecione outro horário disponível.");
            }
            
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirAgendamento(@PathVariable Long id) {
        try {
            agendamentoService.excluirAgendamento(id);
            return ResponseEntity.ok("Agendamento excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 