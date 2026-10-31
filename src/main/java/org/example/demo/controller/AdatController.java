package org.example.demo.controller;

import org.example.demo.repository.KategoriaRepository;
import org.example.demo.repository.PizzaRepository;
import org.example.demo.repository.RendelesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.example.demo.entity.Rendeles;

@Controller
public class AdatController {

    private final KategoriaRepository kategoriaRepository;
    private final PizzaRepository pizzaRepository;
    private final RendelesRepository rendelesRepository;

    public AdatController(
            KategoriaRepository kategoriaRepository,
            PizzaRepository pizzaRepository,
            RendelesRepository rendelesRepository) {
        this.kategoriaRepository = kategoriaRepository;
        this.pizzaRepository = pizzaRepository;
        this.rendelesRepository = rendelesRepository;
    }

    @GetMapping("/adatok")
    public String adatok(@RequestParam(defaultValue = "0") int oldal, Model model) {
        int biztonsagosOldal = Math.max(0, oldal);

        model.addAttribute("kategoriak", kategoriaRepository.findAll(Sort.by("nev")));
        model.addAttribute("pizzak", pizzaRepository.findAll(Sort.by("nev")));

        Page<Rendeles> rendelesOldal = rendelesRepository.findAll(
                PageRequest.of(biztonsagosOldal, 25, Sort.by(Sort.Direction.DESC, "felvetel"))
        );
        model.addAttribute("rendelesOldal", rendelesOldal);

        return "adatok";
    }
}