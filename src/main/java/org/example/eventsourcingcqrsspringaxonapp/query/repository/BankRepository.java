package org.example.eventsourcingcqrsspringaxonapp.query.repository;

import com.google.common.annotations.J2ktIncompatible;
import org.example.eventsourcingcqrsspringaxonapp.query.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, String>{
}
