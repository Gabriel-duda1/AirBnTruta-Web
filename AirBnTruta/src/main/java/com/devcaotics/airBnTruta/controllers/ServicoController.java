package com.devcaotics.airBnTruta.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.airBnTruta.model.entities.Servico;
import com.devcaotics.airBnTruta.model.repositories.Facade;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/servico")
public class ServicoController {

    private String msg;

    @Autowired
    private Facade facade;
    
    @GetMapping({"/save","/save/"})
    public String createPage(Model m){

        m.addAttribute("servico", new Servico());
        return "servico/CadastroServico";

    }

    @PostMapping("/save")
    public String save(Model m, Servico s, HttpServletRequest request) {
        
        /*String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        String desc = request.getParameter("desc");

        Servico s = new Servico();

        s.setDescricao(desc);
        s.setNome(nome);
        s.setTipo(tipo);*/

        

        try {
            if(s.getCodigo() == 0){
                Facade.getCurrentInstance().create(s); 
            }else{
                this.facade.update(s);  
            }
            this.msg="operação realizada com sucesso!";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        return "redirect:/servico";
    }

    @GetMapping({"","/"})
    public String getMethodName(Model m) {

        try {
            List<Servico> servicos = this.facade.readAllServico();
            m.addAttribute("servico", new Servico());
            m.addAttribute("servicos", servicos);
            m.addAttribute("msg", this.msg);
            this.msg = null;
        } catch (SQLException e) {
            m.addAttribute("msg", "Não foi possível recuparar a lista de serviços!");
        }

        return "servico/list";
    }
    
    @GetMapping("/save/{id}")
    public String getUpdate(Model m,@PathVariable("id") int id) {

        List<Servico> servicos;
        try {
            servicos = this.facade.readAllServico();
            m.addAttribute("servico", this.facade.readServico(id));
            
            m.addAttribute("servicos", servicos);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        return "servico/list";
    }

    @GetMapping("/delete/")
    public String getDelete(Model m,@RequestParam int id) {

        try {
            this.facade.deleteServico(id);

            this.msg = "Serviço deletado com sucesso!";
        } catch (SQLException e) {
            this.msg = "Problema ao deletar o serviço!";
        }

        return "redirect:/servico";
    }
    
    

}
