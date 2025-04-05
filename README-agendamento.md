# Sistema de Agendamento, Disponibilidade e Avaliação

Este documento explica como funcionam os componentes de agendamento, disponibilidade e avaliação no sistema Inkspiration.

## Estrutura do Sistema

O sistema é composto por três componentes principais:

1. **Disponibilidade do Profissional**: Define os horários regulares de trabalho do profissional (quando está disponível para atendimento)
2. **Agendamento**: Registra os horários específicos reservados para atendimento a um cliente
3. **Avaliação**: Permite que os clientes avaliem os serviços recebidos após um agendamento

## Fluxo de Agendamento

O processo de agendamento segue estes passos:

1. **Verificar disponibilidade do profissional**: Primeiro, o sistema verifica se o profissional trabalha no horário solicitado (dias e horários regulares)
2. **Verificar conflitos de agendamento**: Depois, verifica se o profissional já tem outro agendamento marcado no mesmo horário
3. **Criar agendamento**: Se o profissional estiver disponível e não houver conflitos, o agendamento é criado

## Disponibilidade do Profissional

A disponibilidade é armazenada como um JSON no seguinte formato:

```json
{
  "Segunda": {"inicio": "08:00", "fim": "18:00"},
  "Terça": {"inicio": "09:00", "fim": "19:00"},
  "Quarta": {"inicio": "08:00", "fim": "18:00"},
  "Quinta": {"inicio": "09:00", "fim": "19:00"},
  "Sexta": {"inicio": "08:00", "fim": "16:00"},
  "Sábado": {"inicio": "09:00", "fim": "14:00"}
}
```

### Endpoint para Cadastrar Disponibilidade

```
POST /disponibilidades/profissional/{idProfissional}
```

**Exemplo de Requisição:**

```json
{
  "Segunda": {"inicio": "08:00", "fim": "18:00"},
  "Terça": {"inicio": "09:00", "fim": "19:00"},
  "Quarta": {"inicio": "08:00", "fim": "18:00"},
  "Quinta": {"inicio": "09:00", "fim": "19:00"},
  "Sexta": {"inicio": "08:00", "fim": "16:00"},
  "Sábado": {"inicio": "09:00", "fim": "14:00"}
}
```

### Endpoint para Verificar Disponibilidade

```
GET /disponibilidades/profissional/{idProfissional}/verificar?inicio={dataHoraInicio}&fim={dataHoraFim}
```

**Exemplo de Requisição:**

```
GET /disponibilidades/profissional/1/verificar?inicio=2023-09-20T10:00:00&fim=2023-09-20T11:00:00
```

**Resposta:**

```json
{
  "disponivel": true,
  "mensagem": "O profissional está trabalhando nesse horário"
}
```

## Agendamento

### Endpoint para Criar Agendamento

```
POST /agendamentos
```

**Parâmetros:**
- `idUsuario`: ID do usuário que está fazendo o agendamento
- `idProfissional`: ID do profissional que será agendado
- `tipoServico`: Descrição do serviço a ser realizado
- `dtInicio`: Data e hora de início (ISO 8601)
- `dtFim`: Data e hora de fim (ISO 8601)

**Exemplo de Requisição:**

```
POST /agendamentos?idUsuario=1&idProfissional=2&tipoServico=Tatuagem&dtInicio=2023-09-20T10:00:00&dtFim=2023-09-20T12:00:00
```

**Possíveis Respostas de Erro:**

- Se o profissional não trabalha no horário solicitado:
  ```json
  "O profissional não está disponível para atendimento nesse horário. Por favor, consulte os horários de atendimento do profissional."
  ```

- Se o profissional já tem outro agendamento no mesmo horário:
  ```json
  "O profissional já possui outro agendamento nesse horário. Por favor, selecione outro horário disponível."
  ```

## Fluxo Recomendado para Clientes

1. **Obter disponibilidade do profissional**:
   - Chame `GET /disponibilidades/profissional/{id}` para obter os horários regulares de trabalho

2. **Verificar se o profissional atende no horário desejado**:
   - Chame `GET /disponibilidades/profissional/{id}/verificar` com o horário desejado

3. **Criar o agendamento**:
   - Somente após confirmar a disponibilidade, chame `POST /agendamentos` para criar o agendamento

## Avaliações

Após a realização do serviço, o cliente pode avaliar o profissional:

```
POST /avaliacoes
```

**Parâmetros:**
- `idUsuario`: ID do usuário que está avaliando
- `idAgendamento`: ID do agendamento que está sendo avaliado
- `descricao`: Comentário sobre o serviço
- `rating`: Avaliação de 1 a 5 estrelas

**Requisição de Exemplo:**

```
POST /avaliacoes?idUsuario=1&idAgendamento=123&descricao=Excelente serviço&rating=5
```

**Regras:**
- Um usuário só pode avaliar um agendamento que ele próprio marcou
- Cada agendamento só pode ser avaliado uma vez
- A avaliação afeta automaticamente a nota média do profissional 