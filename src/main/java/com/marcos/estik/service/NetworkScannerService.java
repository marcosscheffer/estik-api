package com.marcos.estik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.common.IpResponseDTO;

@Service
public class NetworkScannerService {
    private IpResponseDTO checkIpStatus(String ip) {
        try {
            // Comando nativo do SO (Windows: ping -n 1 -w 1000 | Linux: ping -c 1 -W 1)
            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
            ProcessBuilder pb = isWindows 
                ? new ProcessBuilder("ping", "-n", "1", "-w", "1000", ip)
                : new ProcessBuilder("ping", "-c", "1", "-W", "1", ip);
                
            Process process = pb.start();
            int returnCode = process.waitFor();
            return new IpResponseDTO(ip, returnCode == 0);
        } catch (Exception e) {
            return new IpResponseDTO(ip, false);
        }
    }

    public List<IpResponseDTO> getIps(String subnet) {
        String baseIp = "192.168." + subnet + ".";
        
        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<CompletableFuture<IpResponseDTO>> futures = new ArrayList<>();

        for (int i = 1; i <= 254; i++) {
            String ip = baseIp + i;
            futures.add(CompletableFuture.supplyAsync(() -> checkIpStatus(ip), executor));
        }

        // Aguarda todas as requisições terminarem e coleta os resultados
        List<IpResponseDTO> ips = futures.stream()
                .map(future -> future.join())
                .collect(Collectors.toList());

        executor.shutdown();

        return ips;
    }


}
