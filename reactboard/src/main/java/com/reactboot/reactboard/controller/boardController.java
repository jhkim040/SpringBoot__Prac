package com.reactboot.reactboard.controller;

import com.reactboot.reactboard.entity.Board;
import com.reactboot.reactboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class boardController {
    @Autowired
    private BoardService boardService;

//    @GetMapping("/board")
//    public String board() {
//        System.out.println("연결 잘됨?");
//        return "연결테스트";
//    }

    @GetMapping("/board/list")
    public List<Board> boardList(){
        return boardService.boardList();
    }

    @PostMapping("/board/insert")
    public void boardInsert(@RequestBody Board board) {
        Board boardTemp = new Board();
        boardService.write(board);

    }

}
