# Test technique RPN

### Solution
J'ai utilisé la springdoc-openapi, la bibliothèque java qui permet d'automatiser la génération de la documentation de l'API.
La documentation générée est accesible via l'URL http://localhost:8080/swagger-ui-custom.html
Le swagger sous format JSON disponible via l'URL http://localhost:8080/api-docs
Technologies utilisées : SpringBoot 2.7 | JAVA 11 | JUNIT 5 | OPEN API 3
Remarque : pour le stockage des données j'ai utilisé le spring caching donc une sauvegarde des données en cache.

### TODO
Plus de couverture de tests (partie controlleur, model..).
Test d'intégration.
