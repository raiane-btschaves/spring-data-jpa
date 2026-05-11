package io.github.raianebtschaves.libraryapi.repository;

import io.github.raianebtschaves.libraryapi.service.ServiceTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TranstionalTest {


    @Autowired
    ServiceTransaction serviceTransaction;

    /**
     * Commit -> Confirmar as alterações
     * Rollback -> Desfazer as alterações
     */
    @Test
       void simpleTransaction() {
        /** salavr um livro
         * salvar o autor
         * alugar o livro
         * enviar email pro locatário
         * notificar que o livro saiu da livraria
         */

        serviceTransaction.execute();
    }

    @Test
    void transactionStateManaged() {
       serviceTransaction.updateWithoutUpdating();
    }

}
