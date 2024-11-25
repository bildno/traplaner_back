package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.LoginUserResponseDTO;
import com.traplaner.travelboardservice.travelBoard.dto.SearchDTO;
import com.traplaner.travelboardservice.travelBoard.service.TravelBoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/travelboard")
@RequiredArgsConstructor
@Slf4j
public class TravelBoardController {

    private final TravelBoardService travelBoardService;

    @GetMapping("/list")
    public String list(Model model, @ModelAttribute("s") SearchDTO page) {

        Map<String, Object> map = travelBoardService.getList(page);
        model.addAttribute("tbList", map.get("tbList"));
        model.addAttribute("maker", map.get("pm"));
        return "travelBoard/list";
    }

    @GetMapping("/info/{id}")
    public String info(Model model, @PathVariable("id") int id, HttpSession session) {
        Map<String, Object> one = travelBoardService.getOne(id, session);
        model.addAttribute("tOne", one.get("tOne"));
        model.addAttribute("journey", one.get("journey"));
        model.addAttribute("likeFlag", one.get("likeFlag"));
        return "travelBoard/info";
    }

    @PostMapping("/{id}/toggle-like")
    @ResponseBody
    public ResponseEntity<Integer> toggleLike(@PathVariable int id, HttpSession session) {
        LoginUserResponseDTO dto = (LoginUserResponseDTO)session.getAttribute("login");
        log.info(String.valueOf(dto.getId()));
        int likeCount = travelBoardService.toggleLike(id, dto.getId());
        return ResponseEntity.ok(likeCount);  // 단일 정수 값 반환
    }


}
