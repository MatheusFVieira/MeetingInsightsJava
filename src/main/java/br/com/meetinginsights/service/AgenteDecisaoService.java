package br.com.meetinginsights.service;

import br.com.meetinginsights.domain.AnaliseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AgenteDecisaoService {

    public AnaliseResponse analisar(String transcricao) {
        try {
            Path tempFile = Files.createTempFile("transcricao_", ".txt");
            Files.writeString(tempFile, transcricao, StandardCharsets.UTF_8);

            ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\Vieira\\Downloads\\Challenge\\AgenteAI.py", tempFile.toAbsolutePath().toString());

            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            boolean jsonStarted = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{")) jsonStarted = true;
                if (jsonStarted) output.append(line);

                // Agora você verá exatamente onde o Python está quebrando
                System.out.println("[Python] " + line);
            }

            process.waitFor();
            Files.deleteIfExists(tempFile);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(output.toString(), AnaliseResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}