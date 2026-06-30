# OopQuest

Jogo top-down 2D, construído em Java com [libGDX](https://libgdx.com/). O jogador anda por três mapas, interagindo com NPCs e inimigos para resolver quizzes sobre Programação Orientada a Objetos.

Como o projeto ainda não possui artes, os placeholders são formas geométricas nativas do libGDX.

![Gameplay do OopQuest](images/gameplay.gif)

## Sobre o projeto

Foi desenvolvido do zero usando o [OpenSpec](https://github.com/Fission-AI/OpenSpec), uma ferramenta de desenvolvimento orientado a especificações com IA. O projeto foi criado como um teste da ferramenta e seu fluxo de desenvolvimento. Tempo total: 4 dias. Todas as features planejadas no escopo inicial foram entregues.

## Processo de desenvolvimento

Antes de começar com o código, escrevi [`docs/game-design.md`](docs/game-design.md) com o conceito e as mecânicas centrais do jogo para guiar a IA durante o desenvolvimento.

A partir daí segui o fluxo do OpenSpec. Começava utilizando `explore` para discutir cada ideia de task com a IA, que sugeria várias maneiras de construir a feature. Depois seguia o fluxo obrigatório, 
 com `propose` → `apply` → `archive` para formalizar, implementar e arquivar a task. O `explore` é opcional, mas decidi utilizá-lo em todas as tasks para comparar diferentes abordagens antes de iniciar a implementação.

Também utilizei o explore para fazer revisões periódicas, pois, a cada task realizada, o código ficava mais complexo e a IA passava a gerar pequenos erros e decisões de implementação que não seguiam as melhores práticas. Nessas situações, utilizava o próprio explore para auditar o código e discutir as melhorias possíveis, para então aplicar as refatorações.

Mantive as tasks pequenas de propósito, pois tinha receio de que a IA perdesse o contexto em escopos maiores. No fim, isso nunca aconteceu, mas foi assim que organizei o desenvolvimento.

A validação do projeto foi baseada principalmente em testes de comportamento e em auditorias conduzidas com auxílio da IA, em vez de uma revisão manual completa do código.

## Stack

- Java 21 
- libGDX 1.14.2 
- LWJGL3 
- OpenSpec

## Mais detalhes

- [`docs/game-design.md`](docs/game-design.md) — design original do jogo
- [`openspec/changes/archive`](openspec/changes/archive) — histórico de specs de cada feature implementada
