package org.example.eventsourcingcqrsspringaxonapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    @Primary // Dit à Spring : "Utilise celui-là par défaut pour tout Axon"
    public Serializer axonSerializer() {
        // 1. Configurer l'ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. Définir le validateur de types (Sécurité)
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class) // Autorise tout (ou restreins à tes packages)
                .build();

        // 3. Activer le "Default Typing"
        // Cela ajoute le type de la classe dans le JSON pour que Axon s'y retrouve
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        // 4. Construire le Serializer Axon avec cet ObjectMapper
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .lenientDeserialization() // Évite de planter si un champ manque
                .build();
    }
}