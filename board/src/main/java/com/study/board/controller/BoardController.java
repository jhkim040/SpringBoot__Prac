package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    // 글 작성
    @GetMapping("/board/write") // localhost:8090/board/write
    public String boardWriteForm() {
        return "boardWrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model) {
        boardService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        // model.addAttribute("message", "글 작성이 실패하였습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    // 게시글 리스트
    @GetMapping("/board/list") // page : default page, size : 한 페이지 게시글 개수, sort : 정렬 기준 컬럼, direction : 정렬 순서
    public String boardList(Model model,
                            @PageableDefault(page=0, size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
    String searchKeyword) {

        Page<Board> list = null;
        if(searchKeyword == null) { // 검색 아닐 때
           list = boardService.boardList(pageable);
        } else { // 검색일 때
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        // nowPage : 현재 페이지
        // startPage : 블럭에서 보여줄 시작 페이지
        // endPage : 블럭에서 보여줄 마지막 페이지
        int nowPage = list.getPageable().getPageNumber() + 1; // pageable의 페이지는 0에서 시작
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage", endPage);
        return "boardList";
    }

    // 게시글 상세
    @GetMapping("/board/view") // localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id) {
    model.addAttribute("board", boardService.boardView(id));
       return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardModify(@PathVariable("id") Integer id, Board board, Model model){
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        // model.addAttribute("message", "글 수정이 실패하였습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }
}
