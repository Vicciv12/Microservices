package com.authorization.authorization_service.models.res;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailsResponse {
    private Set<String> emails;
}
