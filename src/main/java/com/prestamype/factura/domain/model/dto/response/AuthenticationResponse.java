package com.prestamype.factura.domain.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long exp;
    private Long user_id;
    private String user_name;
    private String descripcion;
    private Long company_id;
    private String company_name;
    private String company_description;
    private String user_tz;
    private Long channel_id;
    private String channel_name;
    private Long sale_team_id;
    private String sale_team_name;
    private String type_user;
    private String token;
    private String refresh_token;
}
