package com.isi.ExamenMricoService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple API - Gestion des Secteurs et Classes pour les nouveau eleve ")
                        .description("""
                API REST pour la gestion des secteurs d'enseignement et de leurs classes associées.

                ## Fonctionnalités principales
                - **Secteurs** : CRUD complet pour les filières/secteurs
                - **Classes** : CRUD complet pour les classes rattachées aux secteurs
                - **Validation** : Contraintes d'intégrité (noms uniques, relations cohérentes)
                - **Gestion d'erreurs** : Réponses JSON standardisées

                ## Relations
                - Un secteur peut avoir plusieurs classes (1:N)
                - Une classe appartient obligatoirement à un secteur
                - La suppression d'un secteur supprime ses classes (cascade)

                ## Base de données
                - PostgreSQL avec JPA/Hibernate
                - Auto-création/mise à jour du schéma en mode développement
                """)
                        .version("1.0.0") // <- fermeture ici
                ) // <- fermer l'info() avant servers()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Environnement de développement local"),
                        new Server()
                                .url("https://api.example.com" + contextPath)
                                .description("Environnement de production (exemple)")
                ));
    }

}
