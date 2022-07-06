package com.project.blog.controller;


import com.project.blog.model.Board;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.BoardService;
import com.project.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/", ""})
    public String index(Model model,
                        @PageableDefault(size=5,sort="id", direction = Sort.Direction.DESC) Pageable pageable,
                        String searchKeyword){

        Page<Board> list = null;

        if(searchKeyword == null){
            list = boardService.글목록(pageable);
        }else {
            list = boardService.글찾기(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //페이지에서 넘어온 현재 페이지를 가져옴
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        //페이지 수가 넘어가게 되는 경우 이동하기가 어렵기 때문에 전체 페이지 수를 받아와서 전체 페이지랑 비교

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index"; //viewResolver 작동
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){

        model.addAttribute("board", boardService.글상세보기(id));

        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){

        return "board/saveForm";
    }

    @GetMapping("/board/modify/{id}")
    public String ModifyForm(@PathVariable("id") int id, Model model){

        model.addAttribute("board", boardService.글상세보기(id));

        return "board/modifyForm";
    }

    @GetMapping("/board/delete/{id}")
    public void deleteForm(@PathVariable("id") int id){

        boardService.글삭제(id);
    }

    @GetMapping("/{username}")
    public String findMyPost(@PathVariable("username") String username,
                             @PageableDefault(size=5,sort="id", direction = Sort.Direction.DESC) Pageable pageable, Model model){

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. :"+username));

        int userid = user.getId();

        Page<Board> list = boardService.내글찾기(userid, pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        //페이지 수가 넘어가게 되는 경우 이동하기가 어렵기 때문에 전체 페이지 수를 받아와서 전체 페이지랑 비교

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/board/myPost";
    }
}
