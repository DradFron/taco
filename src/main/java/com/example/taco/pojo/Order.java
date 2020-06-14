package com.example.taco.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Data
public class Order {
    private long id;
    private Date placedAt;
    @NotBlank(message = "name is required")
    private String deliveryName;
    @NotBlank(message = "street is required")
    private String deliveryStreet;
    @NotBlank(message = "city is required")
    private String deliveryCity;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;
    @CreditCardNumber(message = "not a valid credit card number")
    private String ccNumber;
    private List<Taco> tacos;

    public Order() {
        this.tacos = new ArrayList<>();
    }

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }
}
