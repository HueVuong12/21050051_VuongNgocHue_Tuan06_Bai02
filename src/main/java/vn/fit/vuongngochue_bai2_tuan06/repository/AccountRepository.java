package vn.fit.vuongngochue_bai2_tuan06.repository;

import vn.fit.vuongngochue_bai2_tuan06.model.Account;
import java.util.List;

public interface AccountRepository {
    boolean save(Account account);
    List<Account> findAll();
}
