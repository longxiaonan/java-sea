package com.iee.webbase.demo;

import com.iee.webbase.common.BusinessException;
import com.iee.webbase.demo.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dadiyang
 * @since 2019-06-02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    @Override
    public Account getById(int id) {
        return accountMapper.selectById(id);
    }

    @Override
    public int insert(Account account) {
        int rs = accountMapper.insert(account);
        if (rs <= 0) {
            log.warn("插入账户记录失败: " + account);
            throw new BusinessException("插入失败");
        }
        // 返回新生成的 id
        return account.getId();
    }

}
