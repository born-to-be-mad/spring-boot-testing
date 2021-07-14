package by.dma.springboottesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import by.dma.springboottesting.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value =
            "SELECT * " +
                    "FROM customer " +
                    "ORDER BY joined_at ASC " +
                    "LIMIT 1", nativeQuery = true)
    Customer getEarlyBird();
}
