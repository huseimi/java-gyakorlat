package org.example.demo.controller;

import org.example.demo.entity.Kategoria;
import org.example.demo.entity.Pizza;
import org.example.demo.entity.Rendeles;
import org.example.demo.repository.KategoriaRepository;
import org.example.demo.repository.PizzaRepository;
import org.example.demo.repository.RendelesRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional(readOnly = true)
public class ApiController {

    private final KategoriaRepository kategoriaRepository;
    private final PizzaRepository pizzaRepository;
    private final RendelesRepository rendelesRepository;

    public ApiController(
            KategoriaRepository kategoriaRepository,
            PizzaRepository pizzaRepository,
            RendelesRepository rendelesRepository) {
        this.kategoriaRepository = kategoriaRepository;
        this.pizzaRepository = pizzaRepository;
        this.rendelesRepository = rendelesRepository;
    }

    @GetMapping("/kategoriak")
    public List<KategoriaValasz> kategoriak() {
        return kategoriaRepository.findAll(Sort.by("nev"))
                .stream()
                .map(kategoria -> new KategoriaValasz(
                        kategoria.getNev(),
                        kategoria.getAr()))
                .toList();
    }

    @GetMapping("/pizzak")
    public List<PizzaValasz> pizzak() {
        return pizzaRepository.findAll(Sort.by("nev"))
                .stream()
                .map(pizza -> new PizzaValasz(
                        pizza.getNev(),
                        pizza.getKategoria().getNev(),
                        pizza.isVegetarianus()))
                .toList();
    }

    @GetMapping("/rendelesek")
    public List<RendelesValasz> rendelesek(
            @RequestParam(defaultValue = "0") int oldal,
            @RequestParam(defaultValue = "25") int meret) {

        int biztonsagosOldal = Math.max(0, oldal);
        int biztonsagosMeret = Math.min(Math.max(1, meret), 100);

        return rendelesRepository.findAll(
                        PageRequest.of(
                                biztonsagosOldal,
                                biztonsagosMeret,
                                Sort.by(Sort.Direction.DESC, "felvetel")))
                .stream()
                .map(rendeles -> new RendelesValasz(
                        rendeles.getAz(),
                        rendeles.getPizza().getNev(),
                        rendeles.getDarab(),
                        rendeles.getFelvetel(),
                        rendeles.getKiszallitas()))
                .toList();
    }

    public record KategoriaValasz(String nev, Integer ar) {
    }

    public record PizzaValasz(
            String nev,
            String kategoria,
            boolean vegetarianus) {
    }

    public record RendelesValasz(
            Long az,
            String pizza,
            Integer darab,
            LocalDateTime felvetel,
            LocalDateTime kiszallitas) {
    }
}