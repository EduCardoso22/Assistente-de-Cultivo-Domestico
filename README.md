# Assistente de Cultivo Doméstico

Projeto desenvolvido para a disciplina de Engenharia de Software I do IFSC. O sistema é executado exclusivamente via Interface de Linha de Comando (CLI) no terminal e tem como foco assegurar uma experiência textual robusta e interativa para entusiastas de jardinagem e botânicos amadores.

## Funcionalidades Principais

* **Menu Interativo:** Navegação via menu principal em modo texto amigável e com tratamento estrito de exceções para evitar falhas de digitação.
* **Registro de Plantas:** Permite cadastrar espécies, apelidos e datas de aquisição através do console.
* **Alertas Automáticos:** Cálculo e exibição na inicialização do sistema dos alertas pendentes de rega, poda e adubação, baseados em parâmetros biológicos.
* **Diário de Cultivo:** Registro manual de atividades e manutenções efetuadas nas plantas.
* **Painel Geral:** Impressão de uma tabela estruturada no terminal com o estado de saúde e histórico do cultivo.

---

## Arquitetura e Princípios de Projeto

O software foi desenvolvido priorizando o baixo acoplamento e a alta coesão, permitindo fácil manutenibilidade e separação total entre as regras de negócio e o console. Os seguintes princípios foram aplicados:

* **SOLID:** Implementação integral dos princípios, como o *Single Responsibility Principle* para separação de regras botânicas e apresentação, e o *Dependency Inversion Principle* para isolar mecanismos concretos de leitura de arquivos.
* **Ocultação de Informação:** Atributos de classes isolados com modificadores privados e manipulação através de métodos controladores.
* **Composição em vez de Herança:** Utilização de composição dinâmica para evitar hierarquias rígidas (ex: a planta "tem uma" espécie).
* **Lei de Demeter:** Restrição de invocações apenas a dependências diretas, blindando o impacto de modificações em cadeia.

---

## Testes de Software

O ecossistema de testes foca na precisão lógica das regras de negócios e segurança sob condições de erro.

* **Testes Unitários:** Direcionados à validação do motor de rega para evitar cálculos negativos de calendário e na avaliação dos validadores de texto contra entradas inválidas.
* **Objetos Simulados (Mocks):** Utilização de Mocks de persistência de dados (gravando em memória volátil em vez de discos) e Mocks de emissão de alertas para isolar as rotinas do console.

---

## Metodologia e Processo de Software

O ciclo de desenvolvimento ocorreu sob um fluxo iterativo, adotando a metodologia ágil **Scrum** para promover resposta rápida e adaptativa a requisitos.

* **Ciclos:** Organização em *Sprints* de uma semana.
* **Cerimônias Praticadas:** *Sprint Planning* para refinamento e seleção do *Backlog*, *Daily Scrum* restrito a 15 minutos para alinhamento diário e *Sprint Review* para demonstração do incremento de texto.

---

## 👥 A Equipe

A estrutura do projeto foi mantida por uma equipe auto-organizada:

| Membro | Papel | Responsabilidades |
| --- | --- | --- |
| **Damares Gaia** | Product Owner & Dev | Gestão/priorização do *Product Backlog* e desenvolvimento técnico. |
| **Talles Souza** | Scrum Master & Dev | Garantir práticas do framework, remover impedimentos e desenvolvimento. |
| **Eduardo Cardoso** | Desenvolvedor | Projetar a arquitetura, desenhar diagramas, codificar e implementar testes. |