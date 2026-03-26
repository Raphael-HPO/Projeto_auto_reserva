# Automação para processo de criação de reservas
## Objetivos e Arquitetura do projeto
- ## Descrição do projeto
    - Para esse projeto temos como objetivo automatizar o processo de automação de criação de reservas e para isso é necessário:

        1. Busca das NFEs disponíveis para a abertura de reserva;
        2. Busca do XML correspondente ao código da NFE;
        3. Com esses dados em mãos criar a reserva no site específico utilizando o RPA.
        
        > Durante esses processos o objetivo é salvar os XMLs em um banco local até o final do processo para caso seja necessário a reutilização da nota, ela esteja disponível.
    
- ## Diagrama detalhado de ações do usuário
![Diagrama](./public/diagramaAcoesUsuario.drawio.png)
- ## Diagrama da Arquitetura Global
```mermaid
flowchart LR

    classDef app           fill:#4a0e0e,stroke:#ff5555,color:#fff
    classDef externos      fill:#0d47a1,stroke:#42a5f5,color:#fff
    classDef armazenamento fill:#1b5e20,stroke:#66bb6a,color:#fff

    subgraph ARQ[ARQUITETURA COMPLETA]
        direction LR
        API1[GERENTE]:::app

        subgraph APPs[SERVIÇOS]
            direction TB
                API2[API XMLs]:::app
                API3[API RPA]:::app
                API3 ~~~ API2
        end
        style APPs fill:#260a0a,stroke:#ff5555,color:#fff,stroke-width:2px
        subgraph EXT[CONSULTAS EXTERNAS]
            direction TB
                SFZ[SEFAZ]:::externos
                PLF[SITE]:::externos
                BI[POWER BI]:::externos
                BI ~~~ SFZ ~~~ PLF  
        end
        style EXT fill:#051e3e,stroke:#42a5f5,color:#fff,stroke-width:2px
        subgraph AMZ[ARMAZENAMENTO]
            direction RL
                DB[(BANCO DE DADOS)]:::armazenamento
                AV[ARM. TEMP. XML]:::armazenamento
                DB ~~~ AV
        end
        style AMZ fill:#0a2610,stroke:#66bb6a,color:#fff,stroke-width:2px
        API1~~~EXT~~~APPs~~~AMZ
    end
    style ARQ fill:#121212,stroke:#326ce5,color:#fff,stroke-width:4px,stroke-dasharray: 5 5
    

    API1 <--"<b>1.</b>" GET/ NFEs disponíveis--> BI
    API1 --"<b>2.</b>" GET/ Status das NFEs--> DB
    API1 <--"`<b>3.</b> GET/ XMLs das NFEs
    <b>7.</b> Retorno da requisição`"--> API2
    API2 ~~~ API1
    API2 ~~~ 
    API2 <--"<b>4.</b>" Consultar XMLs--> SFZ
    API2 --"<b>6.</b>" Salvar dados dos XMLs--> DB
    API1 --"<b>8.</b>" POST/Dados dos XMLs--> API3
    API3 <--"<b>9.</b>"Realizar ações RPA--> PLF
    API3 --"<b>10.</b>" Salvar Status das NFEs--> DB
    AV ~~~ API2
    

```
## **Criação de RPA** - Automação de criação de Reservas
- ### Descrição da aplicação
    Responsável pela automação do processo de criação de reservas no site vitge - cliente **ARUAJO**.
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    | Ferramenta | Descrição Ferramenta |
    |:-----------|:---------------------|
    |[TypeScript](https://www.typescriptlang.org/)| Superset do JavaScript que adiciona tipagem estática e recursos modernos, aumentando a segurança e escalabilidade do código. |
    |[Node.js](https://nodejs.org/pt-br)| Ambiente de execução JavaScript assíncrono, baseado no motor V8 do Chrome, que permite a execução de código no lado do servidor. |
    |[NestJS](https://nestjs.com/)| Framework progressivo para Node.js focado em modularidade e injeção de dependências, facilitando a criação de aplicações escaláveis e testáveis. |
    |[Playwright](https://playwright.dev/)| Biblioteca da Microsoft para automação de navegadores (Chromium, Firefox e WebKit) através de uma API unificada, ideal para criação de RPAs robustos. |
    |[Prisma ORM](https://www.prisma.io/)| ORM (Object-Relational Mapper) de próxima geração para Node.js e TypeScript, que simplifica a manipulação do banco de dados com total segurança de tipos. |
    |[Dotenv](https://www.npmjs.com/package/dotenv)| Módulo para carregar variáveis de ambiente de um arquivo `.env` para o `process.env`, essencial para gerenciar credenciais de acesso de forma segura. |
    |[Docker](https://www.docker.com/)| Plataforma de conteinerização que permite empacotar a aplicação e suas dependências, garantindo que o RPA rode de forma idêntica em qualquer ambiente. |

## **API NF-e** - Captura de XMLs
- ### Descrição da aplicação
    Responsável pela busca dos XMLs correspondentes as NFs que serão disponibilizadas para esta aplicação atravez de comunicação HTTP.
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    | Ferramenta | Descrição Ferramenta |
    |:-----------|:---------------------|
    |[Java 21](https://www.oracle.com/java/)| Linguagem de programação robusta e de alta performance, utilizada para o desenvolvimento do core da aplicação e lógica de orquestração. |
    |[Spring Boot 3](https://spring.io/projects/spring-boot)| Framework para facilitar a criação de aplicações Java autossuficientes, com configuração automática e servidor Tomcat embarcado. |
    |[Spring Data JPA](https://spring.io/projects/spring-data-jpa)| Abstração para persistência de dados que simplifica o acesso ao banco de dados e as operações de CRUD através do Hibernate. |
    |[PostgreSQL](https://www.postgresql.org/)| Sistema de gerenciamento de banco de dados relacional (SGBD) focado em integridade de dados e conformidade com padrões SQL. |
    |[OpenFeign](https://spring.io/projects/spring-cloud-openfeign)| Cliente HTTP declarativo utilizado para realizar as requisições de consulta ao Power BI e aos WebServices da SEFAZ de forma simplificada. |
    |[Java-Dotenv](https://github.com/cdimascio/dotenv-java)| Biblioteca para o gerenciamento de variáveis de ambiente (.env), garantindo a segurança de credenciais, certificados e chaves de acesso. |
    |[Maven](https://maven.apache.org/)| Gerenciador de dependências e automação de build, padronizando o ciclo de vida do desenvolvimento e compilação do projeto. |

## **API de Gerenciamento** - Controle das APIs e Consultas (Em Desenvolvimento)
- ### Descrição da aplicação
    Controle das aplicação de acordo com cada ação e reação.
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    | Ferramenta | Descrição Ferramenta |
    |:-----------|:---------------------|
    |[Java 21](https://www.oracle.com/java/)| Linguagem de programação robusta e de alta performance, utilizada para o desenvolvimento do core da aplicação e lógica de orquestração. |
    |[Spring Boot 3](https://spring.io/projects/spring-boot)| Framework para facilitar a criação de aplicações Java autossuficientes, com configuração automática e servidor Tomcat embarcado. |
    |[Spring Data JPA](https://spring.io/projects/spring-data-jpa)| Abstração para persistência de dados que simplifica o acesso ao banco de dados e as operações de CRUD através do Hibernate. |
    |[PostgreSQL](https://www.postgresql.org/)| Sistema de gerenciamento de banco de dados relacional (SGBD) focado em integridade de dados e conformidade com padrões SQL. |
    |[OpenFeign](https://spring.io/projects/spring-cloud-openfeign)| Cliente HTTP declarativo utilizado para realizar as requisições de consulta ao Power BI e aos WebServices da SEFAZ de forma simplificada. |
    |[Java-Dotenv](https://github.com/cdimascio/dotenv-java)| Biblioteca para o gerenciamento de variáveis de ambiente (.env), garantindo a segurança de credenciais, certificados e chaves de acesso. |
    |[Maven](https://maven.apache.org/)| Gerenciador de dependências e automação de build, padronizando o ciclo de vida do desenvolvimento e compilação do projeto. |

