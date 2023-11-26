package com.prestamype.factura.infraestructure.rest.controller;


import com.prestamype.factura.application.usecases.FacturaService;
import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

@RestController
@RequestMapping("/api/v1")
//@AllArgsConstructor
public class XmlController1 {

    //@Autowired
    //FacturaService1 facturaService;

    private final FacturaService facturaService1;
    public XmlController1(FacturaService facturaService1) {

        this.facturaService1 = facturaService1;
    }

    @PostMapping(value = "/xmltofactura1")
    public  ResponseEntity<FacturaEntity> xmlToFactura(@RequestBody RequestXmlDTO request) throws ParserConfigurationException, SAXException {

        FacturaEntity facturaEntity = facturaService1.consultarFactura(facturaService1.xmlToFactura(request));

        FacturaEntity facturaEntitySave=facturaService1.saveFactura(facturaEntity);

        return  new ResponseEntity<FacturaEntity>(facturaEntitySave,HttpStatus.OK);

    }

/*
    @PostMapping(value = "/registrarfactura")
    public  ResponseEntity<Factura> registrarFactura(@RequestBody Factura factura) {
        facturaService.consultarFactura(factura);
        return  null;
    }

 */

    }
