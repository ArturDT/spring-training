package pl.training.bank.account;

import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Account> getByNumber(String accountNumber) {
        try {
            return Optional.of(entityManager.createQuery("select a from Account a where a.number = :number", Account.class)
                    .setParameter("number", accountNumber)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
