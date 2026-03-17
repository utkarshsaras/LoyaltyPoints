package com.example.LoyaltyPoints.Controller;

import com.example.LoyaltyPoints.DTO.CreateCustomerRequest;
import com.example.LoyaltyPoints.DTO.CustomerDetails;
import com.example.LoyaltyPoints.DTO.PurchaseRequest;
import com.example.LoyaltyPoints.DTO.RedemptionRequest;
import com.example.LoyaltyPoints.Entity.Customer;
import com.example.LoyaltyPoints.Entity.LoyaltyEvent;
import com.example.LoyaltyPoints.Exception.CustomerNotFoundException;
import com.example.LoyaltyPoints.Service.CustomerService;
import com.example.LoyaltyPoints.Service.LoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final LoyaltyService loyaltyService;
    private final CustomerService customerService;

    public CustomerController(LoyaltyService loyaltyService, CustomerService customerService)
    {
        this.loyaltyService = loyaltyService;
        this.customerService = customerService;
    }

    @Operation(
            summary = "Get Customer Details by customer id",
            description = """
        Returns the customer details that is customer Id, account level
        and the current points balance of the customer
        """
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable String id)
    {
        try
        {
            Customer customer = customerService.findCustomerDetails(id);
            int pointsBalance = loyaltyService.getPointsBalance(id);

            CustomerDetails customerDetails = new CustomerDetails(customer.getId(), customer.getAccountLevel().toString(), pointsBalance);
            return ResponseEntity.ok(customerDetails);
        }
        catch(CustomerNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Record purchase for customer by customer id",
            description = """
        Add the purchase for the customer and returns new points balance of the customer.
        Returns 404 Not found if customer not found.
        """
    )
    @PostMapping("/{id}/purchases")
    public ResponseEntity<?> addPurchase(@PathVariable String id, @RequestBody PurchaseRequest request)
    {
        try
        {
            int newBalance = loyaltyService.recordPurchase(id, request.getAmount());

            return ResponseEntity.ok(newBalance);
        }
        catch (CustomerNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Redeem points by the customer",
            description = """
        Subtracts redeem points from the current points balance of the customer.
        Returns the new points balance after redeem.
        Returns 404 not found if customer not exists or 409 conflict if redeem points are greater than 
        current points balance
        """
    )
    @PostMapping("/{id}/redemptions")
    public ResponseEntity<?> redeem(@PathVariable String id, @RequestBody RedemptionRequest request)
    {
        try
        {
            int newBalance = loyaltyService.redeemPoints(id, request.getPoints());

            return ResponseEntity.ok(newBalance);
        }
        catch (CustomerNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch(RuntimeException ex)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Return transaction history of the customer",
            description = """
        Returns the complete transaction history of the customer ordered by purchase time.
        """
    )
    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String id)
    {
        try
        {
            List<LoyaltyEvent> transactionsHistory = loyaltyService.getTransaactionsHistory(id);

            return ResponseEntity.ok(transactionsHistory);
        }
        catch (CustomerNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Test api to add customers",
            description = """
        This is a test api for adding customers.
        """
    )
    @PostMapping()
    public ResponseEntity<?> addCustomer(@RequestBody CreateCustomerRequest request)
    {
        try
        {
            Customer newCustomer = customerService.createCustomer(request.getId(), request.getAccountLevel());

            return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
