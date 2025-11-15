package dev.aksifar.workshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static dev.aksifar.workshop.util.Constants.SLASH;
import static dev.aksifar.workshop.util.Constants.VECTOR_STORE_NAME;

@Slf4j
@Configuration
public class RagConfiguration {

    @Value("classpath:/data/models.json")
    private Resource models;

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vectorStoreFile = getVectorStoreFile();

        if (vectorStoreFile.exists()) {
            log.info("Vector store already exist at {}", vectorStoreFile.getAbsolutePath());
            simpleVectorStore.load(vectorStoreFile);
        } else { //creating new vector store from models.json
            log.info("Creating new vector store at {}", vectorStoreFile.getAbsolutePath());
            TextReader textReader = new TextReader(models);
            textReader.getCustomMetadata().put("fileName", "models.txt");
            List<Document> documents = textReader.get();

            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = tokenTextSplitter.apply(documents);

            simpleVectorStore.add(splitDocuments);
            simpleVectorStore.save(vectorStoreFile);

        }
        return simpleVectorStore;
    }


    private File getVectorStoreFile() {
        Path path = Paths.get("src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath() + SLASH + VECTOR_STORE_NAME;
        log.info("Getting Vector store path: {}", absolutePath);
        return new File(absolutePath);
    }
}