package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository repository;


    public List<RoomTypeResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(rt -> new RoomTypeResponseDTO(rt.getId(), rt.getType(), rt.getPrice(), rt.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public RoomTypeResponseDTO create(RoomTypeRequestDTO dto) {
        RoomType roomType = new RoomType();
        roomType.setType(dto.getType());
        roomType.setPrice(dto.getPrice());
        roomType.setDescription(dto.getDescription());
        RoomType saved = repository.save(roomType);
        return new RoomTypeResponseDTO(saved.getId(), saved.getType(), saved.getPrice(), saved.getDescription());
    }

    @Override
    public RoomTypeResponseDTO update(int id, RoomTypeRequestDTO dto) {
        RoomType rt = repository.findById(id).orElseThrow();
        rt.setType(dto.getType());
        rt.setPrice(dto.getPrice());
        rt.setDescription(dto.getDescription());
        RoomType saved = repository.save(rt);
        return new RoomTypeResponseDTO(saved.getId(), saved.getType(), saved.getPrice(), saved.getDescription());
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}

