# Automação para processo de criação de reservas
- ## Diagrama detalhado de ações do usuário
![Diagrama](./public/diagramaAcoesUsuario.drawio.png)
- ## Arquitetura Global
## **Criação de RPA** - Automação de criação de Reservas
- ### Descrição da aplicação
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
        subgraph EXT[EXTERNOS]
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
    API1 <--"<b>2.</b>" GET/ Status das NFEs--> DB
    API1 <--"<b>3.</b>" GET/ XMLs das NFEs--> API2
    API2 ~~~ API1
    API2 <--"<b>4.</b>" Consultar XMLs--> SFZ
    API2 --"<b>5.</b>" Salvar dados dos XMLs--> DB
    API1 --"<b>6.</b>" Dados dos XMLs--> API3
    API3 <--"<b>7.</b>"Realizar ações RPA--> PLF
    API3 --"<b>8.</b>" Salvar Status das NFEs--> DB
    AV ~~~ API2
    

```
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    - [TypeScript](https://www.typescriptlang.org/)
    >Linguagem de programação, baseada em JavaScript, utiliziada juntamente com node.js.
    - [Node.js](https://nodejs.org/pt-br)
    >Compilador de javascript, tem como finalidade executar código JS no lado do servidor, que é compilado pelo motor V8 do chrome.
    - [Nest.js](https://nestjs.com/)
    >Framework para facilitar a criação de APIs e WebHooks, e possui muitas outras finalidades, como a aplicação de injeção de dependências.
    - [Playwright](https://playwright.dev/docs/input)
    >Biblioteca da Microsoft para criação de teste automatizados, assim como o selenium, também cria RPAs.

## **Criação de API** - Busca de XMLs
- ### Descrição da aplicação
- ### Ferramentas utilizadas e Pré-Requisitos Globais

