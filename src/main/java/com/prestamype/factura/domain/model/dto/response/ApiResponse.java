package com.prestamype.factura.domain.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Result result;

    public ApiResponse(Object data, boolean success, Integer errors, String messages) {
        this.result = new Result(data, success, errors, messages);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Object data;
        private boolean success;
        private Integer errors;
        private String messages;
    }
}
