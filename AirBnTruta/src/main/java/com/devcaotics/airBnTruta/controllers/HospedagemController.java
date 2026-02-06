package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
            // TODO Auto-generated catch block
            m.addAttribute("msg", "Erro ao carregar os servicos");
            e.printStackTrace();
        }
        return "hospedeiro/newhospedagem";
    }

    @PostMapping("/new")
    public String postNewHospedagem(Model m, Hospedagem h,
        @RequestParam("servs") String[] servs) {
        
        List<Servico> servicos = Arrays.stream(servs)
            .map(c -> {
                try {
                    return facade.readServico(Integer.parseInt(c));
                } catch (NumberFormatException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
            })
            .collect(Collectors.toList());

        if(servicos == null)
            servicos = new ArrayList<>();

        h.setServicos(servicos);

        h.setHospedeiro((Hospedeiro)session.getAttribute("hospedeiroLogado"));

        try {
            facade.create(h);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "redirect:/hospedeiro";
    }
    
    

}
