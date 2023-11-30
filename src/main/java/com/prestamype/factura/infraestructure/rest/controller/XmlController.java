package com.prestamype.factura.infraestructure.rest.controller;


import com.prestamype.factura.application.usecases.FacturaService;
import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.domain.model.dto.request.SearchFacturaRequest;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@AllArgsConstructor
public class XmlController {

    //@Autowired
    //FacturaService1 facturaService;

    private final FacturaService facturaService1;
    public XmlController(FacturaService facturaService1) {

        this.facturaService1 = facturaService1;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/xmltofactura1")
    public  ResponseEntity<FacturaEntity> xmlToFactura(@RequestBody RequestXmlDTO request)  {

        FacturaEntity facturaEntity = facturaService1.consultarFactura(facturaService1.xmlToFactura(request));

        FacturaEntity facturaEntitySave=facturaService1.saveFactura(facturaEntity);

        return  new ResponseEntity<FacturaEntity>(facturaEntitySave,HttpStatus.OK);

    }

    @GetMapping("/consultarfacturasporusuario/lista")
    public  ResponseEntity<List<FacturaEntity>> consultarFacturasPorUsuario(@RequestParam (value="usuario") String usuario,
                                                                            @RequestParam (value="codigo",required = false ) String codigo,
                                                                            @RequestParam (value="rucemisor",required = false ) String rucEmisor,
                                                                            @RequestParam (value="rucproveedor",required = false ) String rucProveedor )  {


        //return  new ResponseEntity( request.getCodigo(),HttpStatus.OK);
        List<FacturaEntity> listaFacturaEntity = facturaService1.consultarFacturasPorUsuario(usuario,codigo,rucEmisor,rucProveedor);
        return  new ResponseEntity<List<FacturaEntity>>(listaFacturaEntity,HttpStatus.OK);

    }

    /*
    @PostMapping("/solicatarfinancimiento/")
    public  ResponseEntity<List<FacturaEntity>> solicatarFinancimiento(@RequestBody FinanciamientoRequest financiamientoRequest )  {

      //  List<FacturaEntity> listaFacturaEntity = facturaService1.solicatarFinancimiento(usuario,codigo,rucEmisor,rucProveedor);
      //  return  new ResponseEntity<List<FacturaEntity>>(listaFacturaEntity,HttpStatus.OK);

        return null;
    }

     */


    }
