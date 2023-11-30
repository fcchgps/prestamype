package com.prestamype.factura.application.service;


import com.prestamype.factura.application.usecases.FacturaService;
import com.prestamype.factura.domain.model.constant.Constant;
import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.util.NodoXml;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.util.MetodosXml;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
    public FacturaEntity xmlToFactura(RequestXmlDTO request) {


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
}
