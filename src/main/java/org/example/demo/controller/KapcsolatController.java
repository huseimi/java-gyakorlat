package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.entity.KapcsolatUzenet;
import org.example.demo.repository.KapcsolatUzenetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class KapcsolatController {

    private final KapcsolatUzenetRepository repository;

    public KapcsolatController(KapcsolatUzenetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/kapcsolat")
    public String kapcsolat(Model model) {
        model.addAttribute("kapcsolatUzenet", new KapcsolatUzenet());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String uzenetKuldes(
            @Valid @ModelAttribute("kapcsolatUzenet") KapcsolatUzenet uzenet,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "kapcsolat";
        }

        uzenet.setKuldesIdeje(LocalDateTime.now());
        repository.save(uzenet);

        return "redirect:/kapcsolat?elkuldve";
    }

    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {
        model.addAttribute(
                "uzenetek",
                repository.findAll(Sort.by(Sort.Direction.DESC, "kuldesIdeje"))
        );
        return "uzenetek";
    }
}