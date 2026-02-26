package com.tongbora.customerservice.application.projection;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerQuery {
    private int pageNumber;
    private int pageSize;
}
