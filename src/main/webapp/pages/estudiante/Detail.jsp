<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Estudiante Detail</title>
            <link rel="stylesheet" type="text/css" href="/Conexion/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Estudiante Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{estudiante.estudiante.id}" title="Id" />
                    <h:outputText value="Nombres:"/>
                    <h:outputText value="#{estudiante.estudiante.nombres}" title="Nombres" />
                    <h:outputText value="Apellidos:"/>
                    <h:outputText value="#{estudiante.estudiante.apellidos}" title="Apellidos" />
                    <h:outputText value="Cedula:"/>
                    <h:outputText value="#{estudiante.estudiante.cedula}" title="Cedula" />
                    <h:outputText value="Direccion:"/>
                    <h:outputText value="#{estudiante.estudiante.direccion}" title="Direccion" />


                </h:panelGrid>
                <br />
                <h:commandLink action="#{estudiante.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentEstudiante" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][estudiante.estudiante][estudiante.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{estudiante.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentEstudiante" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][estudiante.estudiante][estudiante.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{estudiante.createSetup}" value="New Estudiante" />
                <br />
                <h:commandLink action="#{estudiante.listSetup}" value="Show All Estudiante Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
