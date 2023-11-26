package com.prestamype.factura.infraestructure.adapter.util;



import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class MetodosXml
{
    public static String[] filtrorue=new String[1];
    public static String[] filtrorua=new String[1];
    public static String[] filtrofac=new String[1];
    public static String[] filtrofece=new String[1];
    public static String[] filtrotmo=new String[1];
    public static String[] filtromogra=new String[1];
    public static String[] filtrodes=new String[1];
    public static String[] filtrotdoc=new String[1];
    public static String[] filtrotdocnxc=new String[1];
    public static String[] filtromotivonxc=new String[1];
    public static String[] filtrofecv=new String[1];
    public static String[] filtrotdet=new String[1];
    public static String[] filtrodetcol=new String[2];
    public static String[] filtrodetcolfilter=new String[2];
    public static String[] filtrodetcolfilterublextension=new String[2];
    public static String[] filtronametotal=new String[1];
    public static String[] filtronametotalperc=new String[1];
    public static String[] filtrodetcoltotal=new String[1];
    public static String[] filtrodetcoltotalexo=new String[1];
    public static String[] filtrodetcoltotalperc=new String[1];
    public static String[] filtroTaxTotal=new String[1];
    public static String[] filtroUBLExtensions=new String[1];
    public static String[] filtrovalorvtaitem=new String[1];
    public static String[] filtropreciount=new String[1];
    public static String[] filtrodsctoitem=new String[1];
    public static String[] filtrodescritem=new String[1];
    public static String[] filtropreciolst=new String[1];
    public static String[] filtronameigv_isc=new String[1];
    public static String[] filtrocoligvdet=new String[1];
    public static String[] filtrocoltaxableigv_isc=new String[1];
    public static String[] filtrototalprecioventa=new String[1];
    public static String[] filtroimportetotal=new String[1];
    public static String[] filtrocodigoitem=new String[1];
    public static String[] filtroversionxml=new String[1];
    public static String[] filtrodocreferencia=new String[1];
    public static String[] filtrotdocdocreferencia=new String[1];
    public static String[] filtroobservacionitem=new String[1];
    public static String[] filtroobservacion=new String[1];
    public static String[] filtroidtotal=new String[1];

    private static void addNodeChild(NodoXml onodeparent, ArrayList lstnode, List<NodoXml> lsttags)
    {
        for (int indice = 0; indice < lstnode.size(); indice++)
        {
            NodoXml onodo=(NodoXml) lstnode.get(indice);
            onodo.setTags(onodeparent.getTags().trim()+"/"+onodo.getNombre());
            lsttags.add(onodo);
            addNodeChild(onodo,onodo.lstnode,lsttags);
        }
    }
    public static void formatoNodo(NodoXml onode,List<NodoXml> lsttags, NodoXml onodeinvoice)
    {
        int detalle=0;

        String detallefac="Invoice/cac:InvoiceLine";
        for (int indice = 0; indice < onode.lstnode.size(); indice++)
        {
            NodoXml onodo=(NodoXml) onode.lstnode.get(indice);
            onodo.setTags(onodo.getNombre().trim());
            lsttags.add(onodo);
            addNodeChild(onodo,onodo.lstnode,lsttags);
        }

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                detalle+=1;
                onodo.setIndice(detalle);
                onodeinvoice.lstnode.add(onodo);
            }
        }
        onodeinvoice.setTotal(detalle);
    }
    private static boolean isNumeric(String cadena)
    {
        try
        {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    public static void llenarNodosHijos(NodoXml onodeparent,NodeList lstnode)
    {
        String nodoid="cbc:ID";
        for (int count = 0; count < lstnode.getLength(); count++)
        {
            Node onodechild = lstnode.item(count);
            if (onodechild.getNodeType() == Node.ELEMENT_NODE)
            {
                NodoXml onode=new NodoXml();
                onode.setNombre(onodechild.getNodeName());
                onode.setValor(onodechild.getTextContent());
                if (onode.getNombre().trim().equals(nodoid))
                {
                    if(isNumeric(onode.getValor()))
                    {
                        onode.setValor(String.valueOf(Integer.valueOf(onode.getValor())));
                    }
                }
                if (onodechild.hasChildNodes())
                {
                    llenarNodosHijos(onode,onodechild.getChildNodes());
                }
                else if (onodechild.hasAttributes())
                {
                    NamedNodeMap onodemap = onodechild.getAttributes();
                    for (int i = 0; i < onodemap.getLength(); i++)
                    {
                        Node node = onodemap.item(i);
                        NodoXml onodoattr=new NodoXml();
                        onodoattr.setNombre(node.getNodeName());
                        onodoattr.setValor(node.getNodeValue());
                        onode.lstattr.add(onodoattr);
                    }
                }
                onodeparent.lstnode.add(onode);
            }
        }
    }
    public static void llenarNodos(NodoXml onode, Document odoc)
    {
        if (odoc.hasChildNodes())
        {
            llenarNodosHijos(onode,odoc.getChildNodes());
        }
    }

    public static String buscarNodo(String[] cadena,List<NodoXml> lsttags)
    {
        String valor="";
        for (int fila = 0; fila < cadena.length; fila++)
        {
            for (int indice = 0; indice < lsttags.size(); indice++)
            {
                NodoXml onodo=(NodoXml)lsttags.get(indice);
                if (onodo.getTags().trim().equals(cadena[fila]))
                {
                    valor=onodo.getValor();
                    indice=lsttags.size();
                    fila=cadena.length;
                }
            }
        }

        return valor;
    }
    public static String buscarNodoNote(String[] cadena,List<NodoXml> lsttags)
    {
        String valor="";
        String novalor="SON";
        for (int fila = 0; fila < cadena.length; fila++)
        {
            for (int indice = 0; indice < lsttags.size(); indice++)
            {
                NodoXml onodo=(NodoXml)lsttags.get(indice);
                if (onodo.getTags().trim().equals(cadena[fila]))
                {
                    if(!(onodo.getValor().trim().indexOf(novalor)>-1))
                    {
                        valor=onodo.getValor();
                        indice=lsttags.size();
                        fila=cadena.length;
                    }
                }
            }
        }

        return valor;
    }
    private static boolean existeNodoItem(NodoXml nodoparent,String [] filter,String valortags)
    {
        boolean existenodo=false;
        for (int fila = 0; fila < filter.length; fila++)
        {
            //System.out.println(nodoparent.getTags());
            if (nodoparent.getTags().trim().equals(filter[fila]))
            {
                if (nodoparent.getValor().trim().equals(valortags))
                {
                    existenodo=true;
                    return existenodo;
                }
            }
            else
            {
                for (int indice = 0; indice < nodoparent.lstnode.size(); indice++)
                {
                    NodoXml onodo=(NodoXml)nodoparent.lstnode.get(indice);
                    //System.out.println(onodo.getTags());
                    existenodo=existeNodoItem(onodo,filter,valortags);
                    if (existenodo)
                    {
                        indice=nodoparent.lstnode.size();
                        return existenodo;
                    }
                }
            }
        }
        return existenodo;
    }
    private static String obtenerValorNodoItem(NodoXml nodoparent,String columna)
    {
        String ovalor="0.00";
        //System.out.println(nodoparent.getTags());
        if (nodoparent.getTags().trim().equals(columna))
        {
            ovalor=nodoparent.getValor();
            return ovalor;
        }
        else
        {
            for (int indice = 0; indice < nodoparent.lstnode.size(); indice++)
            {
                NodoXml onodo=(NodoXml)nodoparent.lstnode.get(indice);
                //System.out.println(onodo.getTags());
                ovalor=obtenerValorNodoItem(onodo,columna);
                if (!ovalor.trim().equals("0.00"))
                {
                    return ovalor;
                }
            }
        }
        return ovalor;
    }
    public static String buscarNodoTotal(String[] filterparent,String[] filter,String valortags,String columna,List<NodoXml> lsttags)
    {
        String valor="";

        for (int fila = 0; fila < filter.length; fila++)
        {
            for (int indice = 0; indice < lsttags.size(); indice++)
            {
                NodoXml onodo=(NodoXml)lsttags.get(indice);
                //System.out.println(onodo.getTags());
                if (onodo.getTags().trim().equals(filterparent[fila]))
                {
                    for (int nronodo = 0; nronodo < onodo.lstnode.size(); nronodo++)
                    {
                        NodoXml onodobus=(NodoXml)onodo.lstnode.get(nronodo);
                        //System.out.println(onodobus.getTags());
                        if (existeNodoItem(onodobus,filter,valortags))
                        {
                            valor=obtenerValorNodoItem(onodobus,columna);
                            nronodo=onodo.lstnode.size();
                            indice=lsttags.size();
                            fila=filter.length;
                        }
                    }
                }
            }
        }
        return valor;
    }

    public static String buscarNodoDetalleInvoiceLine(String[] filter,int nro,String columna,List<NodoXml> lsttags)
    {
        String valor="0.00";
        String detallefac="Invoice/cac:InvoiceLine";

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                for (int nronodo = 0; nronodo < onodo.lstnode.size(); nronodo++)
                {
                    NodoXml onodobus=(NodoXml)onodo.lstnode.get(nronodo);
                    if (existeNodoItem(onodobus,filter,String.valueOf(nro)))
                    {
                        valor=obtenerValorNodoItem(onodo,columna);
                        nronodo=onodo.lstnode.size();
                        indice=lsttags.size();
                    }
                }
            }
        }
        return valor;
    }
    public static String buscarNodoDetalleCreditNoteImpuesto(String[] filter,int nro,String[] filtroimp,String nameimp,String columna,List<NodoXml> lstinvoice)
    {
        ArrayList lstnodefind=new ArrayList();
        NodoXml onodoinvoice=new NodoXml();
        String valor="0.00";

        onodoinvoice=obtenerNodoParent(filter,nro,lstinvoice);
        for (int indice = 0; indice < filtroimp.length; indice++)
        {
            buscarNodoDependientesCreditNote(onodoinvoice,filtroimp[indice],nameimp,lstnodefind);
        }
        if (lstnodefind.size()>0)
        {
            NodoXml onododata=(NodoXml)lstnodefind.get(lstnodefind.size()-1);
            valor=obtenerValorNodoItem(onododata,columna);
        }

        return valor;
    }
    public static String buscarNodoDetalleInvoiceLineImpuesto(String[] filter,int nro,String[] filtroimp,String nameimp,String columna,List<NodoXml> lstinvoice)
    {
        ArrayList lstnodefind=new ArrayList();
        NodoXml onodoinvoice=new NodoXml();
        String valor="0.00";

        onodoinvoice=obtenerNodoParent(filter,nro,lstinvoice);
        for (int indice = 0; indice < filtroimp.length; indice++)
        {
            buscarNodoDependientes(onodoinvoice,filtroimp[indice],nameimp,lstnodefind);
        }
        if (lstnodefind.size()>0)
        {
            NodoXml onododata=(NodoXml)lstnodefind.get(lstnodefind.size()-1);
            valor=obtenerValorNodoItem(onododata,columna);
        }

        return valor;
    }
    private static NodoXml obtenerNodoParent(String [] filter,int nro,List<NodoXml> lstinvoice)
    {
        NodoXml onododata=new NodoXml();
        for (int row = 0; row < filter.length; row++)
        {
            for (int indice = 0; indice < lstinvoice.size(); indice++)
            {
                NodoXml onodo=(NodoXml) lstinvoice.get(indice);
                for (int fila = 0; fila < onodo.lstnode.size(); fila++)
                {
                    NodoXml onodochild=(NodoXml)onodo.lstnode.get(fila);

                    if (onodochild.getTags().trim().equals(filter[row]))
                    {
                        if (onodochild.getValor().trim().equals(String.valueOf(nro)))
                        {
                            onododata=onodo;
                            fila=onodo.lstnode.size();
                            indice=lstinvoice.size();
                            row=filter.length;
                        }
                    }
                }
            }
        }
        return onododata;
    }
    private static NodoXml obtenerNodoParentUblExtension(String [] filter,String nro,List<NodoXml> lstinvoice)
    {
        NodoXml onododata=new NodoXml();
        for (int row = 0; row < filter.length; row++)
        {
            for (int indice = 0; indice < lstinvoice.size(); indice++)
            {
                NodoXml onodo=(NodoXml) lstinvoice.get(indice);
                for (int fila = 0; fila < onodo.lstnode.size(); fila++)
                {
                    NodoXml onodochild=(NodoXml)onodo.lstnode.get(fila);

                    if (onodochild.getTags().trim().equals(filter[row]))
                    {
                        if (onodochild.getValor().trim().equals(nro))
                        {
                            onododata=onodo;
                            fila=onodo.lstnode.size();
                            indice=lstinvoice.size();
                            row=filter.length;
                        }
                    }
                }
            }
        }
        return onododata;
    }
    private static boolean buscarNodoDependientes(NodoXml onodoparent,String tags,String nameimp,ArrayList listado)
    {
        String nodoexception="Invoice/cac:InvoiceLine/cac:TaxTotal";
        boolean existe=false;
        if (onodoparent.getTags().trim().equals(tags))
        {
            if (onodoparent.getValor().trim().equals(nameimp))
            {
                existe=true;
            }
        }
        else
        {
            for (int fila = 0; fila < onodoparent.lstnode.size(); fila++)
            {
                NodoXml onodo=(NodoXml)onodoparent.lstnode.get(fila);
                existe=buscarNodoDependientes(onodo,tags,nameimp,listado);
                if (existe)
                {
                    if (!onodo.getTags().trim().equals(nodoexception))
                    {
                        listado.add(onodo);
                        return existe;
                    }
                }
            }
        }
        return existe;
    }
    public static Double obtenerPorcentaje(double tax, double taxable)
    {
        double porctotal=100.00;
        double porcentaje=0.00;

        porcentaje=porctotal*tax/taxable;
        porcentaje=Double.parseDouble(String.valueOf(Math.round(porcentaje)));

        return porcentaje;
    }
    public static double formatoDouble(String vvalue)
    {
        double vformato=0.00;

        if (vvalue.trim().length()==0)
        {
            vformato=0.00;
        }
        else
        {
            vformato=Double.parseDouble(vvalue);
        }
        return vformato;
    }
    private static int formatoInteger(String vvalue)
    {
        int vformato=0;

        if (vvalue.trim().length()==0)
        {
            vformato=0;
        }
        else
        {
            vformato=Integer.parseInt(vvalue);
        }
        return vformato;
    }
    public static String validarCodigoArticulo(String vvalue)
    {
        String vformato="";

        if (vvalue.trim().length()>0)
        {
            if(vvalue.trim().equals("0.00"))
            {
                vformato="";
            }
            else
            {
                vformato=vvalue;
            }
        }

        return vformato;

    }
    public static void buscarNodocbcId(List<NodoXml> lsttags,List<NodoXml> lstcbcid)
    {
        String detallefac="Invoice/cac:InvoiceLine";
        String iddetallefac="Invoice/cac:InvoiceLine/cbc:ID";

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                for (int nronodo = 0; nronodo < onodo.lstnode.size(); nronodo++)
                {
                    NodoXml onodobus=(NodoXml)onodo.lstnode.get(nronodo);
                    if(onodobus.getTags().trim().equals(iddetallefac))
                    {
                        NodoXml onodoid=new NodoXml();
                        onodoid.setNombre(iddetallefac);
                        onodoid.setValor(onodobus.getValor());
                        lstcbcid.add(onodoid);
                        nronodo=onodo.lstnode.size();
                    }
                }
            }
        }
    }
    public static String buscarNodoDetalleUblExtensionsImpuesto(String[] filter,String nro,String[] filtroimp,String nameimp,String columna,List<NodoXml> lstdetalle)
    {
        NodoXml onodoinvoice=new NodoXml();
        String valor="0.00";

        onodoinvoice=obtenerNodoParentUblExtension(filter,nro,lstdetalle);

        for(int indice = 0; indice < onodoinvoice.lstnode.size(); indice++)
        {
            NodoXml onododata=(NodoXml)onodoinvoice.lstnode.get(indice);
            if (onododata.getTags().trim().equals(columna))
            {
                valor=obtenerValorNodoItem(onododata,columna);
                indice=onodoinvoice.lstnode.size();
            }
        }

        return valor;
    }

    public static void formatoNodoUblExtensions(NodoXml onode,List<NodoXml> lsttags, NodoXml onodeublextensions)
    {
        int detalle=0;

        String detallefac="Invoice/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/sac:AdditionalInformation/sac:AdditionalMonetaryTotal";
        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                detalle+=1;
                onodo.setIndice(detalle);
                onodeublextensions.lstnode.add(onodo);
            }
        }
        onodeublextensions.setTotal(detalle);
    }
    public static void formatoNodoCreditNote(NodoXml onode,List<NodoXml> lsttags, NodoXml onodeinvoice)
    {
        int detalle=0;

        String detallefac="CreditNote/cac:CreditNoteLine";
        for (int indice = 0; indice < onode.lstnode.size(); indice++)
        {
            NodoXml onodo=(NodoXml) onode.lstnode.get(indice);
            onodo.setTags(onodo.getNombre().trim());
            lsttags.add(onodo);
            addNodeChild(onodo,onodo.lstnode,lsttags);
        }

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                detalle+=1;
                onodo.setIndice(detalle);
                onodeinvoice.lstnode.add(onodo);
            }
        }
        onodeinvoice.setTotal(detalle);
    }
    public static void formatoNodoUblExtensionsCreditNote(NodoXml onode,List<NodoXml> lsttags, NodoXml onodeublextensions)
    {
        int detalle=0;

        String detallefac="CreditNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/sac:AdditionalInformation/sac:AdditionalMonetaryTotal";
        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                detalle+=1;
                onodo.setIndice(detalle);
                onodeublextensions.lstnode.add(onodo);
            }
        }
        onodeublextensions.setTotal(detalle);
    }
    public static void buscarNodocbcIdCreditNote(List<NodoXml> lsttags,List<NodoXml> lstcbcid)
    {
        String detallefac="CreditNote/cac:CreditNoteLine";
        String iddetallefac="CreditNote/cac:CreditNoteLine/cbc:ID";

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                for (int nronodo = 0; nronodo < onodo.lstnode.size(); nronodo++)
                {
                    NodoXml onodobus=(NodoXml)onodo.lstnode.get(nronodo);
                    if(onodobus.getTags().trim().equals(iddetallefac))
                    {
                        NodoXml onodoid=new NodoXml();
                        onodoid.setNombre(iddetallefac);
                        onodoid.setValor(onodobus.getValor());
                        lstcbcid.add(onodoid);
                        nronodo=onodo.lstnode.size();
                    }
                }
            }
        }
    }
    public static String buscarNodoCreditNote(String[] cadena,List<NodoXml> lsttags)
    {
        String valor="";
        for (int fila = 0; fila < cadena.length; fila++)
        {
            for (int indice = 0; indice < lsttags.size(); indice++)
            {
                NodoXml onodo=(NodoXml)lsttags.get(indice);
                if (onodo.getTags().trim().equals(cadena[fila]))
                {
                    valor=onodo.getValor();
                    indice=lsttags.size();
                    fila=cadena.length;
                }
                else
                {
                    if (onodo.lstnode.size()>0)
                    {
                        valor=buscarNodoChildCreditNote(cadena[fila],onodo.lstnode);
                        if(valor.trim().length()>0)
                        {
                            indice=lsttags.size();
                            fila=cadena.length;
                        }
                    }

                    /*
                    for (int row = 0; row < onodo.lstnode.size(); row++)
                    {
                        NodoXml onodochild=(NodoXml)onodo.lstnode.get(row);
                        if (onodochild.getTags().trim().equals(cadena[fila]))
                        {
                            valor=onodo.getValor();
                            row=onodo.lstnode.size();
                            indice=lsttags.size();
                            fila=cadena.length;
                        }
                    }
                    */

                }
            }
        }

        return valor;
    }
    public static String buscarNodoChildCreditNote(String cadena,List<NodoXml> lsttags)
    {
        String valor="";

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(cadena))
            {
                valor=onodo.getValor();
                indice=lsttags.size();
            }
            else
            {
                if(onodo.lstnode.size()>0)
                {
                    valor=buscarNodoChildCreditNote(cadena,onodo.lstnode);
                    if(valor.trim().length()>0)
                    {
                        indice=lsttags.size();
                    }
                }
            }
        }

        return valor;
    }
    public static String buscarNodoDetalleCreditNoteLine(String[] filter,int nro,String columna,List<NodoXml> lsttags)
    {
        String valor="0.00";
        String detallefac="CreditNote/cac:CreditNoteLine";

        for (int indice = 0; indice < lsttags.size(); indice++)
        {
            NodoXml onodo=(NodoXml)lsttags.get(indice);
            if (onodo.getTags().trim().equals(detallefac))
            {
                for (int nronodo = 0; nronodo < onodo.lstnode.size(); nronodo++)
                {
                    NodoXml onodobus=(NodoXml)onodo.lstnode.get(nronodo);
                    if (existeNodoItem(onodobus,filter,String.valueOf(nro)))
                    {
                        valor=obtenerValorNodoItem(onodo,columna);
                        nronodo=onodo.lstnode.size();
                        indice=lsttags.size();
                    }
                }
            }
        }
        return valor;
    }
    public static String buscarNodoDetalleCreditNoteLineImpuesto(String[] filter,int nro,String[] filtroimp,String nameimp,String columna,List<NodoXml> lstinvoice)
    {
        ArrayList lstnodefind=new ArrayList();
        NodoXml onodoinvoice=new NodoXml();
        String valor="0.00";

        onodoinvoice=obtenerNodoParent(filter,nro,lstinvoice);
        for (int indice = 0; indice < filtroimp.length; indice++)
        {
            buscarNodoDependientes(onodoinvoice,filtroimp[indice],nameimp,lstnodefind);
        }
        if (lstnodefind.size()>0)
        {
            NodoXml onododata=(NodoXml)lstnodefind.get(lstnodefind.size()-1);
            valor=obtenerValorNodoItem(onododata,columna);
        }

        return valor;
    }
    private static boolean buscarNodoDependientesCreditNote(NodoXml onodoparent,String tags,String nameimp,ArrayList listado)
    {
        String nodoexception="CreditNote/cac:CreditNoteLine/cac:TaxTotal";
        boolean existe=false;
        if (onodoparent.getTags().trim().equals(tags))
        {
            if (onodoparent.getValor().trim().equals(nameimp))
            {
                existe=true;
            }
        }
        else
        {
            for (int fila = 0; fila < onodoparent.lstnode.size(); fila++)
            {
                NodoXml onodo=(NodoXml)onodoparent.lstnode.get(fila);
                existe=buscarNodoDependientes(onodo,tags,nameimp,listado);
                if (existe)
                {
                    if (!onodo.getTags().trim().equals(nodoexception))
                    {
                        listado.add(onodo);
                        return existe;
                    }
                }
            }
        }
        return existe;
    }

}
