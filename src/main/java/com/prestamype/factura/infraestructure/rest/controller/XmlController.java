package com.prestamype.factura.infraestructure.rest.controller;


import com.prestamype.factura.application.usecases.FacturaService;
import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.domain.model.dto.request.EstadoFinanciamientoRequest;
import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/registrarXml")
    public  ResponseEntity<FacturaEntity> registrarXml(@RequestBody RequestXmlDTO request)  {

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


    @PostMapping("/solicatarfinancimiento")
    public  ResponseEntity<String> financiamiento( @RequestBody FinanciamientoRequest financiamientoRequest )  {

        facturaService1.financiamiento(financiamientoRequest);
      return  new ResponseEntity<String>("ok....",HttpStatus.OK);

      //  return null;
    }

    @GetMapping("/consultarFinanciamientoPorUsuario/lista")
    public  ResponseEntity<List<FinanciamientoEntity>> consultarFinanciamientoPorUsuario(@RequestParam (value="usuario") String usuario,
                                                                                         @RequestParam (value="factura",required = false ) String factura,
                                                                                         @RequestParam (value="rucproveedor",required = false ) String rucProveedor ) {
        List<FinanciamientoEntity> financiamientoEntities = facturaService1.consultarFinanciamientoPorUsuario(usuario, factura, rucProveedor);
        return new ResponseEntity<List<FinanciamientoEntity>>(financiamientoEntities, HttpStatus.OK);

    }

    @GetMapping("/consultarSolicitudFinanciamiento/lista")
    public  ResponseEntity<List<FinanciamientoEntity>> consultarSolicitudFinanciamiento(@RequestParam (value="factura" ) String factura,
                                                                                         @RequestParam (value="rucproveedor",required = false ) String rucProveedor ) {
        List<FinanciamientoEntity> financiamientoEntities = facturaService1.consultarSolicitudFinanciamiento( factura, rucProveedor);
        return new ResponseEntity<List<FinanciamientoEntity>>(financiamientoEntities, HttpStatus.OK);

    }
    //aprobarRechazarFinanciamiento
    @PutMapping("/aprobarRechazarFinanciamiento")
    public  ResponseEntity<String> aprobarRechazarFinanciamiento( @RequestBody EstadoFinanciamientoRequest estadoFinanciamientoRequest )  {

        facturaService1.aprobarRechazarFinanciamiento(estadoFinanciamientoRequest);
        return  new ResponseEntity<String>("ok....",HttpStatus.OK);

    }
}
