package com.microservices.match.service.interfaces;

import com.microservices.match.dto.MatchCreateDto;
import com.microservices.match.dto.MatchReadDto;
import com.microservices.match.dto.MatchUpdateDto;

public interface MatchService {

    MatchReadDto findById(Long id);

    MatchReadDto createMatch(MatchCreateDto dto);

    MatchReadDto updateMatch(Long id, MatchUpdateDto dto);

    void deleteMatch(Long id);
}
