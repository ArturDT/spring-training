package pl.training.bank.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pl.training.bank.account.entity.Account;
import pl.training.bank.account.repository.AccountRepository;
import pl.training.bank.account.services.AccountService;
import pl.training.bank.common.aop.ResultPage;
import pl.training.bank.generator.service.AccountNumberGenerator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private static final String ACCOUNT_NUMBER = "00000000000000000000000001";

    private AccountNumberGenerator accountNumberGenerator = mock(AccountNumberGenerator.class);
    private AccountRepository accountRepository = mock(AccountRepository.class);
    private AccountService accountService = new AccountService(accountNumberGenerator, accountRepository);
    private ResultPage<Account> resultPage = new ResultPage<>(new ArrayList<>());

    @BeforeEach
    void setUp() {
        when(accountNumberGenerator.getNext()).thenReturn(ACCOUNT_NUMBER);
        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        when(accountRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(new ArrayList<>()));
        resultPage.setTotalPages(1);
    }

    @DisplayName("Should save created account")
    @Test
    void shouldSaveCreatedAccount() {
        Account account = accountService.createAccount();
        verify(accountRepository).save(account);
    }

    @DisplayName("Should assign number to created account")
    @Test
    void shouldAssignNumberToCreatedAccount() {
        Account account = accountService.createAccount();
        assertEquals(ACCOUNT_NUMBER, account.getNumber());
    }

    @DisplayName("Should return page of accounts")
    @Test
    void shouldReturnPageOfAccounts() {
        assertEquals(resultPage, accountService.getAccounts(0, 1));
    }

}
