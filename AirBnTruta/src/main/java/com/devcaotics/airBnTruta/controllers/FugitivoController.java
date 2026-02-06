package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/fugitivo")
public class FugitivoController {

    @Autowired
    private Facade facade;

    @Autowired
    private HttpSession session;

    private String msg = null;

    @GetMapping({"", "/"})
    public String init(Model m,
                       @RequestParam(required = false) String localidade,
                       @RequestParam(required = false) Double precoMax) {

        Fugitivo logado = (Fugitivo) session.getAttribute("fugitivoLogado");

        if (logado != null) {
            try {
                List<Hospedagem> hospedagens;
                if ((localidade != null && !localidade.isEmpty()) || precoMax != null) {
                    hospedagens = facade.filterHospedagempBySelection(localidade, precoMax);
                } else {
                    hospedagens = facade.filterHospedagemByAvailable();
                }
                m.addAttribute("hospedagens", hospedagens);
            } catch (SQLException e) {
                m.addAttribute("msg", "Erro ao carregar hospedagens");
            }
            return "fugitivo/index";
        }

        m.addAttribute("fugitivo", new Fugitivo());
        m.addAttribute("msg", msg);
        msg = null;
        return "fugitivo/login";
    }

    @PostMapping("/save")
    public String save(Fugitivo f) {
        try {
            facade.create(f);
            msg = "Cadastro realizado com sucesso!";
        } catch (SQLException e) {
            msg = "Erro ao cadastrar fugitivo";
        }
        return "redirect:/fugitivo";
    }

    @PostMapping("/login")
    public String login(@RequestParam String vulgo, @RequestParam String senha) {
        try {
            Fugitivo logado = facade.loginFugitivo(vulgo, senha);
            if (logado == null) {
                msg = "Login inválido";
                return "redirect:/fugitivo";
            }
            session.setAttribute("fugitivoLogado", logado);
            return "redirect:/fugitivo";
        } catch (SQLException e) {
            msg = "Erro ao logar";
            return "redirect:/fugitivo";
        }
    }

    @GetMapping("/viewhospedagem/{id}")
    public String viewHospedagem(@PathVariable("id") Integer id, Model m) {
        if (id == null || id <= 0) {
            return "redirect:/fugitivo";
        }

        try {
            Hospedagem h = facade.readHospedagem(id);
            if (h != null) {
                m.addAttribute("hospedagem", h);
                return "fugitivo/detalheHospedagem";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/fugitivo";
    }

    @PostMapping("/interesse")
    public String salvarInteresse(@RequestParam("hospedagemId") int hospedagemId,
                                  @RequestParam("proposta") String proposta,
                                  @RequestParam("tempo") int tempo) throws SQLException {

        Fugitivo fugitivo = (Fugitivo) session.getAttribute("fugitivoLogado");
        if (fugitivo == null) {
            return "redirect:/fugitivo";
        }

        Interesse i = new Interesse();
        i.setInteressado(fugitivo);
        i.setProposta(proposta);
        i.setTempoPermanencia(tempo);
        i.setRealizado(0L);

        Hospedagem h = facade.readHospedagem(hospedagemId);
        i.setInteresse(h);

        facade.create(i);
        return "redirect:/fugitivo/meusinteresses";
    }

    @GetMapping("/meusinteresses")
    public String meusInteresses(Model m) {
        Fugitivo logado = (Fugitivo) session.getAttribute("fugitivoLogado");
        if (logado == null) {
            return "redirect:/fugitivo";
        }

        try {
            List<Interesse> lista = facade.readInteressesByFugitivo(logado.getCodigo());
            m.addAttribute("interesses", lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "fugitivo/meusInteresses";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("fugitivoLogado");
        return "redirect:/fugitivo";
    }
}