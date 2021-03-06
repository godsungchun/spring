package kr.co.leem.web.services;

import kr.co.leem.commons.constants.ResultType;
import kr.co.leem.domains.account.Account;
import kr.co.leem.domains.account.AccountReq;
import kr.co.leem.domains.menu.MenuReq;
import kr.co.leem.libs.jpa.params.PagingHelper;
import kr.co.leem.repositories.jpa.jpa.AccountRepository;
import kr.co.leem.repositories.jpa.jpa.criterias.LowMenuDAO;
import kr.co.leem.repositories.jpa.jpa.criterias.SampleCriteriaDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-13.
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired private AccountRepository accountRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private SampleCriteriaDAO sampleCriteriaDAO;
	@Autowired private LowMenuDAO lowMenuDAO;

	@Override
	public void getAccounts(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception {
		Pageable pageable = PagingHelper.createPageRequest(accountReq);
		Page<Account> page = null;
		String searchPhrase = accountReq.getSearchPhrase();

		if (StringUtils.isEmpty(searchPhrase)) {
			page = accountRepository.findAll(pageable);
		} else {
			page = accountRepository.findByAccountIdContainingOrNameContainingOrTelNumContainingOrHpNumContainingOrAddressContainingOrAddrDetailContainingOrPostNumContaining(pageable, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase, searchPhrase);
		}

		if (page != null) {
			resultMap.put(ResultType.total, page.getTotalElements()); // 총 페이지수
			resultMap.put(ResultType.current, accountReq.getCurrent());	// 현재 페이지
			resultMap.put(ResultType.record, accountReq.getRowCount()); // 총 레코드 수
			resultMap.put(ResultType.rows, page.getContent());
		}
	}

	@Override
	public void getAccount(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception {
		resultMap.put(ResultType.row, accountRepository.findByAccountId(accountReq.getAccountId()));
	}

	@Override
	public void saveAccount(Account account, Map<ResultType, Object> resultMap) throws Exception {
		Account saveAccount = accountRepository.save(account);

		resultMap.put(ResultType.row, saveAccount);
	}

	@Override
	public void delAccount(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception {
		accountRepository.deleteByAccountId(accountReq.getAccountId());
	}

	@Override
	public void setDefaultAccount(Map<ResultType, Object> resultMap) throws Exception {
		long cntUsers = accountRepository.count();
		sampleCriteriaDAO.getSelectDatas(resultMap);
		MenuReq menuReq = new MenuReq();
		menuReq.setMidMenuGrpSeq(3L);
		menuReq.setSearchPhrase("1");
		System.out.println(lowMenuDAO.cntTotalLowMenus(menuReq));
		if (cntUsers < 1) {
			Account account = new Account();
			account.setAccountId("admin");
			account.setName("관리자");
			account.setAddress("서울시");
			account.setHpNum("010-3087-0000");
			account.setPassword(passwordEncoder.encode("asdf1234"));
			account.setAddrDetail("학동");
			account.setPostNum("111-222");
			accountRepository.save(account);
		}
	}
}