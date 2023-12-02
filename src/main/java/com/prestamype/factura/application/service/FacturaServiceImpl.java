package com.prestamype.factura.application.service;


import com.prestamype.factura.application.usecases.FacturaService;
import com.prestamype.factura.domain.model.constant.Constant;
import com.prestamype.factura.domain.model.dto.request.EstadoFinanciamientoRequest;
import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;
import com.prestamype.factura.infraestructure.adapter.util.NodoXml;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.util.MetodosXml;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    FacturaPersistencePort facturaPersistencePort;

    @Override
    public FacturaEntity consultarFactura(FacturaEntity factura) {
        Optional<FacturaEntity> opt = facturaPersistencePort.findFirstByCodigoAndRucEmisorAndRucProveedor(factura.getCodigo(), factura.getRucEmisor(), factura.getRucProveedor());
        if (!opt.isEmpty()) {
            throw new DuplicateKeyException("Ya existe la Factura con el id: " + factura.getCodigo());
        }

        return this.saveFactura(factura);

    }

    @Override
    public FacturaEntity  xmlToFactura(RequestXmlDTO request) {


        ////////////////////////////////////////
        String pproveedor = "";
        String versionactual = "2.1";
        String sobservacion = "";
        String sobservacion2 = "";
        List<NodoXml> lsttags = new ArrayList<NodoXml>();
        List<NodoXml> lstcbcid = new ArrayList<NodoXml>();
        NodoXml onode = new NodoXml();
        NodoXml onodeinvoice = new NodoXml();
        NodoXml onodeublextensions = new NodoXml();
        /////////////////////////////////////////
        MetodosXml.filtroversionxml[0] = "Invoice/cbc:UBLVersionID";
        MetodosXml.filtrofac[0] = "Invoice/cbc:ID";
        MetodosXml.filtrofece[0] = "Invoice/cbc:IssueDate";
        MetodosXml.filtroimportetotal[0] = "Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount";
        MetodosXml.filtrotmo[0] = "Invoice/cbc:DocumentCurrencyCode";
        MetodosXml.filtrorua[0] = "Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID";
        MetodosXml.filtrorue[0] = "Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID";
        /////////////////////////////////////////

        String nombrearchivo = request.getUrl().toString();

        File file = new File(Constant.DIRECTORIO_CARGA + nombrearchivo);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
        } catch (IOException e) {
            throw new NoSuchElementException("NO existe el documento XML: " + file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        MetodosXml.llenarNodos(onode, doc);
        MetodosXml.formatoNodo(onode, lsttags, onodeinvoice);
        MetodosXml.formatoNodoUblExtensions(onode, lsttags, onodeublextensions);
        MetodosXml.buscarNodocbcId(lsttags, lstcbcid);

        String versionubl = MetodosXml.buscarNodo(MetodosXml.filtroversionxml, lsttags);
        String nroFactura = MetodosXml.buscarNodo(MetodosXml.filtrofac, lsttags);
        String fechaEmision = MetodosXml.buscarNodo(MetodosXml.filtrofece, lsttags);
        String importeTotal = MetodosXml.buscarNodo(MetodosXml.filtroimportetotal, lsttags);
        String tipoMoneda = MetodosXml.buscarNodo(MetodosXml.filtrotmo, lsttags);
        String rucProveedor = MetodosXml.buscarNodo(MetodosXml.filtrorua, lsttags);
        String rucEmisor = MetodosXml.buscarNodo(MetodosXml.filtrorue, lsttags);

        if (versionubl.isEmpty() || nroFactura.isEmpty() || fechaEmision.isEmpty() ||
                importeTotal.isEmpty() || tipoMoneda.isEmpty() || rucProveedor.isEmpty() || rucEmisor.isEmpty() )
        {
            throw new NoSuchElementException("Uno de los Tag en el Xml no esta presente... ");
        }

        System.out.println(versionubl);
        System.out.println(nroFactura);
        System.out.println(fechaEmision);
        System.out.println(importeTotal);
        System.out.println(tipoMoneda);
        System.out.println(rucProveedor);
        System.out.println(rucEmisor);


        FacturaEntity factura = FacturaEntity.builder()
                .codigo(nroFactura)
                .fechaEmision(fechaEmision)
                .monto(Double.parseDouble(importeTotal))
                .moneda(tipoMoneda)
                .rucEmisor(rucEmisor)
                .rucProveedor(rucProveedor)
                .usuario(request.getUsuario())
                .build();


        return  factura;
    }



    @Override
    public FacturaEntity saveFactura(FacturaEntity factura) {
        return facturaPersistencePort.saveFactura(factura);
    }


    @Override
    public List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor) {

        return facturaPersistencePort.consultarFacturasPorUsuario(usuario,codigo,rucEmisor, rucProveedor);

    }

    @Override
    public void financiamiento(FinanciamientoRequest financiamientoRequest) {

        //TODO verificar que en financiamiento el doc no se repita con el mismo usuario.
        Optional<FinanciamientoEntity> financiamientoEntity = facturaPersistencePort.findFirstByFacturaAndUsuario(financiamientoRequest.getInvoice_id(),financiamientoRequest.getUsuario());


        Date fechaActual = new Date();
        if ( financiamientoRequest.getPayment_date().before(fechaActual)) {
            throw new NoSuchElementException("La fecha no puede ser menor a la actual: " + financiamientoRequest.getInvoice_id());
        }
        if (!financiamientoEntity.isEmpty() ) {
            throw new DuplicateKeyException("El ususario ya tiene la factura asinada: " + financiamientoRequest.getInvoice_id());
        }

        //TODO si no se envia el parameter monto, se obtiene el monto total del doc y se registra en
        // el financiamiento.
        if ( financiamientoRequest.getNet_amount()==-1)
        {
            FacturaEntity facturaEntity = facturaPersistencePort.findByCodigoAndUsuario(financiamientoRequest.getInvoice_id(),financiamientoRequest.getUsuario());
            financiamientoRequest.setNet_amount(facturaEntity.getMonto());
        }

        //TODO Registrar el Financiamiento
        FinanciamientoEntity financiamientoEntityDto = FinanciamientoEntity.builder()
        .estado("I")
        .fechaPago(financiamientoRequest.getPayment_date())
        .monto(financiamientoRequest.getNet_amount())
        .factura(financiamientoRequest.getInvoice_id())
        .usuario(financiamientoRequest.getUsuario())
        .build();

        facturaPersistencePort.saveFinanciamiento(financiamientoEntityDto);
    }

    @Override
    public List<FinanciamientoEntity> consultarFinanciamientoPorUsuario(String usuario, String factura, String rucProveedor) {
        return facturaPersistencePort.consultarFinanciamientoPorUsuario(usuario,factura, rucProveedor);

    }

    @Override
    public List<FinanciamientoEntity> consultarSolicitudFinanciamiento(String factura, String rucProveedor) {
        return facturaPersistencePort.consultarSolicitudFinanciamiento(factura, rucProveedor);
    }

    @Override
    public void aprobarRechazarFinanciamiento(EstadoFinanciamientoRequest estadoFinanciamientoRequest) {
        //TODO Buscar financiamiento por Id
       /* Optional<FinanciamientoEntity> financiamientoEntity=facturaPersistencePort.findById(estadoFinanciamientoRequest.getIdFinanciamiento());
        if (financiamientoEntity.isEmpty()) {
            throw new NoSuchElementException("No existe el Financiamiento con el id: " + estadoFinanciamientoRequest.getIdFinanciamiento());
        }
        */
        FinanciamientoEntity financiamientoEntity =facturaPersistencePort.findById(estadoFinanciamientoRequest.getIdFinanciamiento()).
                orElseThrow( ()->new NoSuchElementException("No existe el Financiamiento con el id: " + estadoFinanciamientoRequest.getIdFinanciamiento()));

        //TODO verificar que no tenga el campo de aprobaciontime o rechazotime para proceder a actualizar el estado
        if (financiamientoEntity.getAprobaciondate()!= null || financiamientoEntity.getRechazodate()!= null){
            throw new NoSuchElementException("El estado ya ha sido actualizado previamente...");
        }

        //TODO verificar que los estados sean A:aprobado   R:rechazado   I:ingresado, desde el front,igualmente podemos hacer validacion en el back
        if (estadoFinanciamientoRequest.getEstado().equals(Constant.ESTADO_APROBADO))
        {
            financiamientoEntity.setEstado(Constant.ESTADO_APROBADO);
            financiamientoEntity.setAprobaciondate(new Date());
        } else {
            estadoFinanciamientoRequest.setEstado(Constant.ESTADO_RECHAZADO);
            financiamientoEntity.setRechazodate(new Date());
        }

        facturaPersistencePort.saveFinanciamiento(financiamientoEntity);
        return;

    }
}
