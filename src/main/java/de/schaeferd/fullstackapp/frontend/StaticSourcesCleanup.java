package de.schaeferd.fullstackapp.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Profile("dev")
public class StaticSourcesCleanup
{
    private static final String STATIC_DIR = "src/main/resources/static";
    private final Logger log = LoggerFactory.getLogger(StaticSourcesCleanup.class);

    @Bean
    CommandLineRunner cleanStaticResources()
    {
        return _ ->
        {
            log.info("Cleaning static resources directory...");
            Path staticPath = Paths.get(STATIC_DIR);

            if (Files.exists(staticPath) && Files.isDirectory(staticPath))
                deleteDirectoryContents(staticPath);
        };
    }


    private void deleteDirectoryContents(Path directory) throws IOException
    {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(directory))
        {
            for (Path entry : files)
            {
                if (Files.isDirectory(entry))
                    deleteDirectoryContents(entry);
                else
                    Files.delete(entry);
            }
        }

        Files.delete(directory);
    }
}
