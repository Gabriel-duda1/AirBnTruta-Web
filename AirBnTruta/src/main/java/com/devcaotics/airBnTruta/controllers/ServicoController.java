package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;

@Controller
@RequestMapping("/servico")
public class ServicoController {

    private String msg;

    @Autowired
    private Facade facade;

    @GetMapping({"", "/"})
    public String listar(Model m) {
        try {
            List<Servico> servicos = this.facade.readAllServico();
            m.addAttribute("servico", new Servico());
            m.addAttribute("servicos", servicos);
            m.addAttribute("msg", this.msg);
            this.msg = null;
        } catch (SQLException e) {
            m.addAttribute("msg", "Não foi possível recuperar a lista de serviços!");
        }
        return "servico/list";
    }

    @GetMapping({"/save", "/save/"})
    public String createPage(Model m) {
        m.addAttribute("servico", new Servico());
        return "servico/CadastroServico";
    }

    @PostMapping("/save")
    public String save(Servico s) {
        try {
            if (s.getCodigo() == 0) {
                this.facade.create(s);
            } else {
                this.facade.update(s);
            }
            this.msg = "Operação realizada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            this.msg = "Erro ao salvar serviço!";
        }
        return "redirect:/servico";
    }

    @GetMapping("/save/{id}")
    public String getUpdate(Model m, @PathVariable("id") int id) {
        try {
            Servico servico = this.facade.readServico(id);
            m.addAttribute("servico", servico);
        } catch (SQLException e) {
            e.printStackTrace();
            m.addAttribute("msg", "Erro ao carregar serviço para edição!");
        }
        return "servico/CadastroServico";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        try {
            this.facade.deleteServico(id);
            this.msg = "Serviço deletado com sucesso!";
        } catch (SQLException e) {
            this.msg = "Problema ao deletar o serviço!";
        }
        return "redirect:/servico";
    }
}
