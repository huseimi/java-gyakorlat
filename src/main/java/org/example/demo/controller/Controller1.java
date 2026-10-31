package org.example.demo.controller;

import org.example.demo.entity.Kategoria;
import org.example.demo.entity.Pizza;
import org.example.demo.repository.KategoriaRepository;
import org.example.demo.repository.PizzaRepository;
import org.example.demo.repository.RendelesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controller1 {

    private final PizzaRepository pizzaRepository;
    private final KategoriaRepository kategoriaRepository;
    private final RendelesRepository rendelesRepository;

    public Controller1(
            PizzaRepository pizzaRepository,
            KategoriaRepository kategoriaRepository,
            RendelesRepository rendelesRepository) {
        this.pizzaRepository = pizzaRepository;
        this.kategoriaRepository = kategoriaRepository;
        this.rendelesRepository = rendelesRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(
            @RequestParam(required = false) String hiba,
            @RequestParam(required = false) String siker,
            Model model) {

        model.addAttribute("pizzak", pizzaRepository.findAll(Sort.by("nev")));
        model.addAttribute("kategoriak", kategoriaRepository.findAll(Sort.by("nev")));
        model.addAttribute("hiba", hibaUzenet(hiba));
        model.addAttribute("siker", sikerUzenet(siker));

        return "admin";
    }

    @PostMapping("/admin")
    public String adminMuvelet(
            @RequestParam String muvelet,
            @RequestParam(required = false) String nev,
            @RequestParam(required = false) String kategorianev,
            @RequestParam(defaultValue = "false") boolean vegetarianus) {

        if ("letrehoz".equals(muvelet)) {
            if (nev == null || nev.isBlank() || kategorianev == null || kategorianev.isBlank()) {
                return "redirect:/admin?hiba=ures";
            }

            String tisztitottNev = nev.trim();

            if (tisztitottNev.length() > 100) {
                return "redirect:/admin?hiba=hosszu";
            }

            if (pizzaRepository.existsById(tisztitottNev)) {
                return "redirect:/admin?hiba=duplikalt";
            }

            Kategoria kategoria = kategoriaRepository.findById(kategorianev).orElse(null);
            if (kategoria == null) {
                return "redirect:/admin?hiba=kategoria";
            }

            Pizza pizza = new Pizza();
            pizza.setNev(tisztitottNev);
            pizza.setKategoria(kategoria);
            pizza.setVegetarianus(vegetarianus);
            pizzaRepository.save(pizza);

            return "redirect:/admin?siker=letrehoz";
        }

        if ("frissit".equals(muvelet)) {
            if (nev == null || kategorianev == null) {
                return "redirect:/admin?hiba=ures";
            }

            Pizza pizza = pizzaRepository.findById(nev).orElse(null);
            Kategoria kategoria = kategoriaRepository.findById(kategorianev).orElse(null);

            if (pizza == null) {
                return "redirect:/admin?hiba=nemtalalhato";
            }
            if (kategoria == null) {
                return "redirect:/admin?hiba=kategoria";
            }

            pizza.setKategoria(kategoria);
            pizza.setVegetarianus(vegetarianus);
            pizzaRepository.save(pizza);

            return "redirect:/admin?siker=frissit";
        }

        if ("torol".equals(muvelet)) {
            if (nev == null || nev.isBlank()) {
                return "redirect:/admin?hiba=ures";
            }

            if (!pizzaRepository.existsById(nev)) {
                return "redirect:/admin?hiba=nemtalalhato";
            }

            if (rendelesRepository.existsByPizza_Nev(nev)) {
                return "redirect:/admin?hiba=kapcsolt";
            }

            pizzaRepository.deleteById(nev);
            return "redirect:/admin?siker=torol";
        }

        return "redirect:/admin?hiba=muvelet";
    }

    private String hibaUzenet(String kod) {
        if (kod == null) {
            return null;
        }

        return switch (kod) {
            case "ures" -> "Töltsd ki az összes kötelező mezőt.";
            case "hosszu" -> "A pizzanév legfeljebb 100 karakter lehet.";
            case "duplikalt" -> "Ilyen nevű pizza már létezik.";
            case "kategoria" -> "A kiválasztott kategória nem található.";
            case "nemtalalhato" -> "A pizza nem található.";
            case "kapcsolt" -> "A pizzához már rendelés tartozik, ezért nem törölhető.";
            default -> "Ismeretlen művelet.";
        };
    }

    private String sikerUzenet(String kod) {
        if (kod == null) {
            return null;
        }

        return switch (kod) {
            case "letrehoz" -> "A pizza létrejött.";
            case "frissit" -> "A pizza adatai módosultak.";
            case "torol" -> "A pizza törlődött.";
            default -> null;
        };
    }
}