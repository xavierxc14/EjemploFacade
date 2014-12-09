<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Profesor Detail</title>
            <link rel="stylesheet" type="text/css" href="/Conexion/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Profesor Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{profesor.profesor.id}" title="Id" />
                    <h:outputText value="Nombres:"/>
                    <h:outputText value="#{profesor.profesor.nombres}" title="Nombres" />
                    <h:outputText value="Apellidos:"/>
                    <h:outputText value="#{profesor.profesor.apellidos}" title="Apellidos" />
                    <h:outputText value="Cedula:"/>
                    <h:outputText value="#{profesor.profesor.cedula}" title="Cedula" />
                    <h:outputText value="Direccion:"/>
                    <h:outputText value="#{profesor.profesor.direccion}" title="Direccion" />


                </h:panelGrid>
                <br />
                <h:commandLink action="#{profesor.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][profesor.profesor][profesor.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{profesor.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][profesor.profesor][profesor.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{profesor.createSetup}" value="New Profesor" />
                <br />
                <h:commandLink action="#{profesor.listSetup}" value="Show All Profesor Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
