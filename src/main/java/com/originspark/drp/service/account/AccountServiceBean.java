package com.originspark.drp.service.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.originspark.drp.models.User;
import com.originspark.drp.util.enums.Status;

@Transactional
@Service
public class AccountServiceBean implements AccountService {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<User> validate(String username, String password) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> dataQuery = cb.createQuery(User.class);
        Root<User> user = dataQuery.from(User.class);
        dataQuery.select(user);
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(user.get("name"), username));
        criteria.add(cb.equal(user.get("status"), Status.ACTIVE));
        Predicate[] predicates = new Predicate[criteria.size()];
        predicates = criteria.toArray(predicates);
        dataQuery.where(cb.and(predicates));
        return em.createQuery(dataQuery).getResultList();
    }

}
