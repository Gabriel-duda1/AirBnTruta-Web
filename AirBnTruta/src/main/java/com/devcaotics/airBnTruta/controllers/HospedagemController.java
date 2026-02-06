package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hospedagem")
public class HospedagemController {

    @Autowired
    private Facade facade;

    @Autowired
    private HttpSession session;

    @GetMapping("/new")
    public String newHospedagem(Model m) {
        m.addAttribute("hospedagem", new Hospedagem());
        try {
            m.addAttribute("servicos", facade.readAllServico());
        } catch (SQLException e) {
            m.addAttribute("msg", "Erro ao carregar os serviços");
            e.printStackTrace();
        }
        return "hospedeiro/newhospedagem";
    }

    @PostMapping("/save")
    public String salvarHospedagem(Hospedagem h,
                                   @RequestParam(value = "servs", required = false) String[] servs) {

        List<Servico> servicos = new ArrayList<>();
        if (servs != null) {
            servicos = Arrays.stream(servs)
                    .map(c -> {
                        try {
                            return facade.readServico(Integer.parseInt(c));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(s -> s != null)
                    .collect(Collectors.toList());
        }

        h.setServicos(servicos);
        h.setHospedeiro((Hospedeiro) session.getAttribute("hospedeiroLogado"));

        try {
            facade.create(h);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/hospedeiro";
    }
}