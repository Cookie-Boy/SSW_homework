package ru.sibsutis.shop.integration;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.sibsutis.shop.model.Address;
import ru.sibsutis.shop.model.OrderSearchCriteria;
import ru.sibsutis.shop.model.entity.*;
import ru.sibsutis.shop.model.paymentType.Cash;
import ru.sibsutis.shop.model.paymentType.PaymentStatus;
import ru.sibsutis.shop.repository.CustomerRepository;
import ru.sibsutis.shop.repository.OrderRepository;
import ru.sibsutis.shop.repository.PaymentRepository;
import ru.sibsutis.shop.service.OrderService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test") // Активируем test-профиль
class OrderServiceIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Customer testCustomer;
    private Cash testCashPayment;
    private Order testOrder;

    @BeforeEach
    void setUp() throws Exception {
        // Создаем Liquibase вручную
        Connection connection = DataSourceUtils.getConnection(dataSource);
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(),
                database
        );
        // Полная пересоздание схемы перед каждым тестом
        liquibase.dropAll();
        liquibase.update(new Contexts());

        testCustomer = new Customer();
        testCustomer.setName("Test Customer");
        Address address = new Address();
        address.setCity("Новосибирск");
        address.setStreet("Кирова");
        testCustomer.setAddress(address);
        customerRepository.save(testCustomer);

//        testCashPayment = new Cash();
//        testCashPayment.setAmount(100.50);
//        testCashPayment.setStatus(PaymentStatus.COMPLETED);
//        testCashPayment.setCashTendered(100.50f);
//        paymentRepository.save(testCashPayment);

        testOrder = new Order();
        testOrder.setCustomer(testCustomer);
        testOrder.setPayment(testCashPayment);
        testOrder.setDate(LocalDateTime.now());
        testOrder.setStatus("COMPLETED");
        orderRepository.save(testOrder);
    }

    @Test
    void shouldFindOrdersByAddress() {
        Address searchAddress = new Address();
        searchAddress.setCity("Новосибирск");
        searchAddress.setStreet("Кирова");

        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setAddress(searchAddress);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldFindOrdersByPaymentType() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setPayment(Cash.class);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(0);
//        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldFindOrdersByTimeInterval() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setFromDate(LocalDateTime.now().minusDays(1));
        criteria.setToDate(LocalDateTime.now().plusDays(1));

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldFindOrdersByCustomerName() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setCustomerName("Test Customer");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldFindOrdersByPaymentStatus() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
            criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldFindOrdersByOrderStatus() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setOrderStatus("COMPLETED");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }

    @Test
    void shouldNotFindOrdersWithWrongCriteria() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setOrderStatus("CANCELLED");

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCombineMultipleCriteria() {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        criteria.setCustomerName("Test Customer");
        criteria.setOrderStatus("COMPLETED");
        criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = orderService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(testOrder);
    }
}