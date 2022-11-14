# Test technique RPN

### Road map
- Pour la couche persistence, utiliser une base de données (non relationelle) comme MongoDB par exemple. D'où spring-data-mongodb.

- Ou bien, rajouter un historique des opérations, des utilisateurs, une base relationelle comme postgreSQL. spring-data-jpa
    - Lister les operations sur une pile pendant une période.
    - Lister les opérations faites par un utilisateur sur une pile.

- Sécurisation de l'API moyennant des controles de sécurité (Scopes, habilitations des appelants).
- Implémenter une architecture DDD (hexagonale par exemple) afin de séparer la partie métier des autres partie (infra, rest) et la rendre accessible moyennant des ports.
