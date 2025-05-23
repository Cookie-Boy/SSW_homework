package ru.sibsutis.shop.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.model.OrderSearchCriteria;
import ru.sibsutis.shop.model.entity.Customer;
import ru.sibsutis.shop.model.entity.Order;
import ru.sibsutis.shop.model.entity.Payment;
import ru.sibsutis.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findOrdersByCriteria(OrderSearchCriteria criteria) {
        return orderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getAddress() != null) {

                // Сначала соединяем Order с Customer
                Join<Order, Customer> customerJoin = root.join("customer");

                // Доступ к полям Address напрямую (они в той же таблице)
                predicates.add(cb.equal(customerJoin.get("address").get("city"), criteria.getAddress().getCity()));
                predicates.add(cb.equal(customerJoin.get("address").get("street"), criteria.getAddress().getStreet()));
            }

            if (criteria.getFromDate() != null && criteria.getToDate() != null) {
                predicates.add(cb.between(root.get("date"), criteria.getFromDate(), criteria.getToDate()));
            }

            if (criteria.getPayment() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.type(), criteria.getPayment()));
            }

            if (criteria.getCustomerName() != null) {
                Join<Order, Customer> customerJoin = root.join("customer");
                predicates.add(cb.equal(customerJoin.get("name"), criteria.getCustomerName()));
            }

            if (criteria.getPaymentStatus() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.get("status"), criteria.getPaymentStatus()));
            }

            if (criteria.getOrderStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getOrderStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}