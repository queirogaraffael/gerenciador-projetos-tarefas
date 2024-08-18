# Sistema de Gerenciamento de Tarefas e Projetos

Este é um sistema para gerenciamento de tarefas e projetos, desenvolvido em Java. Ele permite criar, atualizar, visualizar e excluir projetos e tarefas, e também executar tarefas. O sistema é organizado em três controladores principais:
1. MenuPrincipalController: Gerencia a navegação entre o menu principal e os menus de projetos e tarefas. 
2. MenuProjetosController: Gerencia operações relacionadas a projetos, como criação, atualização, visualização e exclusão. 
3. MenuTarefasController: Gerencia operações relacionadas a tarefas, incluindo criação, atualização, visualização e exclusão.

## Funcionalidades

### MenuPrincipalController:
1. Exibe o menu principal.
2. Gerencia a navegação para menus de gerenciamento de projetos e tarefas.

### MenuProjetosController:
1. Cria novos projetos.
2. Atualiza projetos existentes.
3. Visualiza todos os projetos ou projetos por nome.
4. Remove projetos.
5. Executa tarefas associadas a um projeto.

### MenuTarefasController:
1. Cria novas tarefas.
2. Atualiza tarefas existentes.
3. Visualiza tarefas por projeto, prioridade ou status.
4. Remove e executa tarefas.

## Ferramentas e Tecnologias Utilizadas
1. Java 17: Linguagem de programação utilizada para o desenvolvimento do sistema. 
2. JPA (Java Persistence API): Para o gerenciamento da persistência de dados e interação com o banco de dados. 
3. Hibernate: Implementação da JPA para mapeamento objeto-relacional. 
4. EntityManagerFactory: Gerencia a criação e o gerenciamento de entidades JPA. 
5. Maven: Ferramenta de automação de construção e gerenciamento de dependências do projeto. 
6. Swing: Biblioteca gráfica utilizada para a interface do usuário. 
7. Lombok: Biblioteca para redução de código boilerplate em Java. 
8. PostgreSQL: Banco de dados relacional utilizado para armazenar dados do sistema.
9. Cache: para melhorar a usabilidade do usuario.

## Arquitetura MVC
O projeto segue uma arquitetura Modelo-Visão-Controlador (MVC). Aqui está uma breve descrição de cada componente:

1. Modelo: Processa dados, incluindo leitura e filtragem de projetos e tarefas, e interação com o banco de dados.
2. Visão: Interage com o usuário, exibindo informações e solicitando entradas.
3. Controlador: Coordena a interação entre o Modelo e a Visão, gerenciando o fluxo de dados e as operações.

## Como Executar
1. Clone o repositório para sua máquina local. 
2. Abra o projeto em sua IDE Java. 
3. Certifique-se de ter configurado corretamente o banco de dados PostgreSQL(ou o do seu gosto) e as credenciais de acesso no arquivo persistence.xml. 
4. Compile e execute o projeto a partir da classe Main.

## Contribuição
Contribuições são bem-vindas! Para contribuir com melhorias, abra uma issue ou envie um pull request.