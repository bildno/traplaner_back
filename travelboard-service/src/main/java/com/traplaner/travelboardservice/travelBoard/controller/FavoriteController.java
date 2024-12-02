package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.MemberDTO;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/topThree")
    public ResponseEntity<List<FavoriteDTO>> getTopThreeFavorites() {
        List<FavoriteDTO> topThreeFavorites = favoriteService.getTopThree();
        return ResponseEntity.ok(topThreeFavorites);
    }

    @PostMapping("/{id}/toggle-like")
    @ResponseBody
    public ResponseEntity<Integer> toggleLike(@PathVariable int id, HttpSession session) {
        MemberDTO dto = (MemberDTO) session.getAttribute("login");

        int likeCount = favoriteService.toggleLike(id, dto.getId());

        return ResponseEntity.ok(likeCount);
    }
}
