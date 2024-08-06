package com.example.firstproject.controller;

import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            String email = userDetails.getUsername();
            Member member = memberRepository.findByEmail(email).orElse(null);
            model.addAttribute("member", member);
        }

        List<Member> members = (List<Member>) memberRepository.findAll();
        model.addAttribute("members", members);
        return "home";
    }
}
