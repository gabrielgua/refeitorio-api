package com.gabrielgua.refeitorio.domain.service;

import com.gabrielgua.refeitorio.domain.exception.ResourceNotFoundException;
import com.gabrielgua.refeitorio.domain.exception.UserNotFoundException;
import com.gabrielgua.refeitorio.domain.model.User;
import com.gabrielgua.refeitorio.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private AtendimentoService atendimentoService;
    private final UserRepository repository;
    



    @Transactional(readOnly = true)
    public User findByCredential(String credential) {
        return repository.findById(credential).orElseThrow(() -> new UserNotFoundException(credential));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findRandom() {
        var users = listAll();

        var rand = new Random();
        return users.get(rand.nextInt(users.size()));
    }

    /**
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public BigDecimal getDiscount(User user) {
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal salary = user.getSalary(); // Extracted variable for salary
    
        if (salary == null) {
            salary = BigDecimal.ZERO;
        }
    
        int credential = Integer.parseInt(user.getCredential());
        System.out.println(credential);
        var currentAtendimento = atendimentoService.findCurrentAtendimento();
    
        if(currentAtendimento.isPresent()){
            //Café da Manhã
            if(currentAtendimento.get().getName().equals("Café da Manhã") || currentAtendimento.get().getName().equals("Lanche da Tarde")){
                
                //Estagiarios, Academicos, Residentes.
                if(credential >=60000 && credential <=79999){
                    discount = BigDecimal.valueOf(1);
                } else {
                    discount = BigDecimal.ZERO;
                }
            }
            //almoço
            if(currentAtendimento.get().getName().equals("Almoço")){
                //CLTS e Aprendizes
                if(credential >= 1 &&  credential <= 29999){
                    //1000 ou menos
                    if (salary.compareTo(BigDecimal.valueOf(1000)) <= 0) {
                        discount = BigDecimal.valueOf(.7); //70% de desconto
                    // mais que 1000 e 2000 ou menos
                    } else if (salary.compareTo(BigDecimal.valueOf(1000)) == 1 && salary.compareTo(BigDecimal.valueOf(2000)) <= 0) {
                        discount = BigDecimal.valueOf(.5); //50% de desconto
                    // mais que 2000 e 5000 ou menos
                    } else if (salary.compareTo(BigDecimal.valueOf(2000)) == 1 && salary.compareTo(BigDecimal.valueOf(5000)) <= 0) {
                        discount = BigDecimal.valueOf(.2); //20% de desconto
                    //mais que 5000
                    } else if (salary.compareTo(BigDecimal.valueOf(5000)) == 1) {
                        discount = BigDecimal.ZERO; //0% de desconto
                    //qualquer outra coisa
                    } else {
                        discount = BigDecimal.ZERO; //0% de desconto
                    }
                } else if(credential >=60000 && credential <= 79999){
                    discount = BigDecimal.valueOf(1);
                } else {
                    discount = BigDecimal.ZERO;
                }
            }
            //Jantar
            if(currentAtendimento.get().getName().equals("Jantar")){
                if(credential >=50000 && credential <= 59999 || credential >=30000 && credential <= 39999){
                    discount = BigDecimal.ZERO; //0% de desconto pra corpo clinico e RFCC
                } else if (credential >=40000 && credential <= 49999 || credential >=90000 && credential <= 92999){
                    discount = BigDecimal.ZERO; //0% de desconto pra terceiro
                } else {
                    discount = BigDecimal.valueOf(1); // quem não é terceiro, corpo clinico, ou rfcc. 100% de desconto
                }
            }
            
        } else {
            throw new ResourceNotFoundException("Atendimento não encontrado");
        }
    
        return discount;
    }    
}