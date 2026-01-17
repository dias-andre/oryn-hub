# Oryn Back-End 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Amazon S3](https://img.shields.io/badge/Amazon%20S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)

O projeto é o núcleo do ecossistema Oryn Hub, desenvolvida em Java para fornecer a infraestrutura necessária para comunidades do Discord. O sistema centraliza a gestão de tesouraria, tickets de suporte e a automação de sorteios (giveaways), garantindo escalabilidade e segurança.


## Funcionalidades Principais

O sistema foi desenhado para gerenciar fluxos complexos de comunidades digitais, focando em:

- Gestão de Squads: Criação e administração de grupos/comunidades com hierarquia de membros (Owner, Admin, Moderator, User).

- Sistema de Convites: Gerenciamento dinâmico de convites com limites de uso, pausas e expiração automática.

- Automação de Giveaways: Criação de sorteios com suporte a upload de comprovantes de entrega.

- Autenticação Híbrida: Suporte completo para login via credenciais tradicionais (E-mail/Senha) e integração nativa com OAuth2 Discord.

- Armazenamento de Mídia: Integração robusta para upload de arquivos e provas de sorteios.

## Stack Tecnológica

O projeto utiliza o que há de mais moderno no ecossistema Java para garantir performance e segurança:

- Spring Web: Estrutura base para a construção da API RESTful.

- Spring Security: Implementação rigorosa de autenticação e autorização baseada em JWT.

- AWS SDK (S3Client): Utilizado para a persistência de arquivos, configurado especificamente para operar com o Cloudflare R2, garantindo baixa latência e custo-benefício no armazenamento de objetos.

- OpenAPI 3.1: Documentação padronizada para facilitar o consumo por front-ends e bots.

## Estrutura de Integração (Cloudflare R2)

Para o gerenciamento de provas de sorteios e avatares, o sistema utiliza o protocolo S3 para interagir com o Cloudflare R2.