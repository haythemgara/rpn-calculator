# Test technique RPN

### Road map
- Pour la couche persistence, utiliser une base de donn�es (non relationelle) comme MongoDB par exemple. D'o� spring-data-mongodb.

- Ou bien, rajouter un historique des op�rations, des utilisateurs, une base relationelle comme postgreSQL. spring-data-jpa
    - Lister les operations sur une pile pendant une p�riode.
    - Lister les op�rations faites par un utilisateur sur une pile.

- S�curisation de l'API moyennant des controles de s�curit� (Scopes, habilitations des appelants).
- Impl�menter une architecture DDD (hexagonale par exemple) afin de s�parer la partie m�tier des autres partie (infra, rest) et la rendre accessible moyennant des ports.
