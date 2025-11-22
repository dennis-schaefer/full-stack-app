package de.schaeferd.fullstackapp.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dev")
public class ViteDevServer implements SmartLifecycle
{
    private static final String FRONTEND_DIR = "src/main/frontend";

    private final Logger log = LoggerFactory.getLogger(ViteDevServer.class);
    private Process viteProcess;
    private boolean running = false;

    @Override
    public void start()
    {
        log.info("Starting Vite Dev-Server...");

        try
        {
            var command = new String[] {"sh", "-c", "npm run dev"};
            var isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
            if (isWindows)
                command = new String[] {"cmd.exe", "/c", "npm run dev"};

            var builder = new ProcessBuilder();
            builder.command(command);
            builder.directory(new File(FRONTEND_DIR));
            builder.inheritIO();

            viteProcess = builder.start();
            running = true;

            log.info("Started Vite Dev-Server (PID: {})", viteProcess.pid());

        }
        catch (IOException e)
        {
            log.error("An error occurred while starting the Vite Dev-Server", e);
        }
    }

    @Override
    public void stop()
    {
        if (viteProcess == null || !viteProcess.isAlive())
            return;



        this.viteProcess.toHandle().descendants().forEach(handle ->
        {
            log.debug("Stopping Vite Dev-Sever Sub-Process PID: {}", handle.pid());
            handle.destroy();
        });

        log.info("Stopping Vite Dev-Server...");
        this.viteProcess.destroy();

        try
        {
            if (!this.viteProcess.waitFor(5, TimeUnit.SECONDS))
            {
                log.warn("Vite Process is not responding - Stopping forcefully...");
                this.viteProcess.toHandle().descendants().forEach(ProcessHandle::destroyForcibly);
                this.viteProcess.destroyForcibly();
            }
        }
        catch (InterruptedException e)
        {
            this.viteProcess.destroyForcibly();
            Thread.currentThread().interrupt();
        }

        running = false;
    }

    @Override
    public boolean isRunning()
    {
        return running;
    }

    @Override
    public int getPhase()
    {
        return Integer.MAX_VALUE;
    }
}
