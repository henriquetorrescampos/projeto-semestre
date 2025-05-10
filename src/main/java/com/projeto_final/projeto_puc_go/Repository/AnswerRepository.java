package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository me permite fazer operações no banco
//sem escrever linha de SQL(crud: creat, read, update, delete)
// posso chamar os metodos findAll(), findById(), save() e deleteById()
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
