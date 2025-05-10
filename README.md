# Aura General Backend

Este é o backend principal do projeto **Aura**, desenvolvido pelo grupo 8. Ele fornece APIs e integrações para gerenciar usuários, serviços, agendamentos e notificações.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.3**
- **H2 Database** (banco de dados em memória)
- **JWT** (JSON Web Token) para autenticação
- **Twilio API** para envio de mensagens via WhatsApp
- **Spring Security** para segurança
- **Spring Validation** para validação de dados
- **Spring Mail** para envio de e-mails
- **Swagger/OpenAPI** para documentação da API

## Funcionalidades

- **Gerenciamento de Usuários**:
  - Registro, login, atualização e exclusão de usuários.
  - Autenticação com JWT.
  
- **Serviços**:
  - Listagem e gerenciamento de serviços disponíveis.

- **Agendamentos**:
  - Criação e listagem de agendamentos.
  - Integração com serviços e usuários.

- **Mensagens**:
  - Envio de mensagens via WhatsApp para os usuários.
  - Envio de tokens para redefinição de senha via e-mail.

## Configuração do Ambiente

### Pré-requisitos
- **Java 21** instalado.
- **Maven** configurado.

### Variáveis de Ambiente
Configure as seguintes variáveis no arquivo `application.properties` ou como variáveis de ambiente:

- **JWT**:
  - `JWT`: Chave secreta para geração de tokens JWT.
- **Twilio**:
  - `TWILIO_SID`: SID da conta Twilio.
  - `TWILIO_AUTH`: Token de autenticação da Twilio.
- **E-mail**:
  - `EMAIL_USERNAME`: Endereço de e-mail usado para envio.
  - `EMAIL_PASSWORD`: Senha do e-mail configurado para envio.

### Executando o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/AURA-Group-8/aura-general-backend.git
   cd aura-general-backend
2. Compile e execute o projeto
3. Acesse a aplicação:  
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console