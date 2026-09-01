package com.marcos.estik.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.HeartbeatResponseDTO;
import com.marcos.estik.domain.dto.HeartbeatRequestDTO;
import com.marcos.estik.domain.dto.HeatbeatDTO;
import com.marcos.estik.domain.enums.StatusEnum;

@Service
public class HeartbeatService {
    private final Map<String, HeatbeatDTO> currentPcs = new ConcurrentHashMap<>();

    public void pingHeartbeat(HeartbeatRequestDTO dto) {
        HeatbeatDTO heartbeat = new HeatbeatDTO(
            dto.ip(),
            LocalDateTime.now()
        );
        currentPcs.put(dto.name(), heartbeat);
    }

    public List<HeartbeatResponseDTO> getHeartBeat() {
        LocalDateTime expire = LocalDateTime.now().minusMinutes(5);
        return currentPcs.entrySet().stream()
            .map(entry -> {
                String name = entry.getKey();
                HeatbeatDTO value = entry.getValue();

                StatusEnum status = value.lastPing().isBefore(expire)
                    ? StatusEnum.OFFLINE
                    : StatusEnum.ONLINE;

                return new HeartbeatResponseDTO(name, value.ip(), status);
            }).toList();
    }

    
}
