package com.tongbora.customerservice.application.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerCustomPageQuery {
    private int pageNumber;
    private int pageSize;
}
